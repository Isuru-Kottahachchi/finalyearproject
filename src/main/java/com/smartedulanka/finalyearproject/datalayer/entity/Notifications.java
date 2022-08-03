package com.smartedulanka.finalyearproject.datalayer.entity;


import javax.persistence.*;

@Entity
@Table(name = "notifications")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notification_id;

    @Column(nullable = true, unique = false, length = 100)
    private String responseSubmittedTime;

    @Column(nullable = true, unique = false, length = 1000)
    private String respondedUserName;

    @Column(nullable = true, unique = false, length = 1000)
    private Long questionAuthorId;

    @Column(nullable = true, unique = false, length = 1000)
    private Long respondedQuestionId;

    @Column(nullable = true, unique = false, length = 1000)
    private String respondedFullQuestion;





    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Long getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(Long notification_id) {
        this.notification_id = notification_id;
    }

    public String getResponseSubmittedTime() {
        return responseSubmittedTime;
    }

    public void setResponseSubmittedTime(String responseSubmittedTime) {
        this.responseSubmittedTime = responseSubmittedTime;
    }

    public String getRespondedUserName() {
        return respondedUserName;
    }

    public void setRespondedUserName(String respondedUserName) {
        this.respondedUserName = respondedUserName;
    }

    public Long getQuestionAuthorId() {
        return questionAuthorId;
    }

    public void setQuestionAuthorId(Long questionAuthorId) {
        this.questionAuthorId = questionAuthorId;
    }

    public Long getRespondedQuestionId() {
        return respondedQuestionId;
    }

    public void setRespondedQuestionId(Long respondedQuestionId) {
        this.respondedQuestionId = respondedQuestionId;
    }

    public String getRespondedFullQuestion() {
        return respondedFullQuestion;
    }

    public void setRespondedFullQuestion(String respondedFullQuestion) {
        this.respondedFullQuestion = respondedFullQuestion;
    }
}
