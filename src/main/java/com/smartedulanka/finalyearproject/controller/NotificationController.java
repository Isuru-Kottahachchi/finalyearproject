package com.smartedulanka.finalyearproject.controller;

import com.smartedulanka.finalyearproject.datalayer.entity.Answer;
import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import com.smartedulanka.finalyearproject.datalayer.entity.User;
import com.smartedulanka.finalyearproject.repository.AnswerRepository;
import com.smartedulanka.finalyearproject.repository.NotificationsRepository;
import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import com.smartedulanka.finalyearproject.repository.UserRepository;
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
public class NotificationController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationsRepository notificationsRepository;

    @GetMapping("/showNotifiedQuestion")
    public String loadRespondedQuestion(@RequestParam Long question_id, Model model){

        model.addAttribute("question",new Question());
        model.addAttribute("answer",new Answer());

        /*Question objects loads from database to html*/
        List<Question> questionsList = questionRepository.getRespondedQuestion(question_id);
        List<Answer> answerList = answerRepository.findAll();

        Collections.reverse(questionsList);
        Collections.reverse(answerList);

        model.addAttribute("questionsList",questionsList);
        model.addAttribute("AnswersList",answerList);







        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();

        /*Get unchecked Notifications count and indicate*/
        String userName = customUserDetails.getFullName();

        User user = userRepository.getById(userId);



        String recentNotificationCheckedTime = user.getRecentNotificationsCheckedTime();

        Long countOfUncheckedNotifications = notificationsRepository.getNumberOfUncheckedNotifications(userId,recentNotificationCheckedTime,userName);

        /*Get unchecked Notifications*/
        ArrayList<Long> loggedUserAnsweredQuestionIdList = notificationsRepository.getLoggedUserAnsweredQuestionIds(userName);


        List<Long> previouslyAnsweredQuestionsNotificationIdsList = new ArrayList<Long>();


        List<Long> numberOfNotificationsList = new ArrayList<>();


        for (Long loggedUserAnsweredQuestionId: loggedUserAnsweredQuestionIdList) {


            List<String> answerSubmittedTimeList = notificationsRepository.getAnswersSubmittedTime(userName,loggedUserAnsweredQuestionId);

            String firstAnswerSubmittedTime = Collections.min(answerSubmittedTimeList);


            previouslyAnsweredQuestionsNotificationIdsList = notificationsRepository.getUnCheckedNotificationIds(userName,loggedUserAnsweredQuestionId,firstAnswerSubmittedTime,recentNotificationCheckedTime);



            int previouslyAnsweredQuestionsNotificationIdsListSize = previouslyAnsweredQuestionsNotificationIdsList.size();
            Long previouslyAnsweredQuestionsNotificationsCount = new Long(previouslyAnsweredQuestionsNotificationIdsListSize);


            numberOfNotificationsList.add(previouslyAnsweredQuestionsNotificationsCount);

        }

        Long followingQuestionsNotificationsCount = 0L;
        for(Long numberOfNotifications : numberOfNotificationsList){

            followingQuestionsNotificationsCount += numberOfNotifications;
        }

        Long numberOfTotalUncheckedNotifications = countOfUncheckedNotifications + followingQuestionsNotificationsCount;


        model.addAttribute("numberOfUncheckedNotifications",numberOfTotalUncheckedNotifications);








        Map<Long, List<Answer>> questionAnswerMap =
                answerList.stream().collect(Collectors.groupingBy(answer -> answer.getQuestion().getQuestion_id()));


        model.addAttribute("questionAnswerMap", questionAnswerMap);


        return "forum.html";

    }
}
