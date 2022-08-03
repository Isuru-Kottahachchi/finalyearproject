package com.smartedulanka.finalyearproject.domain;

import java.util.ArrayList;
import java.util.HashMap;

public class RatingValueOfAUser {


    HashMap<Long, Long> specificUsersRatingValue ;

    Long[] answerIds;

    Long currentLoggedInUserId;

    public HashMap<Long, Long> getSpecificUsersRatingValue() {
        return specificUsersRatingValue;
    }

    public void setSpecificUsersRatingValue(HashMap<Long, Long> specificUsersRatingValue) {
        this.specificUsersRatingValue = specificUsersRatingValue;
    }

    public Long[] getAnswerIds() {
        return answerIds;
    }

    public void setAnswerIds(Long[] answerIds) {
        this.answerIds = answerIds;
    }

    public Long getCurrentLoggedInUserId() {
        return currentLoggedInUserId;
    }

    public void setCurrentLoggedInUserId(Long currentLoggedInUserId) {
        this.currentLoggedInUserId = currentLoggedInUserId;
    }
}
