package com.smartedulanka.finalyearproject.controller;

import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import com.smartedulanka.finalyearproject.datalayer.entity.ReportedQuestions;
import com.smartedulanka.finalyearproject.datalayer.entity.User;
import com.smartedulanka.finalyearproject.domain.ReportQuestion.QuestionReportedOrNot;
import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import com.smartedulanka.finalyearproject.repository.ReportedQuestionsRepository;
import com.smartedulanka.finalyearproject.repository.UserRepository;
import com.smartedulanka.finalyearproject.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class ReportedQuestionsRestController {

    @Autowired
    private ReportedQuestionsRepository reportedQuestionsRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;


    /*Real time report button color change*/
    @PostMapping("/reportQuestion")
    public QuestionReportedOrNot reportQuestion(@RequestParam Long questionId){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();
        String userEmail = customUserDetails.getUsername();
        String userName = customUserDetails.getFullName();



        String questionReportedOrNot = reportedQuestionsRepository.questionReportedOrNot(userId,questionId);
        String questionAuthorName = questionRepository.getQuestionAuthorName(questionId);
        String questionAuthorEmail = questionRepository.getQuestionAuthorEmail(questionId);

        if(questionReportedOrNot == null){

            Question question = questionRepository.getPreviouslySavedQuestion(questionId);


            ReportedQuestions reportedQuestions = new ReportedQuestions();


            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            reportedQuestions.setReported_time(dtf.format(now));


            reportedQuestions.setId_question(questionId);


            reportedQuestions.setQuestion(question);


            reportedQuestions.setReporter_Name(userName);
            reportedQuestions.setReporter_id(userId);
            reportedQuestions.setReportedOrNot("REPORTED");
            reportedQuestions.setQuestion_author_name(questionAuthorName);
            reportedQuestions.setQuestion_author_email(questionAuthorEmail);

            reportedQuestionsRepository.save(reportedQuestions);


        }else if(questionReportedOrNot.equals("REPORTED")){

            ReportedQuestions reportedQuestionsOldOb = reportedQuestionsRepository.getQuestionReportObject(userId,questionId);

            reportedQuestionsOldOb.setReportedOrNot("NOTREPORTED");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            reportedQuestionsOldOb.setReported_time(dtf.format(now));

            reportedQuestionsOldOb.setId_question(questionId);

            reportedQuestionsRepository.save(reportedQuestionsOldOb);



        }else if (questionReportedOrNot.equals("NOTREPORTED")){


            ReportedQuestions reportedQuestionsObj = reportedQuestionsRepository.getQuestionReportObject(userId,questionId);


            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            reportedQuestionsObj.setReported_time(dtf.format(now));

            reportedQuestionsObj.setId_question(questionId);
            reportedQuestionsObj.setReporter_Name(userName);
            reportedQuestionsObj.setReporter_id(userId);
            reportedQuestionsObj.setReportedOrNot("REPORTED");

            reportedQuestionsRepository.save(reportedQuestionsObj);

        }

        QuestionReportedOrNot questionReportedOrNotObj = new QuestionReportedOrNot();

        //reported question realtime display
        String updatedQuestionReportedOrNot = reportedQuestionsRepository.questionReportedOrNot(userId,questionId);


        questionReportedOrNotObj.setQuestionReportedOrNot(updatedQuestionReportedOrNot);


        return questionReportedOrNotObj ;
    }








    @PostMapping("/loadQuestionReportButton")
    public QuestionReportedOrNot loadQuestionReportButton(@RequestParam Long[] questionIdArray){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();


        HashMap<Long,String> questionIdReportStatusMap = new HashMap<>();

        for(Long question_id : questionIdArray){

            String questionReportedOrNotVar = reportedQuestionsRepository.questionReportedOrNot(userId,question_id);

            questionIdReportStatusMap.put(question_id,questionReportedOrNotVar);

        }

        QuestionReportedOrNot questionReportedOrNotObj = new QuestionReportedOrNot();

        questionReportedOrNotObj.setQuestionIdWithReportStatus(questionIdReportStatusMap);




        /*Get logged users question Ids for Hide report button from own questions*/
        ArrayList<Long> loggedUsersQuestionIdArrayList = new ArrayList<>();

        loggedUsersQuestionIdArrayList = questionRepository.getQuestionIds(userId);


        /*Convert ArrayList to a Array*/
        Long[] arr = new Long[loggedUsersQuestionIdArrayList.size()];
        arr = loggedUsersQuestionIdArrayList.toArray(arr);


        String loggedInUsersRole = userRepository.getRole(userId);


        questionReportedOrNotObj.setLoggedUsersQuestionIds(arr);
        questionReportedOrNotObj.setLoggedInUsersRole(loggedInUsersRole);


        return questionReportedOrNotObj;
    }

}
