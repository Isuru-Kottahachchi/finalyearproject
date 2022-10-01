package com.smartedulanka.finalyearproject.domain;

public class Profile {

    String joinedDateAndTime;
    String role;
    String fullName;
    String email;
    Long numberOfQuestionAsked;
    Long numberOfAnswersGiven;
    Long numberOfUpVotes;
    Long numberOfDownVotes;
    Long numberOfAcceptedAnswers;

    Long questionAuthorId;


    public String getJoinedDateAndTime() {
        return joinedDateAndTime;
    }

    public void setJoinedDateAndTime(String joinedDateAndTime) {
        this.joinedDateAndTime = joinedDateAndTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getNumberOfQuestionAsked() {
        return numberOfQuestionAsked;
    }

    public void setNumberOfQuestionAsked(Long numberOfQuestionAsked) {
        this.numberOfQuestionAsked = numberOfQuestionAsked;
    }

    public Long getNumberOfAnswersGiven() {
        return numberOfAnswersGiven;
    }

    public void setNumberOfAnswersGiven(Long numberOfAnswersGiven) {
        this.numberOfAnswersGiven = numberOfAnswersGiven;
    }

    public Long getNumberOfUpVotes() {
        return numberOfUpVotes;
    }

    public void setNumberOfUpVotes(Long numberOfUpVotes) {
        this.numberOfUpVotes = numberOfUpVotes;
    }

    public Long getNumberOfDownVotes() {
        return numberOfDownVotes;
    }

    public void setNumberOfDownVotes(Long numberOfDownVotes) {
        this.numberOfDownVotes = numberOfDownVotes;
    }

    public Long getNumberOfAcceptedAnswers() {
        return numberOfAcceptedAnswers;
    }

    public void setNumberOfAcceptedAnswers(Long numberOfAcceptedAnswers) {
        this.numberOfAcceptedAnswers = numberOfAcceptedAnswers;
    }




    public Long getQuestionAuthorId() {
        return questionAuthorId;
    }

    public void setQuestionAuthorId(Long questionAuthorId) {
        this.questionAuthorId = questionAuthorId;
    }
}
