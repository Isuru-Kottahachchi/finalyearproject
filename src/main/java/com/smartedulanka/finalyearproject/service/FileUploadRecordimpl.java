package com.smartedulanka.finalyearproject.service;

import com.smartedulanka.finalyearproject.datalayer.entity.UploadRecords;
import com.smartedulanka.finalyearproject.datalayer.entity.User;
import com.smartedulanka.finalyearproject.repository.UploadRepository;
import com.smartedulanka.finalyearproject.repository.UserRepository;
import com.smartedulanka.finalyearproject.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileUploadRecordimpl implements FileUploadRecord {

    @Autowired
    private UploadRepository uploadRepo;


    @Autowired
    private UserRepository userRepository;


    /*File upload details to the database*/

    @Override
    public void saveUploadRecord(String uploadedFileNameUrl, String subjectArea, String fileName){

        UploadRecords uploadrecord = new UploadRecords();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();


       User user;
       try {

           CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
           Long userId = customUserDetails.getUserId();
           String userEmail = customUserDetails.getUsername();
           String userName = customUserDetails.getFullName();


           user = userRepository.getById(userId);
           uploadrecord.setUser(user);
           uploadrecord.setUploaderEmail(userEmail);
           uploadrecord.setUploaderName(userName);


       }catch(Exception e){
           System.out.println(e);
       }

        uploadrecord.setUploadedFileName(uploadedFileNameUrl);
        uploadrecord.setReviewStatus("PENDING");
        uploadrecord.setFileSubmittedTime(dtf.format(now));
        uploadrecord.setSubjectArea(subjectArea);

        uploadrecord.setFileName(fileName);

        uploadRepo.save(uploadrecord);

    }
}
