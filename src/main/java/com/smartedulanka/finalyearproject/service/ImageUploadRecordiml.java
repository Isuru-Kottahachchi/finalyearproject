package com.smartedulanka.finalyearproject.service;

import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ImageUploadRecordiml implements ImageUploadRecord {
    @Autowired
    private QuestionRepository questionRepository;
    @Override
    public void saveImageUploadRecord(Question question,String uploadedImageNameUrl,String imageName) {

        question.setImageURL(uploadedImageNameUrl);
        question.setUploadedImageName(imageName);




        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        question.setQuestionstatus(false);
        question.setQuestionSubmittedTime(dtf.format(now));
        /* question.setQuestionAuthorName();*/

        questionRepository.save(question);



    }
}
