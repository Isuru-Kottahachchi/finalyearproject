package com.smartedulanka.finalyearproject.service;


import com.smartedulanka.finalyearproject.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingCountService {


    @Autowired
    private RatingRepository ratingRepository;



    public Long retrieveAUsersRatingValue(Long user_id,Long answer_id) {
        if (answer_id != null) {
            return ratingRepository.getAUsersRatingValue(user_id,answer_id);
        }
        return 1L;
    }


   /*When do you use this*/
    public Long retrieveUpdatedRatingValue(Long answer_id) {
        if (answer_id != null) {
            return ratingRepository.getUpdatedRatingValue(answer_id);
        }
        return 1L;
    }

    public Long retrieveUpdatedPositiveRatingValue(Long answer_id) {
        if (answer_id != null) {
            return ratingRepository.getUpdatedPositiveRatingValue(answer_id);
        }
        return 1L;
    }
    public Long retrieveUpdatedNegativeRatingValue(Long answer_id) {
        if (answer_id != null) {
            return ratingRepository.getUpdatedNegativeRatingValue(answer_id);
        }
        return 1L;
    }




    /*Load Voting when forum is loading*/


    public Long retrieveRatingsCount(Long answer_id) {
        if (answer_id != null) {
            return ratingRepository.getFinalRatingValues(answer_id);
        }
        return 1L;
    }


    public Long retrievePositiveRatingsCount(Long answer_id) {
        if (answer_id != null) {
            return ratingRepository.getFinalPositiveRatingValue(answer_id);
        }
        return 1L;
    }

    public Long retrieveNegativeRatingsCount(Long answer_id) {
        if (answer_id != null) {
            return ratingRepository.getFinalNegativeRatingValue(answer_id);
        }
        return 1L;
    }








    public Long retrieveCurrentRatingValue(Long userId, Long answer_id) {


        if (userId != null) {
            return ratingRepository.getCurrentRatingValue(userId,answer_id);
        }
        return 1L;

    }




}
