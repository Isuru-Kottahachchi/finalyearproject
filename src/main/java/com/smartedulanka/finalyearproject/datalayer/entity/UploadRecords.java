
package com.smartedulanka.finalyearproject.datalayer.entity;


import javax.persistence.*;

@Entity
@Table(name = "uploadrecords")
public class UploadRecords {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uploadRecordId;

    @Column(nullable = false, unique = false, length = 1000)
    private String uploadedFileName;

    @Column(nullable = false, unique = false, length = 100)
    private String reviewStatus;

    @Column(nullable = false, unique = false, length = 100)
    private String subjectArea;

    @Column(nullable = false, unique = false, length = 1000)
    private String fileName;

    @Column(nullable = true, unique = false, length = 100, updatable = false)
    private String fileSubmittedTime;

    @Column(nullable = true, unique = false, length = 100)
    private String fileReviewedTime;

    @Column(nullable = true, unique = false, length = 100)
    private String uploaderEmail;

    @Column(nullable = false, unique = false, length = 100)
    private String uploaderName;





    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Long getUploadRecordId() {
        return uploadRecordId;
    }

    public void setUploadRecordId(Long uploadRecordId) {
        this.uploadRecordId = uploadRecordId;
    }

    public String getUploadedFileName() {
        return uploadedFileName;
    }

    public void setUploadedFileName(String uploadedFileName) {
        this.uploadedFileName = uploadedFileName;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public String isReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getFileReviewedTime() {
        return fileReviewedTime;
    }
    public void setFileReviewedTime(String fileReviewedTime) {
        this.fileReviewedTime = fileReviewedTime;
    }

    public String getUploaderEmail() {
        return uploaderEmail;
    }

    public void setUploaderEmail(String uploaderEmail) {
        this.uploaderEmail = uploaderEmail;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }
}

