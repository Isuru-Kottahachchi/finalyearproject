package com.smartedulanka.finalyearproject.service;

import com.smartedulanka.finalyearproject.datalayer.entity.Answer;
import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import com.smartedulanka.finalyearproject.datalayer.entity.Ratings;
import com.smartedulanka.finalyearproject.repository.AnswerRepository;
import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import com.smartedulanka.finalyearproject.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PreviouslySavedObjectService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Ratings retrievePreviouslySavedRating(Long userId,Long answer_id) {
        if (userId != null) {
            return ratingRepository.getPreviouslySavedRating(userId,answer_id);
        }
        return new Ratings();
    }



    public Answer retrievePreviouslySavedAnswer(Long answer_id) {
        if (answer_id != null) {
            return answerRepository.getPreviouslySavedAnswer(answer_id);
        }
        return new Answer();
    }

    public Question retrievePreviouslySavedQuestion(Long question_id) {
        if (question_id != null) {
            return questionRepository.getPreviouslySavedQuestion(question_id);
        }
        return new Question();
    }







   /* public Long retrieveRatingId(Long question_id) {


        if (question_id != null) {
            return ratingRepository.getRatingId(question_id);
        }
        return 1L;


    }*/


}
