package com.smartedulanka.finalyearproject.controller;


import com.smartedulanka.finalyearproject.datalayer.entity.Answer;
import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import com.smartedulanka.finalyearproject.datalayer.entity.ReportedQuestions;
import com.smartedulanka.finalyearproject.datalayer.entity.User;
import com.smartedulanka.finalyearproject.repository.*;
import com.smartedulanka.finalyearproject.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ReportedQuestionsController {

    @Autowired
    private ReportedQuestionsRepository reportedQuestionsRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/reportedQuestion.html")
    public String loadReportQuestion(Model model) {

        List<ReportedQuestions> reportedQuestionsList = reportedQuestionsRepository.retrieveAllReportedQuestions();
        Collections.reverse(reportedQuestionsList);

        model.addAttribute("reportedQuestionsList", reportedQuestionsList);

        return "reportedQuestion.html";
    }





    /*Show the reported question in admin panel*/
    @GetMapping("/showReportedQuestion")
    public String loadShowReportedQuestion(@RequestParam Long question_id, Model model){

        model.addAttribute("question",new Question());
        model.addAttribute("answer",new Answer());

        /*Question objects loads from database to html*/
        List<Question> questionsList = questionRepository.getReportedQuestion(question_id);
        List<Answer> answerList = answerRepository.findAll();

        Collections.reverse(questionsList);
        Collections.reverse(answerList);

        model.addAttribute("questionsList",questionsList);
        model.addAttribute("AnswersList",answerList);







        /*Get unchecked Notifications count and indicate*/
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();
        String userName = customUserDetails.getFullName();

        User user = userRepository.getById(userId);



        String recentNotificationCheckedTime = user.getRecentNotificationsCheckedTime();



        //Getting unchecked notifications count for asked questions(Notification type 1)

        Long countOfUncheckedNotifications = null;

        if(recentNotificationCheckedTime != null){

            countOfUncheckedNotifications = notificationsRepository.getNumberOfUncheckedNotifications(userId,recentNotificationCheckedTime,userName);

        }else if(recentNotificationCheckedTime == null){

            countOfUncheckedNotifications = notificationsRepository.getNumberOfUncheckedNotification(userId,userName);

        }





        /*Get unchecked Following question Notifications(Notifications type 2*/

        //Get logged in users previously replied question Ids
        ArrayList<Long> loggedUserAnsweredQuestionIdList = notificationsRepository.getLoggedUserAnsweredQuestionIds(userName);


        List<Long> previouslyAnsweredQuestionsNotificationIdsList = new ArrayList<Long>();


        List<Long> numberOfNotificationsList = new ArrayList<>();

        //Looping logged in users question ids
        for (Long loggedUserAnsweredQuestionId: loggedUserAnsweredQuestionIdList) {

            //Get answers submitted time of the question
            List<String> answerSubmittedTimeList = notificationsRepository.getAnswersSubmittedTime(userName,loggedUserAnsweredQuestionId);


            //Getting the first answer submitted time of the question
            String firstAnswerSubmittedTime = Collections.min(answerSubmittedTimeList);


            if(recentNotificationCheckedTime == null){

                //Getting previously answered question's notification ids(new users who have not checked notifications at least once.)

                previouslyAnsweredQuestionsNotificationIdsList = notificationsRepository.getUnCheckedNotificationIDs(userName,loggedUserAnsweredQuestionId,firstAnswerSubmittedTime);

            }else{

                //Getting previously answered question's notification ids
                previouslyAnsweredQuestionsNotificationIdsList = notificationsRepository.getUnCheckedNotificationIds(userName,loggedUserAnsweredQuestionId,firstAnswerSubmittedTime,recentNotificationCheckedTime);


            }

            //Convert int to Long

            int previouslyAnsweredQuestionsNotificationIdsListSize = previouslyAnsweredQuestionsNotificationIdsList.size();
            Long previouslyAnsweredQuestionsNotificationsCount = new Long(previouslyAnsweredQuestionsNotificationIdsListSize);


            numberOfNotificationsList.add(previouslyAnsweredQuestionsNotificationsCount);

        }

        //Getting sum of all elements of numberOfNotificationsList (each previously replied questions notifications)
        Long followingQuestionsNotificationsCount = 0L;
        for(Long numberOfNotifications : numberOfNotificationsList){

            followingQuestionsNotificationsCount += numberOfNotifications;
        }

        //int followingQuestionsNotificationsCount = numberOfNotificationsList.size();

        Long numberOfTotalUncheckedNotifications = countOfUncheckedNotifications + followingQuestionsNotificationsCount;

        model.addAttribute("numberOfUncheckedNotifications",numberOfTotalUncheckedNotifications);




        Map<Long, List<Answer>> questionAnswerMap =
                answerList.stream().collect(Collectors.groupingBy(answer -> answer.getQuestion().getQuestion_id()));


        model.addAttribute("questionAnswerMap", questionAnswerMap);


        return "forum.html";

    }
}
