package com.example.TechnoShark.SchoolRanking.Utils;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorEntity<T> extends ResponseEntity<ErrorApi<T>> {

    public ErrorEntity(String message, String path, T data, Map<String, Object> metadata, List<String> errors) {
        super(new ErrorApi<T>(message, path, data, metadata, errors), HttpStatus.BAD_REQUEST);
    }


    public void status(HttpStatus status) {
        // this.status = status;
    }

}
