package com.smartedulanka.finalyearproject.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadRecord {
    public void saveUploadRecord(String uploadedFileName, String str, String fileName);


}
