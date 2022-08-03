package com.smartedulanka.finalyearproject.datalayer.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long question_id;

    @Column(nullable = false, unique = false, length = 100)
    private String question_subjectArea;

    @Column(nullable = false, unique = false, length = 1000)
    private String questionNote;

    @Column(nullable = false, unique = false, length = 1000)
    private String  fullQuestion;

    @Column(nullable = true, unique = false, length = 100)
    private String questionStatus;

    @Column(nullable = true, unique = false, length = 100)
    private String questionSubmittedTime;

    @Column(nullable = true, unique = false, length = 1000)
    private String uploadedImageName;

    @Column(nullable = true, unique = false, length = 1000)
    private String imageURL;

    @Column(nullable = false, unique = false, length = 1000)
    private String questionAuthorName;

    @Column(nullable = false, unique = false, length = 1000)
    private String questionAuthorEmail;

    public String getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(String questionStatus) {
        this.questionStatus = questionStatus;
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





    /*One to many mapping with answer*/
    @OneToMany(mappedBy = "question", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.REMOVE })
    private List<Answer> answer;

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }




    /*One to many mapping with Notifications*/
    @OneToMany(mappedBy = "question", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.REMOVE })
    private List<Notifications> notifications;

    public List<Notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notifications> notifications) {
        this.notifications = notifications;
    }


    /*One to many mapping with Report questions*/
    @OneToMany(mappedBy = "question", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.REMOVE })
    private List<ReportedQuestions> reportedQuestions;

    public List<ReportedQuestions> getReportedQuestions() {
        return reportedQuestions;
    }

    public void setReportedQuestions(List<ReportedQuestions> reportedQuestions) {
        this.reportedQuestions = reportedQuestions;
    }





    @OneToMany(mappedBy = "question", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.REMOVE })
    private List<Ratings> ratings;

    public List<Ratings> getRatings() {
        return ratings;
    }

    public void setRatings(List<Ratings> ratings) {
        this.ratings = ratings;
    }


    public String getUploadedImageName() {
        return uploadedImageName;
    }

    public void setUploadedImageName(String uploadedImageName) {
        this.uploadedImageName = uploadedImageName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }


    public String getQuestion_subjectArea() {
        return question_subjectArea;
    }

    public void setQuestion_subjectArea(String question_subjectArea) {
        this.question_subjectArea = question_subjectArea;
    }

    public String getQuestionNote() {
        return questionNote;
    }

    public void setQuestionNote(String questionNote) {
        this.questionNote = questionNote;
    }


    public String getFullQuestion() {
        return fullQuestion;
    }

    public void setFullQuestion(String fullQuestion) {
        this.fullQuestion = fullQuestion;
    }

    public String getQuestionstatus() {
        return questionStatus;
    }

    public void setQuestionstatus(String questionstatus) {
        this.questionStatus = questionstatus;
    }

    public String getQuestionSubmittedTime() {
        return questionSubmittedTime;
    }

    public void setQuestionSubmittedTime(String questionSubmittedTime) {
        this.questionSubmittedTime = questionSubmittedTime;
    }

    public String getQuestionAuthorName() {
        return questionAuthorName;
    }

    public void setQuestionAuthorName(String questionAuthorName) {
        this.questionAuthorName = questionAuthorName;
    }

    public String getQuestionAuthorEmail() {
        return questionAuthorEmail;
    }

    public void setQuestionAuthorEmail(String questionAuthorEmail) {
        this.questionAuthorEmail = questionAuthorEmail;
    }
}
