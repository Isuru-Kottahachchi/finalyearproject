package com.smartedulanka.finalyearproject.controller;

import com.smartedulanka.finalyearproject.datalayer.entity.Answer;
import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import com.smartedulanka.finalyearproject.datalayer.entity.User;
import com.smartedulanka.finalyearproject.domain.*;
import com.smartedulanka.finalyearproject.repository.AnswerRepository;
import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import com.smartedulanka.finalyearproject.repository.UserRepository;
import com.smartedulanka.finalyearproject.security.CustomUserDetails;
import com.smartedulanka.finalyearproject.service.*;
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
public class ForumRestController {

    @Autowired
    private RatingCountService ratingCountService;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private PreviouslySavedObjectService previouslySavedObjectService;

    @Autowired
    private PreviousContributionService previousContributionService;

    @Autowired
    private DeleteAnswersHideButtonsService deleteAnswersHideButtonsService;

    @Autowired
    private QuestionStatusService questionStatusService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AnswerAcceptButtonService answerAcceptButtonService;

    @Autowired
    private UserRepository userRepository;


    /*OnLoad functions EndPoints*/

    @PostMapping("/loadVotingButtons")
    public RatingValueOfAUser loadVotingButtons(@RequestParam Long[] answerIdArray){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();

        HashMap<Long,Long> aUsersRatingValueMap = new HashMap<Long,Long>();

        ArrayList<Long> answerIdList = new ArrayList<Long>();


        for (Long answerId: answerIdArray) {

            Long ratingButtonValue = ratingCountService.retrieveAUsersRatingValue(userId,answerId);
            aUsersRatingValueMap.put(answerId,ratingButtonValue);

        }

        RatingValueOfAUser ratingValueOfAUser = new RatingValueOfAUser();

        ratingValueOfAUser.setSpecificUsersRatingValue(aUsersRatingValueMap);



        /*Hide answer authors rating buttons from their own answers*/

        for (Long answerId: answerIdArray) {

            Long answerAuthorId = answerRepository.getAnswerAuthorId(answerId);

            if(answerAuthorId.equals(userId)){
                answerIdList.add(answerId);
            }
        }

        /*Convert ArrayList to Array*/
        Long[] answer_Id_Array = new Long[answerIdList.size()];
        answer_Id_Array = answerIdList.toArray(answer_Id_Array);

        ratingValueOfAUser.setAnswerIds(answer_Id_Array);
        ratingValueOfAUser.setCurrentLoggedInUserId(userId);

        /*Hide answer authors rating buttons from their own answers*/

        return ratingValueOfAUser;
    }




    @PostMapping("/loadRatingValues")
    public RatingResponseCount passAnswerIdArray(@RequestParam Long[] answerIdArray){

        /*for (Long element: answerIdArray) {
            System.out.println(element);
        }*/

        HashMap<Long, Long> answerIdRatingCountMap = new HashMap<Long,Long>();
        HashMap<Long, Long> answerIdRatingPositiveCountMap = new HashMap<Long,Long>();
        HashMap<Long, Long> answerIdRatingNegativeCountMap = new HashMap<Long,Long>();


        for (Long answerId: answerIdArray) {

            Long count = ratingCountService.retrieveRatingsCount(answerId);
            Long positiveCount = ratingCountService.retrievePositiveRatingsCount(answerId);
            Long negativeCount = ratingCountService.retrieveNegativeRatingsCount(answerId);

            answerIdRatingCountMap.put(answerId,count);
            answerIdRatingPositiveCountMap.put(answerId,positiveCount);
            answerIdRatingNegativeCountMap.put(answerId,negativeCount);

            //System.out.println("Answer id"+" " + answerId + " " + count +" " + "Positive votes "+" "+ positiveCount + " "+"Negative votes "+" "+ negativeCount);

        }
        RatingResponseCount ratingResponseCount = new RatingResponseCount();

        ratingResponseCount.setAnswerIdRatingCount(answerIdRatingCountMap);
        ratingResponseCount.setAnswerIdRatingPositiveCount(answerIdRatingPositiveCountMap);
        ratingResponseCount.setAnswerIdRatingNegativeCount( answerIdRatingNegativeCountMap);


        return ratingResponseCount;
    }







    @PostMapping("/loggedInUserNotificationPanelCheckedTime")
    public void setCurrentlyLoggedInUse(){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();

        User user = userRepository.getById(userId);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        user.setRecentNotificationsCheckedTime(dtf.format(now));

        userRepository.save(user);

    }



    /*Hide or show delete and accept answer button according to the user id when loading */
    @PostMapping("/loadDeleteAcceptAnswerButtons")
    public AnswerDelete deleteAnswers(@RequestParam Long[] answerIdArray){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();

        HashMap<Long,Long>  answerIdUserIdMap = new HashMap<Long,Long>();
        HashMap<Long,Long>  answerIdQuestionAuthorIdMap = new HashMap<Long,Long>();

        for (Long answerId: answerIdArray) {

            Long answerAuthorId = deleteAnswersHideButtonsService.retrieveAnswerAuthorUserId(answerId);

            answerIdUserIdMap.put(answerId,answerAuthorId);


            Long questionId = deleteAnswersHideButtonsService.retrieveQuestionId(answerId);
            Long questionAuthorId = deleteAnswersHideButtonsService.retrieveQuestionAuthorId(questionId);

            answerIdQuestionAuthorIdMap.put(answerId,questionAuthorId);

        }
        String userRole = userRepository.getRole(userId);

        AnswerDelete answerDelete = new AnswerDelete();

        answerDelete.setAnswerUserId(answerIdUserIdMap);
        answerDelete.setCurrentLoggedInUserId(userId);
        answerDelete.setAnswerIdQuestionAuthorId(answerIdQuestionAuthorIdMap);

        answerDelete.setUserRole(userRole);

        return answerDelete;

    }


    @PostMapping("/loadQuestionStatus")
    public QuestionStatusUpdate passQuestionIdArray(@RequestParam Long[] questionIdArray){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();


        HashMap<Long, String> questionIdQuestionStatusMap = new HashMap<Long,String>();
        HashMap<Long,Long> questionIdUserIdMap = new HashMap<Long, Long>();
        //HashMap<Long, String>questionIdUserRole = new HashMap<Long, String>();

        for (Long questionId: questionIdArray) {

            String questionStatus = questionStatusService.retrieveQuestionStatus(questionId);
            Long questionAuthorId = questionStatusService.retrieveUserId(questionId);

            questionIdQuestionStatusMap.put(questionId,questionStatus);
            questionIdUserIdMap.put(questionId,questionAuthorId);

        }

        String userRole = userRepository.getRole(userId);


        QuestionStatusUpdate questionStatusUpdate = new QuestionStatusUpdate();

        questionStatusUpdate.setQuestionIdQuestionStatusValue(questionIdQuestionStatusMap);
        questionStatusUpdate.setQuestionIdWithUserId(questionIdUserIdMap);

        questionStatusUpdate.setUserRole(userRole);
        questionStatusUpdate.setCurrentLoggedInUserId(userId);


        return questionStatusUpdate;
    }

    @PostMapping("/loadAnswerAcceptBtn")
    public AnswerAccept loadAnswerAcceptBtn(@RequestParam Long[] answerIdArray){

        HashMap<Long,String> answerIdAnswerStatusMap = new HashMap<Long,String>();

        for(Long answerId : answerIdArray){

            String answerStatus = answerAcceptButtonService.retrieveAnswerStatus(answerId);

            answerIdAnswerStatusMap.put(answerId,answerStatus);
        }

        AnswerAccept answerAcceptBtn = new AnswerAccept();

        answerAcceptBtn.setAnswerIdAnswerStatus(answerIdAnswerStatusMap);

        return answerAcceptBtn;
    }







    /*Onclick functions EndPoints*/

    @PostMapping("/showContribution")
    public UsersPreviousContribution showContribution(@RequestParam String questionAuthorName){

        Long user_id = previousContributionService.retrieveAuthorId(questionAuthorName);

        String role = previousContributionService.retrieveRole(user_id);
        String joinedDate = previousContributionService.retrieveJoinedDate(user_id);
        Long numberOfQuestions = previousContributionService.retrieveNumberOfQuestions(user_id);
        Long numberOfAnswers = previousContributionService.retrieveNumberOfAnswers(user_id);
        Long numberOfUpVotes = previousContributionService.retrieveNumberOfUpVotes(user_id);
        Long numberOfDownVotes = previousContributionService.retrieveNumberOfDownVotes(user_id);
        Long numberOfAcceptedAnswers = previousContributionService.retrieveNumberOfAcceptedAnswers(user_id);

        UsersPreviousContribution usersPreviousContribution = new UsersPreviousContribution();

        usersPreviousContribution.setJoinedDateAndTime(joinedDate);
        usersPreviousContribution.setRole(role);
        usersPreviousContribution.setNumberOfQuestionAsked(numberOfQuestions);
        usersPreviousContribution.setNumberOfAnswersGiven(numberOfAnswers);
        usersPreviousContribution.setNumberOfUpVotes(numberOfUpVotes);
        usersPreviousContribution.setNumberOfDownVotes(numberOfDownVotes);
        usersPreviousContribution.setNumberOfAcceptedAnswers(numberOfAcceptedAnswers);

        return usersPreviousContribution;
    }



    @PostMapping("/acceptAnswer")
    public QuestionIdStatus acceptAnswer(@RequestParam Long answerId){

        Answer answerOb = previouslySavedObjectService.retrievePreviouslySavedAnswer(answerId);
        Question questionOb = previouslySavedObjectService.retrievePreviouslySavedQuestion(answerOb.getQuestion().getQuestion_id());

        if(answerOb.getAnswerStatus().equals("Not Accepted")){

            answerOb.setAnswerStatus("Answer Accepted");
            questionOb.setQuestionstatus("SOLVED");

            answerRepository.save(answerOb);
            questionRepository.save(questionOb);

        }else if (answerOb.getAnswerStatus().equals("Answer Accepted")){

            answerOb.setAnswerStatus("Not Accepted");
            questionOb.setQuestionstatus("UNSOLVED");

            answerRepository.save(answerOb);
            questionRepository.save(questionOb);
        }

        HashMap<Long, String> questionIdStatusMap = new HashMap<Long, String>();

        questionIdStatusMap.put(answerOb.getQuestion().getQuestion_id(),questionOb.getQuestionstatus());

        QuestionIdStatus questionIdStatus = new QuestionIdStatus();
        questionIdStatus.setQuestionIdStatusValue(questionIdStatusMap);

        return questionIdStatus;
    }



    @PostMapping("/acceptAnswerRealTimeDisplay")
    public AnswerAccept acceptAnswerRealTimeDisplay(@RequestParam Long answerId){

        HashMap<String,String> answerStatusQuestionStatus = new HashMap<String,String>();

        String answerStatus = answerAcceptButtonService.retrieveAnswerStatus(answerId);
        Long question_id = answerAcceptButtonService.retrieveQuestionId(answerId);

        String questionStatus = answerAcceptButtonService.retrieveQuestionStatus(question_id);


        /*if(answerStatus.equals("Not Accepted") && questionStatus.equals("SOLVED")){

            Long acceptedAnswerId = answerAcceptButtonService.retrieveAcceptedAnswerId(question);
            Answer answerOb = answerRepository.getById(acceptedAnswerId);
            answerOb.setAnswerStatus("Not Accepted");

        }*/

        answerStatusQuestionStatus.put(answerStatus,questionStatus);

        AnswerAccept answerAccept = new AnswerAccept();
        answerAccept.setAnswerStatusQuestionStatus(answerStatusQuestionStatus);

        return answerAccept;
    }



}
