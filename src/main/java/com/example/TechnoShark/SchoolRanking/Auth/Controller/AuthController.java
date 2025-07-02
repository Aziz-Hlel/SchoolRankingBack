package com.example.TechnoShark.SchoolRanking.Auth.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TechnoShark.SchoolRanking.Auth.DTO.AuthResponse;
import com.example.TechnoShark.SchoolRanking.Auth.DTO.JwtUserResponse;
import com.example.TechnoShark.SchoolRanking.Auth.DTO.LoginRequest;
import com.example.TechnoShark.SchoolRanking.Auth.DTO.RefreshTokenRequest;
import com.example.TechnoShark.SchoolRanking.Auth.DTO.TokenRefreshResponse;
import com.example.TechnoShark.SchoolRanking.Auth.Model.CustomUserDetails;
import com.example.TechnoShark.SchoolRanking.Auth.Service.CustomUserDetailsService;
import com.example.TechnoShark.SchoolRanking.Auth.Service.JwtService;
import com.example.TechnoShark.SchoolRanking.Config.JwtProperties;
import com.example.TechnoShark.SchoolRanking.ErrorHandler.Exceptions.InvalidTokenException;
import com.example.TechnoShark.SchoolRanking.Utils.ApiResponse;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final JwtProperties jwtProperties;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()));

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // Generate tokens
            String accessToken = jwtService.generateAccessToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            JwtUserResponse user = userDetails.getUser();

            AuthResponse response = AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .user(user)
                    .tokenType("Bearer")
                    .expiresIn(jwtProperties.getAccessTokenExpiration() / 1000) // Convert to seconds
                    .build();

            log.info("User {} logged in successfully", loginRequest.getEmail());

            ApiResponse<AuthResponse> apiResponse = ApiResponse.<AuthResponse>builder()
                    .message("User logged in successfully")
                    .success(true)
                    .data(response)
                    .status(HttpStatus.OK)
                    .build();

            return ResponseEntity.ok(apiResponse);

        } catch (BadCredentialsException e) {
            log.warn("Failed login attempt for email: {}", loginRequest.getEmail());
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenRefreshResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest refreshRequest) {

        try {
            String refreshToken = refreshRequest.getRefreshToken();
            String username = jwtService.extractUsername(refreshToken);

            if (username == null) {
                throw new InvalidTokenException("Invalid refresh token");
            }

            CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (!jwtService.validateRefreshToken(refreshToken, username)) {
                throw new InvalidTokenException("Invalid or expired refresh token");
            }

            // Generate new tokens
            String newAccessToken = jwtService.generateAccessToken(userDetails);
            String newRefreshToken = jwtService.generateRefreshToken(userDetails);

            // Revoke old refresh token
            jwtService.revokeRefreshToken(username);

            TokenRefreshResponse response = TokenRefreshResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .tokenType("Bearer")
                    .expiresIn(jwtProperties.getAccessTokenExpiration() / 1000)
                    .build();

            log.info("Token refreshed successfully for user: {}", username);

            ApiResponse<TokenRefreshResponse> apiResponse = ApiResponse.<TokenRefreshResponse>builder()
                    .message("Token refreshed successfully")
                    .success(true)
                    .data(response)
                    .status(HttpStatus.OK)
                    .build();

            return ResponseEntity.ok(apiResponse);

        } catch (ExpiredJwtException e) {
            log.warn("Refresh token expired: {}", e.getMessage());
            throw new InvalidTokenException("Refresh token expired");
        } catch (Exception e) {
            log.error("Error refreshing token: {}", e.getMessage());
            throw new InvalidTokenException("Invalid refresh token");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Map<String, String>>> logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring(7);
            try {
                String username = jwtService.extractUsername(accessToken);
                jwtService.revokeRefreshToken(username);
                log.info("User {} logged out successfully", username);
            } catch (Exception e) {
                log.warn("Error during logout: {}", e.getMessage());
            }
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully logged out");

        ApiResponse<Map<String, String>> apiResponse = ApiResponse.<Map<String, String>>builder()
                .message("Successfully logged out")
                .success(true)
                .data(response)
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // require authentcation on a method
    // if a controller permitted from authentication you can add this annotation to
    @PreAuthorize("isAuthenticated()")
    @GetMapping({ "/me" })
    public ResponseEntity<?> getCurrentUser() {

        return ResponseEntity.ok("ds");
        // return ResponseEntity.ok("t5l");
        // maybe fetch user details by username or handle anonymous user
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // log.info(auth.getClass().getName());
        // if (auth == null || !auth.isAuthenticated() || auth instanceof
        // AnonymousAuthenticationToken) {
        // throw new AccessDeniedException("Unauthorized");
        // }
        // return ResponseEntity.ok("You are authenticated");

    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}