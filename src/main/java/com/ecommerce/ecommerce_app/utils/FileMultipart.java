package com.ecommerce.ecommerce_app.utils;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

@Getter
public class FileMultipart implements MultipartFile {
    private final String name;
    private final String originalFilename;
    private final String contentType;
    private final byte[] content;

    public FileMultipart(String name, String originalFilename, String contentType, byte[] content) {
        if (name == null || originalFilename == null || contentType == null || content == null) {
            throw new IllegalArgumentException("None of the parameters can be null.");
        }
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.content = content;
    }

    @Override
    public boolean isEmpty() {
        return content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() {
        return Arrays.copyOf(content, content.length);
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (FileOutputStream fos = new FileOutputStream(dest)) {
            fos.write(content);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileMultipart)) return false;
        FileMultipart that = (FileMultipart) o;
        return name.equals(that.name) &&
                originalFilename.equals(that.originalFilename) &&
                contentType.equals(that.contentType) &&
                Arrays.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, originalFilename, contentType);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }
}
