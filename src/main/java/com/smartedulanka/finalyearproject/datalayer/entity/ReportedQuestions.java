package com.smartedulanka.finalyearproject.datalayer.entity;

import javax.persistence.*;

@Entity
@Table(name = "reportedquestions")
public class ReportedQuestions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reported_question_id;

    @Column(nullable = true, unique = false, length = 45)
    private String reporter_Name;

    @Column(nullable = true, unique = false, length = 45)
    private Long id_question;

    @Column(nullable = true, unique = false, length = 45)
    private String question_author_name;

    @Column(nullable = true, unique = false, length = 45)
    private String question_author_email;

    @Column(nullable = true, unique = false, length = 45)
    private String reported_time;

    @Column(nullable = true, unique = false, length = 45)
    private Long reporter_id;

    @Column(nullable = true, unique = false, length = 45)
    private String reportedOrNot;




   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }


    public Long getReported_question_id() {
        return reported_question_id;
    }

    public void setReported_question_id(Long reported_question_id) {
        this.reported_question_id = reported_question_id;
    }

    public String getReporter_Name() {
        return reporter_Name;
    }

    public void setReporter_Name(String reporter_Name) {
        this.reporter_Name = reporter_Name;
    }

    public Long getId_question() {
        return id_question;
    }

    public void setId_question(Long id_question) {
        this.id_question = id_question;
    }


    public String getReported_time() {
        return reported_time;
    }

    public void setReported_time(String reported_time) {
        this.reported_time = reported_time;
    }

    public Long getReporter_id() {
        return reporter_id;
    }

    public void setReporter_id(Long reporter_id) {
        this.reporter_id = reporter_id;
    }

    public String getReportedOrNot() {
        return reportedOrNot;
    }

    public void setReportedOrNot(String reportedOrNot) {
        this.reportedOrNot = reportedOrNot;
    }

    public String getQuestion_author_name() {
        return question_author_name;
    }

    public void setQuestion_author_name(String question_author_name) {
        this.question_author_name = question_author_name;
    }

    public String getQuestion_author_email() {
        return question_author_email;
    }

    public void setQuestion_author_email(String question_author_email) {
        this.question_author_email = question_author_email;
    }
}