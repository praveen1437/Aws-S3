package com.example.demo.exception;

import lombok.*;

@Setter
@Getter
public class FileUploadException extends Exception{

    private final String code;
    private final String fileName;

    public FileUploadException(String code, String message, String fileName) {
        super(message);
        this.code = code;
        this.fileName = fileName;
    }
}
