package com.example.TechnoShark.SchoolRanking.Utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;


public class ErrorApi<T> extends ApiResponse<T> {

    public ErrorApi(String message, String path,T data, Map<String, Object> metadata, List<String> errors) {
        this.success = false;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.metadata = metadata;
        this.errors = errors;

        // return new ResponseEntity<Err >

    }

    public void status(HttpStatus status) {
        this.status = status;
    }

}
