package com.ecommerce.ecommerce_app.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.ecommerce.ecommerce_app.enums.FileType;
import com.ecommerce.ecommerce_app.service.AwsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsServiceImpl implements AwsService {

    @Value("${cloud.aws.s3.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;


    @Override
    public String uploadFile(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(FileType.fromFilename(Objects.requireNonNull(file.getOriginalFilename())).toString());

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes())) {
            PutObjectRequest putRequest = new PutObjectRequest(bucketName, fileName, inputStream, metadata);
            s3Client.putObject(putRequest);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
        return s3Client.getUrl(bucketName, fileName).toString();
    }

    @Override
    public byte[] downloadFile(String fileName) {
        //TODO: download based on the fileName
        try {
            S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, fileName));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            s3Object.getObjectContent().transferTo(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file from S3", e);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
    }

}
