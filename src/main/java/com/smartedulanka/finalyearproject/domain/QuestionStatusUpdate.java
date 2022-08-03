package com.smartedulanka.finalyearproject.domain;

import java.util.HashMap;

public class QuestionStatusUpdate {

    HashMap<Long, String> questionIdQuestionStatusValue ;
    HashMap<Long,Long> questionIdWithUserId;
    String userRole;

    Long currentLoggedInUserId;

    public Long getCurrentLoggedInUserId() {
        return currentLoggedInUserId;
    }

    public void setCurrentLoggedInUserId(Long currentLoggedInUserId) {
        this.currentLoggedInUserId = currentLoggedInUserId;
    }

    public HashMap<Long, String> getQuestionIdQuestionStatusValue() {
        return questionIdQuestionStatusValue;
    }

    public void setQuestionIdQuestionStatusValue(HashMap<Long, String> questionIdQuestionStatusValue) {
        this.questionIdQuestionStatusValue = questionIdQuestionStatusValue;
    }

    public HashMap<Long, Long> getQuestionIdWithUserId() {
        return questionIdWithUserId;
    }

    public void setQuestionIdWithUserId(HashMap<Long, Long> questionIdWithUserId) {
        this.questionIdWithUserId = questionIdWithUserId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
