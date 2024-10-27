package com.ecommerce.ecommerce_app.utils;

import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MultipartFileUtil {

    public static MultipartFile createMultipartFile(String name, String originalFilename, String contentType, Path imagePath) throws IOException {
        byte[] content = Files.readAllBytes(imagePath);
        return new FileMultipart(name, originalFilename, contentType, content);
    }

    public static byte[] resizeImage(byte[] originalImage, int targetWidth, int targetHeight) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(originalImage);
        BufferedImage originalBufferedImage = ImageIO.read(bais);

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalBufferedImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", baos);

        return baos.toByteArray();
    }

}
