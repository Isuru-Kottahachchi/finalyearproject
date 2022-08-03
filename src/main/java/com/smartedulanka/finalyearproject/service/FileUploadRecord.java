package com.smartedulanka.finalyearproject.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface FileUploadRecord {
    public void saveUploadRecord(String uploadedFileNameUrl, String str, String fileName) throws FileNotFoundException;


}
