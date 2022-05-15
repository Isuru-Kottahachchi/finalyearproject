package com.smartedulanka.finalyearproject.service;

import com.smartedulanka.finalyearproject.datalayer.entity.UploadRecord;
import com.smartedulanka.finalyearproject.repository.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileUploadRecordimpl implements FileUploadRecord {
    @Autowired
    private UploadRepository uploadRepo;




    /*File upload details to the database*/

    @Override
    public void saveUploadRecord(String uploadedFileNameUrl, String subjectArea, String fileName) {

        UploadRecord uploadrecord = new UploadRecord();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();


        uploadrecord.setUploadedFileName(uploadedFileNameUrl);
        uploadrecord.setReviewStatus("PENDING");
        //uploadrecord.setUploaderId(user.getId());
        uploadrecord.setFileSubmittedTime(dtf.format(now));
        uploadrecord.setSubjectArea(subjectArea);
        uploadrecord.setFileName(fileName);



        uploadRepo.save(uploadrecord);

    }
}
