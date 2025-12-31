package com.example.demo.exception;

import com.example.demo.domain.ApiError;
import com.example.demo.domain.ApiResponse;
import com.example.demo.domain.FileUploadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Exception occurred please try after some time");
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ApiResponse<FileUploadResponse>> handleFileUploadException(FileUploadException e) {
        ApiError apiError = ApiError.builder().code(e.getCode()).message(e.getMessage()).details(e.getFileName()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, apiError));
    }
}
