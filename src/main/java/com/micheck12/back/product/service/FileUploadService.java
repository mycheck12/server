package com.micheck12.back.product.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    String saveFile(MultipartFile multipartFile) throws IOException;
    void deleteImages(String originalFilename);

}
