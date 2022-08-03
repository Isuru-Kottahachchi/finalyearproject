package com.smartedulanka.finalyearproject.domain;

import java.util.HashMap;

public class AnswerDelete {

    HashMap<Long,Long> answerUserId;

    Long currentLoggedInUserId;

    String userRole;

    HashMap<Long,Long> answerIdQuestionAuthorId;

    public HashMap<Long, Long> getAnswerUserId() {
        return answerUserId;
    }

    public void setAnswerUserId(HashMap<Long, Long> answerUserId) {
        this.answerUserId = answerUserId;
    }

    public Long getCurrentLoggedInUserId() {
        return currentLoggedInUserId;
    }

    public void setCurrentLoggedInUserId(Long currentLoggedInUserId) {
        this.currentLoggedInUserId = currentLoggedInUserId;
    }

    public HashMap<Long, Long> getAnswerIdQuestionAuthorId() {
        return answerIdQuestionAuthorId;
    }

    public void setAnswerIdQuestionAuthorId(HashMap<Long, Long> answerIdQuestionAuthorId) {
        this.answerIdQuestionAuthorId = answerIdQuestionAuthorId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
