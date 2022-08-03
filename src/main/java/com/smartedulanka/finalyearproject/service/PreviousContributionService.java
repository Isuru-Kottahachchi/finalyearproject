package com.smartedulanka.finalyearproject.service;


import com.smartedulanka.finalyearproject.repository.AnswerRepository;
import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import com.smartedulanka.finalyearproject.repository.RatingRepository;
import com.smartedulanka.finalyearproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreviousContributionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private RatingRepository ratingRepository;


    public Long retrieveAuthorId(String questionAuthorName) {
        if (questionAuthorName != null) {
            return userRepository.getAuthorId(questionAuthorName);
        }
        return 1L;
    }


    public String retrieveJoinedDate(Long user_id) {
        if (user_id != null) {
            return userRepository.getJoinedDate(user_id);
        }
        return "str";
    }


    public Long retrieveNumberOfQuestions(Long user_id) {
        if (user_id != null) {
            return questionRepository.getNumberOfQuestions(user_id);
        }
        return 1L;
    }

    public Long retrieveNumberOfAnswers(Long user_id) {
        if (user_id != null) {
            return answerRepository.getNumberOfAnswers(user_id);
        }
        return 1L;
    }

    public Long retrieveNumberOfUpVotes(Long user_id) {
        if (user_id != null) {
            return ratingRepository.getNumberOfUpVotes(user_id);
        }
        return 1L;
    }

    public Long retrieveNumberOfDownVotes(Long user_id) {
        if (user_id != null) {
            return ratingRepository.getNumberOfDownVotes(user_id);
        }
        return 1L;
    }


    public Long retrieveNumberOfAcceptedAnswers(Long user_id) {
        if (user_id != null) {
            return answerRepository.getNumberOfAcceptedAnswers(user_id);
        }
        return 1L;
    }

    public String retrieveRole(Long user_id) {
        if (user_id != null) {
            return userRepository.getRole(user_id);
        }
        return "str";
    }

}
