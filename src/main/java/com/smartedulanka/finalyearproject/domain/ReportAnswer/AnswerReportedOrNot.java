package com.smartedulanka.finalyearproject.domain.ReportAnswer;

import java.util.HashMap;

public class AnswerReportedOrNot {

    HashMap<Long,String> answerIdWithReportStatus;

    Long[] loggedUsersAnswerIds;

    String loggedInUsersRole;

    String answerReportedOrNot;

    public HashMap<Long, String> getAnswerIdWithReportStatus() {
        return answerIdWithReportStatus;
    }

    public void setAnswerIdWithReportStatus(HashMap<Long, String> answerIdWithReportStatus) {
        this.answerIdWithReportStatus = answerIdWithReportStatus;
    }

    public Long[] getLoggedUsersAnswerIds() {
        return loggedUsersAnswerIds;
    }

    public void setLoggedUsersAnswerIds(Long[] loggedUsersAnswerIds) {
        this.loggedUsersAnswerIds = loggedUsersAnswerIds;
    }

    public String getLoggedInUsersRole() {
        return loggedInUsersRole;
    }

    public void setLoggedInUsersRole(String loggedInUsersRole) {
        this.loggedInUsersRole = loggedInUsersRole;
    }

    public String getAnswerReportedOrNot() {
        return answerReportedOrNot;
    }

    public void setAnswerReportedOrNot(String answerReportedOrNot) {
        this.answerReportedOrNot = answerReportedOrNot;
    }
}
