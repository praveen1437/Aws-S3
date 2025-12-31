package com.example.demo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUploadResponse {
    private String fileName;
    private String fileUrl;
    private String status;
}
