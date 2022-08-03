package com.smartedulanka.finalyearproject.service;

import com.smartedulanka.finalyearproject.datalayer.entity.Question;

import java.io.FileNotFoundException;

public interface ImageUploadRecord {
    public void saveImageUploadRecord(Question question,String uploadedImageNameUrl, String imageName) throws FileNotFoundException;
}
