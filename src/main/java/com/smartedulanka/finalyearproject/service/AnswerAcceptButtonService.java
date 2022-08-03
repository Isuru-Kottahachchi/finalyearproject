package com.smartedulanka.finalyearproject.service;

import com.smartedulanka.finalyearproject.repository.AnswerRepository;
import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerAcceptButtonService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public String retrieveAnswerStatus(Long answer_id) {
        if (answer_id != null) {
            return answerRepository.getAnswerStatus(answer_id);
        }
        return "str";
    }






    public Long retrieveQuestionId(Long answerId) {
        if (answerId != null) {
            return answerRepository.getQuestionId(answerId);
        }
        return 1L;
    }

    public String retrieveQuestionStatus(Long question_id) {
        if (question_id != null) {
            return questionRepository.getQuestionStatus(question_id);
        }
        return "str";
    }


    /*public Long retrieveAcceptedAnswerId(Long question_id) {
        if (question_id != null) {
            return answerRepository.getAcceptedAnswerId(question_id);
        }
        return 1L;
    }*/





}
