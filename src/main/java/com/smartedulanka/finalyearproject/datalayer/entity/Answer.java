package com.smartedulanka.finalyearproject.datalayer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answer_id;

    @Column(nullable = true, unique = false, length = 100)
    private Long answer_authorID;

    @Column(nullable = true, unique = false, length = 100)
    private Long questionID;




    @Column(nullable = false, unique = false, length = 100)
    private String fullAnswer;

    @Column(nullable = true, unique = false, length = 100)
    private String answerSubmittedTime;



    public String getFullAnswer() {
        return fullAnswer;
    }

    public void setFullAnswer(String fullAnswer) {
        this.fullAnswer = fullAnswer;
    }

    public Long getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(Long answer_id) {
        this.answer_id = answer_id;
    }

    public Long getAnswer_authorID() {
        return answer_authorID;
    }

    public void setAnswer_authorID(Long answer_authorID) {
        this.answer_authorID = answer_authorID;
    }

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

   /* public String getAnswer() {
        return fullAnswer;
    }

    public void setAnswer(String fullAnswer) {
        this.fullAnswer = fullAnswer;
    }*/
    public String getAnswerSubmittedTime() {
        return answerSubmittedTime;
    }

    public void setAnswerSubmittedTime(String answerSubmittedTime) {
        this.answerSubmittedTime = answerSubmittedTime;
    }
}
