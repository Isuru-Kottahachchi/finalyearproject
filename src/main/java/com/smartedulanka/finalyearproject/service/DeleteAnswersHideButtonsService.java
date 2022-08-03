package com.smartedulanka.finalyearproject.service;


import com.smartedulanka.finalyearproject.repository.AnswerRepository;
import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteAnswersHideButtonsService {


    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;


    public Long retrieveAnswerAuthorUserId(Long answer_id) {

        if (answer_id != null) {
            return answerRepository.getAnswerAuthorUserId(answer_id);
        }
        return 1L;

    }

    public Long retrieveQuestionId(Long answer_id) {

        if (answer_id != null) {
            return answerRepository.getQuestionId(answer_id);
        }
        return 1L;

    }

    public Long retrieveQuestionAuthorId(Long question_id) {

        if (question_id != null) {
            return questionRepository.getQuestionAuthorId(question_id);
        }
        return 1L;
    }
}
