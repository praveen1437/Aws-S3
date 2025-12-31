package com.example.demo.service;

import com.example.demo.domain.FileUploadResponse;
import com.example.demo.exception.FileUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;

@Service
@Slf4j
public class FileHandlingService {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public FileUploadResponse uploadFile(MultipartFile multipartFile) throws FileUploadException {
        try {
            if(true) {
                throw new RuntimeException("Test");
            }
            PutObjectResponse putObjectResponse = s3Client.putObject(PutObjectRequest.builder().bucket(bucketName)
                    .key(multipartFile.getOriginalFilename()).build(), RequestBody.fromBytes(multipartFile.getBytes()));
            String fileUrl = String.format(
                    "https://%s.s3.%s.amazonaws.com/%s",
                    bucketName,
                    s3Client.serviceClientConfiguration().region().id(),
                    multipartFile.getOriginalFilename()
            );
            return FileUploadResponse.builder()
                    .fileName(multipartFile.getOriginalFilename()).fileUrl(fileUrl).status("UPLOADED").build();
        } catch (Exception e) {
            String fileName = multipartFile.getOriginalFilename();
            log.error("Exception occurred while uploading file, file name : {}", fileName, e);
            throw new FileUploadException("UPLOAD_FAILED" ,fileName, e.getMessage());
        }
    }

    public byte[] getFile(String fileName) throws IOException {
        ResponseBytes<GetObjectResponse> getObjectResponse = s3Client.getObjectAsBytes(
                GetObjectRequest.builder().bucket(bucketName).key(fileName).build()
        );
        return getObjectResponse.asByteArray();
    }
}
