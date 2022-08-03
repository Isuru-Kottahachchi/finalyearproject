package com.smartedulanka.finalyearproject.service;


import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreviouslyAskedQuestionService {

    @Autowired
    QuestionRepository questionRepository;


    public List<Question> findAllQuestionInId(Long user_id) {

        if (user_id != null) {
            return questionRepository.getQuestionsWithId(user_id);
        }
        return questionRepository.findAll();
    }

   /* public List<Question> findSolvedQuestionInId(Long user_id) {

        if (user_id != null) {
            return questionRepository.getSolvedQuestionsWithId(user_id);
        }
        return questionRepository.findAll();
    }
*/

}
