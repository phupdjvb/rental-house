package com.jvb_intern.rental_acommodation.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/*
 * Quản lý việc tải lên và lấy đường dẫn cho các tệp ảnh
 */
public interface FileUploadService {
    void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException;
}
