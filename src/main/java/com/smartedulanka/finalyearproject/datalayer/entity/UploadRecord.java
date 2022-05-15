
package com.smartedulanka.finalyearproject.datalayer.entity;


import javax.persistence.*;

@Entity
@Table(name = "uploadrecord")
public class UploadRecord {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false, length = 1000)
    private String UploadedFileName;

    @Column(nullable = false, unique = false, length = 100)
    private String reviewStatus;
/*
    @Column(nullable = false, unique = true, length = 100)
    private Long uploaderId;*/


    @Column(nullable = false, unique = false, length = 100)
    private String subjectArea;

    @Column(nullable = false, unique = false, length = 1000)
    private String fileName;

    @Column(nullable = true, unique = false, length = 100)
    private String fileSubmittedTime;

    @Column(nullable = true, unique = false, length = 100)
    private String fileReviewedTime;

    public String getFileReviewedTime() {
        return fileReviewedTime;
    }

    public void setFileReviewedTime(String fileReviewedTime) {
        this.fileReviewedTime = fileReviewedTime;
    }

    public String getFileSubmittedTime() {
        return fileSubmittedTime;
    }

    public void setFileSubmittedTime(String fileSubmittedTime) {
        this.fileSubmittedTime = fileSubmittedTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

/*    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUploadedFileName() {
        return UploadedFileName;
    }

    public void setUploadedFileName(String uploadedFileName) {
        UploadedFileName = uploadedFileName;
    }
    public String isReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}

