package com.smartedulanka.finalyearproject.service;

import com.smartedulanka.finalyearproject.datalayer.entity.Question;

public interface ImageUploadRecord {
    public void saveImageUploadRecord(Question question,String uploadedImageNameUrl, String imageName);
}
