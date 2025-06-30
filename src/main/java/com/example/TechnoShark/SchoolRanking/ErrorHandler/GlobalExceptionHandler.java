package com.example.TechnoShark.SchoolRanking.ErrorHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import com.example.TechnoShark.SchoolRanking.ErrorHandler.Exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {

                ErrorResponse errorResponse = ErrorResponse.builder()
                                .message(ex.getMessage())
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .timestamp(LocalDateTime.now())
                                .errors(Map.of("error", "Unauthorized", "message", ex.getMessage()))
                                .build();

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        // this one catches exceptions throw by spring security at the controller level
        // ( like when using @PreAuthorize)
        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("error", "Forbidden", "message", ex.getMessage()));
        }

        // Handle missing required request parameters
        @ExceptionHandler(MissingServletRequestParameterException.class)
        public ResponseEntity<ErrorResponse> handleMissingRequestParamException(
                        MissingServletRequestParameterException ex,
                        HttpServletRequest request) {
                String paramName = ex.getParameterName();
                String errorMessage = String.format("Missing required request parameter: '%s'", paramName);

                ErrorResponse errorResponse = ErrorResponse.builder()
                                .message(errorMessage)
                                .path(request.getRequestURI())
                                .method(request.getMethod())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Handle JSON parse errors (bad JSON or type mismatches in request body) (
        // before Jakarta Bean Validation , while the Jackson
        // deserialization )
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
                        HttpServletRequest request) {

                Map<String, String> errorDetails = new HashMap<>();

                // Extract root cause if available
                Throwable rootCause = ex.getMostSpecificCause();

                // Optionally, parse Jackson errors
                if (rootCause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException invalidFormatException) {

                        String fieldPath = invalidFormatException.getPath().stream()
                                        .map(ref -> ref.getFieldName())
                                        .collect(Collectors.joining("."));
                        errorDetails.put(fieldPath, "Invalid value: expected type " +
                                        invalidFormatException.getTargetType().getSimpleName());

                } else if (rootCause instanceof com.fasterxml.jackson.databind.exc.MismatchedInputException mismatchedInputException) {

                        String fieldPath = mismatchedInputException.getPath().stream()
                                        .map(ref -> ref.getFieldName())
                                        .collect(Collectors.joining("."));
                        errorDetails.put(fieldPath.isEmpty() ? "body" : fieldPath, "Invalid or missing input");

                } else if (rootCause instanceof java.time.format.DateTimeParseException) {

                        if (ex.getCause() instanceof InvalidFormatException invalidFormatEx) {
                                // ? if the client insert an int in the a LocalDate type field , the field will
                                // just be parsed as null if it allowed and no error will be thrown like in the
                                // lastInspectionDate in school-staff
                                String fieldPath = invalidFormatEx.getPath().stream()
                                                .map(ref -> ref.getFieldName())
                                                .collect(Collectors.joining("."));
                                errorDetails.put(fieldPath, "Invalid value: expected type " +
                                                invalidFormatEx.getTargetType().getSimpleName());

                        }

                        else {
                                errorDetails.put("unkown error", "! untreated error");
                        }

                }

                else {
                        errorDetails.put("body", "Malformed JSON or request body");
                }

                ErrorResponse errorResponse = ErrorResponse.builder()
                                .message("Malformed or unreadable request body")
                                .errors(errorDetails)
                                .path(request.getRequestURI())
                                .method(request.getMethod())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        @ExceptionHandler(ResponseStatusException.class)
        public ResponseEntity<ErrorResponse> handleStatusException(ResponseStatusException ex,
                        HttpServletRequest request) {
                ErrorResponse errorResponse = ErrorResponse.builder()
                                .message(ex.getReason())
                                .path(request.getRequestURI())
                                .method(request.getMethod())
                                .status(ex.getStatusCode().value())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }

        // Handle type mismatches in @RequestParam or @PathVariable (e.g., UUID or
        // Integer parse error)
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                        HttpServletRequest request) {
                Map<String, String> errors = new HashMap<>();

                String paramName = ex.getName();
                Class<?> requiredType = ex.getRequiredType();
                String expectedType = requiredType != null ? requiredType.getSimpleName() : "unknown";

                String actualValue = String.valueOf(ex.getValue());

                errors.put(paramName, String.format(
                                "Invalid value '%s' for parameter '%s'. Expected type: %s.",
                                actualValue, paramName, expectedType));

                ErrorResponse errorResponse = ErrorResponse.builder()
                                .message("Parameter binding failed")
                                .errors(errors)
                                .path(request.getRequestURI())
                                .method(request.getMethod())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Handle validation errors (e.g., @Valid failed)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                Map<String, String> validationErrors = new HashMap<>();
                ex.getBindingResult().getFieldErrors()
                                .forEach(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));

                ErrorResponse errorResponse = ErrorResponse.builder()
                                .message("Validation failed")
                                .errors(validationErrors) // ✅ Separate field for validation errors
                                .path(request.getRequestURI())
                                .method(request.getMethod())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {

                ErrorResponse errorResponse = ErrorResponse.builder()
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .method(request.getMethod())
                                .status(HttpStatus.NOT_FOUND.value())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {

                log.error("Unhandled exception occurred", ex); // full stack trace in logs

                ErrorResponse errorResponse = ErrorResponse.builder()
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .method(request.getMethod())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

}
