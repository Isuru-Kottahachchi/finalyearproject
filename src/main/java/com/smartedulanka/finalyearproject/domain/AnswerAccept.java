package com.smartedulanka.finalyearproject.domain;

import java.util.HashMap;

public class AnswerAccept {

    private HashMap<Long,String> answerIdAnswerStatus;
    private HashMap<String,String> answerStatusQuestionStatus;



    public HashMap<Long, String> getAnswerIdAnswerStatus() {
        return answerIdAnswerStatus;
    }

    public void setAnswerIdAnswerStatus(HashMap<Long, String> answerIdAnswerStatus) {
        this.answerIdAnswerStatus = answerIdAnswerStatus;
    }

    public HashMap<String, String> getAnswerStatusQuestionStatus() {
        return answerStatusQuestionStatus;
    }

    public void setAnswerStatusQuestionStatus(HashMap<String, String> answerStatusQuestionStatus) {
        this.answerStatusQuestionStatus = answerStatusQuestionStatus;
    }
}
