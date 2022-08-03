package com.smartedulanka.finalyearproject.domain;

import java.util.HashMap;

public class RatingResponseCount {

    private HashMap<Long, Long> answerIdRatingCount;
    private HashMap<Long, Long> answerIdRatingPositiveCount;
    private HashMap<Long, Long> answerIdRatingNegativeCount;

    public HashMap<Long, Long> getAnswerIdRatingCount() {
        return answerIdRatingCount;
    }

    public void setAnswerIdRatingCount(HashMap<Long, Long> answerIdRatingCount) {
        this.answerIdRatingCount = answerIdRatingCount;
    }
    public HashMap<Long, Long> getAnswerIdRatingPositiveCount() {
        return answerIdRatingPositiveCount;
    }

    public void setAnswerIdRatingPositiveCount(HashMap<Long, Long> answerIdRatingPositiveCount) {
        this.answerIdRatingPositiveCount = answerIdRatingPositiveCount;
    }
    public HashMap<Long, Long> getAnswerIdRatingNegativeCount() {
        return answerIdRatingNegativeCount;
    }

    public void setAnswerIdRatingNegativeCount(HashMap<Long, Long> answerIdRatingNegativeCount) {
        this.answerIdRatingNegativeCount = answerIdRatingNegativeCount;
    }
}
