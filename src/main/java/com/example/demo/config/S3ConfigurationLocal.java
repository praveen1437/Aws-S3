package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Profile("local")
@Slf4j
public class S3ConfigurationLocal {

    @Value("${aws.s3.access.key}")
    private String accessKey;
    @Value("${aws.s3.access.pwd}")
    private String passWord;
    @Value("${aws.s3.region}")
    private String region;

    @Bean
    @Profile("local")
    public S3Client s3Client() {
        log.info("creating s3Client for local");
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, passWord);
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .build();
    }

}
