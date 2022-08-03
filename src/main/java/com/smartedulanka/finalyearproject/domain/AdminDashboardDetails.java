package com.smartedulanka.finalyearproject.domain;

public class AdminDashboardDetails {

    private Long numberOfPendingFiles;
    private Long numberOfReportedQuestions;
    private Long numberOfReportedAnswers;

    public Long getNumberOfReportedQuestions() {
        return numberOfReportedQuestions;
    }

    public void setNumberOfReportedQuestions(Long numberOfReportedQuestions) {
        this.numberOfReportedQuestions = numberOfReportedQuestions;
    }

    public Long getNumberOfReportedAnswers() {
        return numberOfReportedAnswers;
    }

    public void setNumberOfReportedAnswers(Long numberOfReportedAnswers) {
        this.numberOfReportedAnswers = numberOfReportedAnswers;
    }

    public Long getNumberOfPendingFiles() {
        return numberOfPendingFiles;
    }

    public void setNumberOfPendingFiles(Long numberOfPendingFiles) {
        this.numberOfPendingFiles = numberOfPendingFiles;
    }
}
