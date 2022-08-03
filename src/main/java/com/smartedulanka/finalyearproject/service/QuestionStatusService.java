package com.smartedulanka.finalyearproject.service;


import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionStatusService {

    @Autowired
    private QuestionRepository questionRepository;



    public String retrieveQuestionStatus(Long question_id) {


        if (question_id != null) {
            return questionRepository.getQuestionStatus(question_id);
        }
        return "";

    }


    public Long retrieveUserId(Long question_id) {


        if (question_id != null) {
            return questionRepository.getUserId(question_id);
        }
        return 1L;

    }


}
