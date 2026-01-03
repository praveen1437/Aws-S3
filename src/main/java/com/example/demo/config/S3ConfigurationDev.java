package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
@Configuration
@Profile("dev")
@Slf4j
public class S3ConfigurationDev {

    @Value("${aws.s3.region}")
    private String region;

    @Bean
    public S3Client s3Client() {
        log.info("creating s3Client for dev");
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        log.info("Creating s3Presigner for dev");
        return S3Presigner.builder()
                .region(Region.of(region)).build();
    }
}
