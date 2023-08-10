package com.jvb_intern.rental_acommodation.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.jvb_intern.rental_acommodation.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Override
    public void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // key word: try-with-resource: (> Java 7) tự động đóng tài nguyên khi khối try
        // kết thúc
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new IOException("Không thể lưu tệp tin: " + fileName, e);
        }
    }
}
