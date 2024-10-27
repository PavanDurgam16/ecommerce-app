package com.ecommerce.ecommerce_app.service;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AwsService {

    String uploadFile(MultipartFile multipartFile) throws FileUploadException, IOException;

    Object downloadFile(String imageUrl) throws IOException;

    public void deleteFile(String imageUrl);
}
