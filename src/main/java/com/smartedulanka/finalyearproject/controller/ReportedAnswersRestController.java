package com.smartedulanka.finalyearproject.controller;

import com.smartedulanka.finalyearproject.datalayer.entity.Answer;
import com.smartedulanka.finalyearproject.datalayer.entity.ReportedAnswers;
import com.smartedulanka.finalyearproject.datalayer.entity.User;
import com.smartedulanka.finalyearproject.domain.ReportAnswer.AnswerReportedOrNot;
import com.smartedulanka.finalyearproject.repository.AnswerRepository;
import com.smartedulanka.finalyearproject.repository.ReportedAnswersRepository;
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
public class ReportedAnswersRestController {

    @Autowired
    private ReportedAnswersRepository reportedAnswersRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;


    /*Real time report button color change*/
    @PostMapping("/reportAnswer")
    public AnswerReportedOrNot reportAnswer(@RequestParam Long answerId){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();
        String userName = customUserDetails.getFullName();

        String answerReportedOrNot = reportedAnswersRepository.answerReportedOrNot(userId,answerId);
        String answerAuthorName = answerRepository.getAnswerAuthorName(answerId);
        String answerAuthorEmail = answerRepository.getAnswerAuthorEmail(answerId);

        if(answerReportedOrNot == null){

            Answer answer = answerRepository.getPreviouslySavedAnswer(answerId);


            /*Get questionId of the answer(Need questionId for load reported answers in admin panel)*/
            long reportedAnswersQuestionId = answerRepository.getReportedAnswersQuestionId(answerId);

            ReportedAnswers reportedAnswers = new ReportedAnswers();


            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            reportedAnswers.setReported_time(dtf.format(now));

            reportedAnswers.setId_answer(answerId);

            reportedAnswers.setAnswer(answer);

            reportedAnswers.setReporter_Name(userName);
            reportedAnswers.setReporter_id(userId);
            reportedAnswers.setReportedOrNot("REPORTED");
            reportedAnswers.setReportedAnswersQuestionId(reportedAnswersQuestionId);
            reportedAnswers.setAnswer_author_name(answerAuthorName);
            reportedAnswers.setAnswer_author_email(answerAuthorEmail);

            reportedAnswersRepository.save(reportedAnswers);


        }else if(answerReportedOrNot.equals("REPORTED")){


            ReportedAnswers reportedAnswersOldOb = reportedAnswersRepository.getAnswerReportObject(userId,answerId);


            reportedAnswersOldOb.setReportedOrNot("NOTREPORTED");


            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            reportedAnswersOldOb.setReported_time(dtf.format(now));

            reportedAnswersOldOb.setId_answer(answerId);

            reportedAnswersRepository.save(reportedAnswersOldOb);

        }else if (answerReportedOrNot.equals("NOTREPORTED")){


            ReportedAnswers reportedAnswersObj = reportedAnswersRepository.getAnswerReportObject(userId,answerId);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            reportedAnswersObj.setReported_time(dtf.format(now));


            reportedAnswersObj.setId_answer(answerId);
            reportedAnswersObj.setReporter_Name(userName);
            reportedAnswersObj.setReporter_id(userId);
            reportedAnswersObj.setReportedOrNot("REPORTED");

            reportedAnswersRepository.save(reportedAnswersObj);

        }


        AnswerReportedOrNot answerReportedOrNotObj = new  AnswerReportedOrNot();

        /*Get answer status after clicking report answer button*/
        String UpdatedAnswerReportedOrNot = reportedAnswersRepository.answerReportedOrNot(userId,answerId);

        answerReportedOrNotObj.setAnswerReportedOrNot(UpdatedAnswerReportedOrNot);

        return answerReportedOrNotObj ;
    }






    @PostMapping("/loadAnswerReportButton")
    public AnswerReportedOrNot loadAnswerReportButton(@RequestParam Long[] answerIdArray){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();

        HashMap<Long,String> answerIdReportStatusMap = new HashMap<>();



        /*Get logged in users answer Ids for hide report button from own answers*/
        ArrayList<Long> loggedUsersAnswerIdArrayList = new ArrayList<>();

        loggedUsersAnswerIdArrayList = answerRepository.getAnswerIds(userId);

        /*Convert ArrayList to a Array*/
        Long[] arr = new Long[loggedUsersAnswerIdArrayList.size()];
        arr = loggedUsersAnswerIdArrayList.toArray(arr);

        String loggedInUsersRole = userRepository.getRole(userId);


        for(Long answer_id : answerIdArray){

            String answerReportedOrNotVar = reportedAnswersRepository.answerReportedOrNot(userId,answer_id);

            answerIdReportStatusMap.put(answer_id,answerReportedOrNotVar);

        }

        AnswerReportedOrNot answerReportedOrNotObj = new AnswerReportedOrNot();

        answerReportedOrNotObj.setAnswerIdWithReportStatus(answerIdReportStatusMap);
        answerReportedOrNotObj.setLoggedUsersAnswerIds(arr);
        answerReportedOrNotObj.setLoggedInUsersRole(loggedInUsersRole);


        return answerReportedOrNotObj;
    }



    /*Indicate reported answers to the admin users(Indicate as users have reported this answer in forum)*/
    @PostMapping("/loadAnswerReportStatusForAdmins")
    public AnswerReportedOrNot loadAnswerReportStatusForAdmins(@RequestParam Long[] answerIdArray){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();

        String loggedInUsersRole = userRepository.getRole(userId);

        HashMap<Long,String> answerIdReportStatusMap = new HashMap<>();

        for(Long answerId : answerIdArray){

            String answerReportStatus = reportedAnswersRepository.getReportStatus(answerId);
            answerIdReportStatusMap.put(answerId,answerReportStatus);

        }

        AnswerReportedOrNot answerReportedOrNotObject = new AnswerReportedOrNot();
        answerReportedOrNotObject.setAnswerIdWithReportStatus(answerIdReportStatusMap);
        answerReportedOrNotObject.setLoggedInUsersRole(loggedInUsersRole);

        return answerReportedOrNotObject;
    }
}
