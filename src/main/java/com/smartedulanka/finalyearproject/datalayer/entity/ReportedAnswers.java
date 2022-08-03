package com.smartedulanka.finalyearproject.datalayer.entity;

import javax.persistence.*;

@Entity
@Table(name = "reportedanswers")
public class ReportedAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reported_answer_id;

    @Column(nullable = true, unique = false, length = 45)
    private String reporter_Name;

    @Column(nullable = true, unique = false, length = 45)
    private Long id_answer;

    @Column(nullable = true, unique = false, length = 45)
    private String answer_author_name;

    @Column(nullable = true, unique = false, length = 45)
    private String answer_author_email;

    @Column(nullable = true, unique = false, length = 45)
    private String reported_time;

    @Column(nullable = true, unique = false, length = 45)
    private Long reporter_id;

    @Column(nullable = true, unique = false, length = 45)
    private String reportedOrNot;

    @Column(nullable = true, unique = false, length = 45)
    private Long reportedAnswersQuestionId;

    public Long getReportedAnswersQuestionId() {
        return reportedAnswersQuestionId;
    }

    public void setReportedAnswersQuestionId(Long reportedAnswersQuestionId) {
        this.reportedAnswersQuestionId = reportedAnswersQuestionId;
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

    public Long getReported_answer_id() {
        return reported_answer_id;
    }

    public void setReported_answer_id(Long reported_answer_id) {
        this.reported_answer_id = reported_answer_id;
    }

    public String getReporter_Name() {
        return reporter_Name;
    }

    public void setReporter_Name(String reporter_Name) {
        this.reporter_Name = reporter_Name;
    }

    public Long getId_answer() {
        return id_answer;
    }

    public void setId_answer(Long id_answer) {
        this.id_answer = id_answer;
    }

    public String getAnswer_author_name() {
        return answer_author_name;
    }

    public void setAnswer_author_name(String answer_author_name) {
        this.answer_author_name = answer_author_name;
    }

    public String getAnswer_author_email() {
        return answer_author_email;
    }

    public void setAnswer_author_email(String answer_author_email) {
        this.answer_author_email = answer_author_email;
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



/*    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }*/
}
