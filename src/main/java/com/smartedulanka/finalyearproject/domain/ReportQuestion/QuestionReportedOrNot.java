package com.smartedulanka.finalyearproject.domain.ReportQuestion;

import java.util.HashMap;

public class QuestionReportedOrNot {

    HashMap<Long,String> questionIdWithReportStatus;

    Long[] loggedUsersQuestionIds;

    String loggedInUsersRole;

    String questionReportedOrNot;




    public HashMap<Long, String> getQuestionIdWithReportStatus() {
        return questionIdWithReportStatus;
    }

    public void setQuestionIdWithReportStatus(HashMap<Long, String> questionIdWithReportStatus) {
        this.questionIdWithReportStatus = questionIdWithReportStatus;
    }

    public Long[] getLoggedUsersQuestionIds() {
        return loggedUsersQuestionIds;
    }

    public void setLoggedUsersQuestionIds(Long[] loggedUsersQuestionIds) {
        this.loggedUsersQuestionIds = loggedUsersQuestionIds;
    }

    public String getLoggedInUsersRole() {
        return loggedInUsersRole;
    }

    public void setLoggedInUsersRole(String loggedInUsersRole) {
        this.loggedInUsersRole = loggedInUsersRole;
    }

    public String getQuestionReportedOrNot() {
        return questionReportedOrNot;
    }

    public void setQuestionReportedOrNot(String questionReportedOrNot) {
        this.questionReportedOrNot = questionReportedOrNot;
    }
}
