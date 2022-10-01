package com.smartedulanka.finalyearproject.service;

import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import com.smartedulanka.finalyearproject.datalayer.entity.User;
import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import com.smartedulanka.finalyearproject.repository.UserRepository;
import com.smartedulanka.finalyearproject.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ImageUploadRecordiml implements ImageUploadRecord {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void saveImageUploadRecord(Question question,String uploadedImageNameUrl,String imageName)  {


        /*Mapping passing current logged in user details*/
        User user;
        try {

            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = customUserDetails.getUserId();
            String userEmail = customUserDetails.getUsername();
            String userName = customUserDetails.getFullName();

            user = userRepository.getById(userId);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            question.setImageURL(uploadedImageNameUrl);
            question.setUploadedImageName(imageName);

            question.setQuestionAuthorName(userName);
            question.setQuestionAuthorEmail(userEmail);
            question.setUser(user);

            question.setQuestionstatus("UNSOLVED");
            question.setQuestionSubmittedTime(dtf.format(now));

            /*Foul language check*/

            questionRepository.save(question);

        }catch(Exception e){
            System.out.println(e);
        }



    }

    public void saveWithoutImageUploadRecord(Question question){

            User user;

            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = customUserDetails.getUserId();
            String userEmail = customUserDetails.getUsername();
            String userName = customUserDetails.getFullName();

            user = userRepository.getById(userId);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            question.setQuestionAuthorName(userName);
            question.setQuestionAuthorEmail(userEmail);
            question.setUser(user);

            question.setQuestionstatus("UNSOLVED");
            question.setQuestionSubmittedTime(dtf.format(now));

            /*Foul language check*/

            questionRepository.save(question);



    }
}
