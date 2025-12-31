package com.example.demo.controller;

import com.example.demo.domain.ApiResponse;
import com.example.demo.domain.FileUploadResponse;
import com.example.demo.exception.FileUploadException;
import com.example.demo.service.FileHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("v1/file")
public class FileHandlingController {

    @Autowired
    private FileHandlingService fileHandlingService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<FileUploadResponse>> uploadFile(@RequestPart("file") MultipartFile multipartFile) throws IOException, FileUploadException {
        return new ResponseEntity<>(new ApiResponse<>(true, fileHandlingService.uploadFile(multipartFile), null), HttpStatus.ACCEPTED);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("fileName") String fileName) throws IOException {
        return new ResponseEntity<>(fileHandlingService.getFile(fileName), HttpStatus.ACCEPTED);
    }
}
