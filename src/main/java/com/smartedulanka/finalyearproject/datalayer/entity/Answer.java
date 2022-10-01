package com.smartedulanka.finalyearproject.datalayer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answer_id;

    @Column(nullable = false, unique = false, length = 1000)
    private String fullAnswer;

    @Column(nullable = false, unique = false, length = 100)
    private String answerSubmittedTime;

    @Column(nullable = false, unique = false, length = 1000)
    private Long answerAuthorId;

    @Column(nullable = false, unique = false, length = 1000)
    private String answerAuthorName;

    @Column(nullable = false, unique = false, length = 1000)
    private String answerAuthorEmail;

    @Column(nullable = false, unique = false, length = 1000)
    private String answerStatus;

    public String getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(String answerStatus) {
        this.answerStatus = answerStatus;
    }

    /*Many to one mapping question*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }


    /* Many to One mapping with users*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    @OneToMany(mappedBy = "answer", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.REMOVE })
    private List<Ratings> ratings;

    public List<Ratings> getRatings() {
        return ratings;
    }

    public void setRatings(List<Ratings> ratings) {
        this.ratings = ratings;
    }




    @OneToMany(mappedBy = "answer", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.REMOVE })
    private List<ReportedAnswers> reportedAnswers;

    public List<ReportedAnswers> getReportedAnswers() {
        return reportedAnswers;
    }

    public void setReportedAnswers(List<ReportedAnswers> reportedAnswers) {
        this.reportedAnswers = reportedAnswers;
    }

    @OneToMany(mappedBy = "answer", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.REMOVE })
    private List<Notifications> notifications;

    public List<Notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notifications> notifications) {
        this.notifications = notifications;
    }

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

    public String getAnswerSubmittedTime() {
        return answerSubmittedTime;
    }

    public void setAnswerSubmittedTime(String answerSubmittedTime) {
        this.answerSubmittedTime = answerSubmittedTime;

    }

    public Long getAnswerAuthorId() {
        return answerAuthorId;
    }

    public void setAnswerAuthorId(Long answerAuthorId) {
        this.answerAuthorId = answerAuthorId;
    }

    public String getAnswerAuthorEmail() {
        return answerAuthorEmail;
    }

    public void setAnswerAuthorEmail(String answerAuthorEmail) {
        this.answerAuthorEmail = answerAuthorEmail;
    }

    public String getAnswerAuthorName() {
        return answerAuthorName;
    }

    public void setAnswerAuthorName(String answerAuthorName) {
        this.answerAuthorName = answerAuthorName;
    }
}
