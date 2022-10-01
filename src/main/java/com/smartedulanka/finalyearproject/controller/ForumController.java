package com.smartedulanka.finalyearproject.controller;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.smartedulanka.finalyearproject.PreviousQuestionSuggestion.QuestionSuggester;
import com.smartedulanka.finalyearproject.datalayer.entity.*;
import com.smartedulanka.finalyearproject.domain.Profile;
import com.smartedulanka.finalyearproject.foulLangugeDetection.AnswerFoulLanguageDetect;
import com.smartedulanka.finalyearproject.foulLangugeDetection.BadWordsAPI;
import com.smartedulanka.finalyearproject.foulLangugeDetection.QuestionFoulLanguageDetect;
import com.smartedulanka.finalyearproject.imageModeration.ImageModeration;
import com.smartedulanka.finalyearproject.repository.*;
import com.smartedulanka.finalyearproject.security.CustomUserDetails;
import com.smartedulanka.finalyearproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ForumController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private PreviouslySavedObjectService previouslySavedObjectService;

    @Autowired
    private PreviouslyAskedQuestionService previouslyAskedQuestionService;



    @Autowired
    private ReportedQuestionsRepository reportedQuestionsRepository;

    @Autowired
    private ReportedAnswersRepository reportedAnswersRepository;

    @Autowired
    private NotificationsRepository notificationsRepository;


    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ImageUploadRecord imageUploadRecord;

    private AmazonClient amazonClient;


    @Autowired
    ForumController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    //@Autowired
    ForumController(ImageModeration imageModeration){this.imageModeration = imageModeration;}


  /* Creating question object*/
    @RequestMapping("/forum.html")
    public String loadForum(Model model) {

        model.addAttribute("question",new Question());

        model.addAttribute("answer",new Answer());

         /*Question objects loads from database to html*/
        List<Question> questionsList = questionRepository.findAll();

        /*Answers objects load from database to html*/
        List<Answer> answerList = answerRepository.findAll();

        /*Question set to descending order new questions first*/
        Collections.reverse(questionsList);
        Collections.reverse(answerList);

        model.addAttribute("questionsList",questionsList);





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





       /*Question Id and Answer objects hash map mapping*/
        Map<Long, List<Answer>> questionAnswerMap =
                answerList.stream().collect(Collectors.groupingBy(answer -> answer.getQuestion().getQuestion_id()));


        model.addAttribute("questionAnswerMap", questionAnswerMap);

        return "forum.html";
    }


    @Autowired
    private QuestionFoulLanguageDetect questionFoulLanguageDetect;


    @Autowired
    private ImageModeration imageModeration;


    @Autowired
    private BadWordsAPI badWordsAPI;


        /*when image submission this will be called*/
    @PostMapping("/saveQuestion")
    public String addQuestion(Question question,@RequestPart(value = "file") MultipartFile file) throws IOException {

        /*Foul language detection*/

        if(questionFoulLanguageDetect.detectFoulLanguage(question).equals("Detected")) {

            return "foulLanguage.html";

        }else if(questionFoulLanguageDetect.detectFoulLanguage(question).equals("Not Detected")){

            if(badWordsAPI.detectFoulLanguageWords(question).equals("Detected")){

                return "foulLanguage.html";
            }else{

                        /*Offensive image detection*/
                       if(file!= null && !file.isEmpty()){

                           try {

                               String result = imageModeration.ImageModerate(file);
                               if(result.equals("Offensive")){

                                   return "offensiveImage.html";
                               }

                           }catch(Exception e){

                               System.out.println(e);
                           }

                       }


                  /*  String photo = file.getOriginalFilename();
                    String bucket = "mbitfyproject";

                    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

                    DetectModerationLabelsRequest request = new DetectModerationLabelsRequest()
                            .withImage(new Image().withS3Object(new S3Object().withName(photo).withBucket(bucket)))
                            .withMinConfidence(60F);
                  */
            }

        }

        try {

            if(file!= null && !file.isEmpty()){

                imageUploadRecord.saveImageUploadRecord(question,amazonClient.uploadImage(file),file.getOriginalFilename());

            }else{

                imageUploadRecord.saveWithoutImageUploadRecord(question);

            }

        }catch(Exception e){

            return "403.html";

        }


        return "questionStatus.html";
    }



    @Autowired
    UserRepository userRepository;

    @Autowired
    private AnswerFoulLanguageDetect answerFoulLanguageDetect;

    @PostMapping("/saveAnswer")
    public String addAnswer(Answer answer,@RequestParam(value = "questionId",required = true)Long questionId,Model model) throws IOException {


        /*Foul language detection*/

        if(answerFoulLanguageDetect.detectFoulLanguageInAnswer(answer).equals("Detected")){

            return "foulLanguage.html";

        }else if(answerFoulLanguageDetect.detectFoulLanguageInAnswer(answer).equals("Not Detected")){

            if(badWordsAPI.detectFoulLanguageWordsInAnswer(answer).equals("Detected")){

                return "foulLanguage.html";
            }

        }
        /*Foul language detection*/


       User user;
       try{

           CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
           Long userId = customUserDetails.getUserId();
           String userEmail = customUserDetails.getUsername();
           String userName = customUserDetails.getFullName();

           user = userRepository.getById(userId);

           answer.setAnswerAuthorName(userName);
           answer.setUser(user);
           answer.setAnswerAuthorEmail(userEmail);
           answer.setAnswerAuthorId(userId);

       }catch (Exception e){

           System.out.println(e);
       }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        answer.setAnswerSubmittedTime(dtf.format(now));
        Question question = questionRepository.getById(questionId);
        answer.setQuestion(question);
        answer.setAnswerStatus("Not Accepted");

       answerRepository.save(answer);







      /*Notifications*/

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();

       Notifications notifications = new Notifications();

        notifications.setQuestionAuthorId(answer.getQuestion().getUser().getUserId());
        notifications.setRespondedFullQuestion(answer.getQuestion().getFullQuestion());
        notifications.setRespondedQuestionId(answer.getQuestion().getQuestion_id());
        notifications.setResponseSubmittedTime(dtf.format(now));
        notifications.setRespondedUserName(answer.getAnswerAuthorName());

        notifications.setAnswer(answer);
        notifications.setQuestion(answer.getQuestion());

        notificationsRepository.save(notifications);

        //return "redirect:/forum.html";

        model.addAttribute("questionId",questionId);
        return "answerStatus";
    }


    @Autowired
    private NotificationService notificationService;

    @GetMapping("/showNotifications")
    public String showNotifications(Model model){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();
        String userName = customUserDetails.getFullName();





        /*Retrieve all notification except currently logged in users answer notification in own questions (Notification type 1)*/
        List<Notifications> notificationsList = notificationService.findNotificationsInId(userId,userName);

        Collections.reverse(notificationsList);

        model.addAttribute("notificationsList", notificationsList);




      /*Show notifications when someone answered on the question that logged in user answered previously  (Notification type 2)*/

       /*Getting logged user previously answered questionIds*/
        ArrayList<Long> loggedUserAnsweredQuestionIdList = notificationsRepository.getLoggedUserAnsweredQuestionIds(userName);


        ArrayList<Notifications> loggedUserAnsweredQuestionNotificationList = new ArrayList<Notifications>();


        //Looping logged in users question ids
        for (Long loggedUserAnsweredQuestionId: loggedUserAnsweredQuestionIdList) {

            //Get answers submitted time of the question
            List<String> answerSubmittedTimeList = notificationsRepository.getAnswersSubmittedTime(userName,loggedUserAnsweredQuestionId);


            //Getting the first answer submitted time of the question
            String firstAnswerSubmittedTime = Collections.min(answerSubmittedTimeList);

            //Getting notification ids of the question which are after first answer submit
            List<Long> notificationIdsList = notificationsRepository.getNotificationIds(userName,loggedUserAnsweredQuestionId,firstAnswerSubmittedTime);

            //Looping notification ids
            for(Long notificationId : notificationIdsList){

                Notifications notificationsObj = notificationsRepository.getNotificationsByQuestionId(userName,notificationId);

                if(notificationsObj != null){

                    loggedUserAnsweredQuestionNotificationList.add(notificationsObj);
                }

            }

        }
        Collections.reverse(loggedUserAnsweredQuestionNotificationList);

        model.addAttribute("loggedUserAnsweredQuestionNotificationList", loggedUserAnsweredQuestionNotificationList);

        return "notification.html";
    }



    @PostMapping("/editQuestion")
    public String editQuestion(@RequestParam("subjectArea") String subjectArea,@RequestParam(value = "questionId",required = true)Long questionId,@RequestParam ("fullQuestion")String fullQuestion,@RequestParam("questionNote")String questionNote){

        Question question = questionRepository.getPreviouslySavedQuestion(questionId);
        System.out.println(questionId);

        question.setQuestion_subjectArea(subjectArea);
        question.setQuestionNote(questionNote);
        question.setFullQuestion(fullQuestion);

        questionRepository.save(question);

        return "redirect:/forum.html";
    }

    @PostMapping("/editProfile")
    public String editProfile(@RequestParam("firstName") String firstName,@RequestParam(value = "lastName",required = true)String lastName,@RequestParam ("email")String email){


        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();


        User user = userRepository.getById(userId);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        userRepository.save(user);


        return "editRedirect.html";
    }





    /*@PostMapping("/editQuestions")
    public String editQuestions(@RequestParam Long questionId){


        return "redirect:/forum.html";
    }*/



    @Autowired
    private QuestionSuggester questionSuggester;

    @GetMapping("/searchForumQuestion")
    public String listSearchRecords(@RequestParam("searchBarInput") String searchBarInput,Model model){

        String processedQuestionText = questionSuggester.analyseQuestion(searchBarInput);


        model.addAttribute("question",new Question());
        model.addAttribute("answer",new Answer());


        List<Question> questionsList = questionRepository.findRelevantQuestions(processedQuestionText);



        /*Answers objects load from database to html*/
        List<Answer> answerList = answerRepository.findAll();

        /*Question set to descending order new questions first*/
        Collections.reverse(questionsList);
        Collections.reverse(answerList);

        model.addAttribute("questionsList",questionsList);

        /*Question Id and Answer hash map mapping*/
        Map<Long, List<Answer>> questionAnswerMap =
                answerList.stream().collect(Collectors.groupingBy(answer -> answer.getQuestion().getQuestion_id()));


        model.addAttribute("questionAnswerMap", questionAnswerMap);








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



        return "forum";

    }





    @PostMapping("/deleteQuestion")
    public String deleteQuestion(@RequestParam Long questionId){

        questionRepository.deleteById(questionId);

        return "redirect:/forum.html";
    }

    @PostMapping("/deleteAnswer")
    public String deleteAnswer(@RequestParam Long answerId){

        /*If delete accepted answer*/
        String answerStatus = answerRepository.getAnswerStatus(answerId);

        if( answerStatus.equals("Answer Accepted")){

            Long questionId = answerRepository.getQuestionsId(answerId);

            String questionStatus = questionRepository.getQuestionStatus(questionId);

            if(questionStatus.equals("SOLVED")){

                Question question = questionRepository.getById(questionId);

                question.setQuestionstatus("UNSOLVED");

            }
        }

        answerRepository.deleteById(answerId);

        return "redirect:/forum.html";
    }


    @GetMapping("/deleteQuestionFromAdminPanel")
    public String deleteQuestionFromAdminPanel(@RequestParam Long questionId){

        questionRepository.deleteById(questionId);

        return "redirect:/reportedQuestion.html";
    }


    @GetMapping("/deleteAnswerFromAdminPanel")
    public String deleteAnswerFromAdminPanel(@RequestParam Long answerId){

        /*If delete accepted answer*/
        String answerStatus = answerRepository.getAnswerStatus(answerId);

        if( answerStatus.equals("Answer Accepted")){

            Long questionId = answerRepository.getQuestionsId(answerId);

            String questionStatus = questionRepository.getQuestionStatus(questionId);

            if(questionStatus.equals("SOLVED")){

                Question question = questionRepository.getById(questionId);

                question.setQuestionstatus("UNSOLVED");

            }
        }

        answerRepository.deleteById(answerId);

        return "redirect:/reportedAnswers.html";
    }









    @GetMapping("/previouslyAskedQuestions")
    public String loadPreviouslyAskedQuestion(Model model){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();

        model.addAttribute("question",new Question());
        model.addAttribute("answer",new Answer());

        /*Question objects loads from database to html*/
        List<Question> questionsList = previouslyAskedQuestionService.findAllQuestionInId(userId);
        List<Answer> answerList = answerRepository.findAll();

        Collections.reverse(questionsList);
        Collections.reverse(answerList);

        model.addAttribute("questionsList",questionsList);




        /*Get unchecked Notifications count and indicate*/

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

    @GetMapping("/previouslyRepliedQuestions")
    public String loadPreviouslyRepliedQuestions(Model model){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();

        /*Get currently logged in users answered question's Ids*/
        ArrayList<Long> questionIdList = answerRepository.getQuestionIds(userId);


        /*Get currently logged in user answered question objects*/
        ArrayList<Question> repliedQuestionsList = new ArrayList<Question>();

        for(Long questionId : questionIdList){

            repliedQuestionsList.add(questionRepository.getQuestionsFromId(questionId));

        }

        model.addAttribute("question",new Question());
        model.addAttribute("answer",new Answer());


        List<Answer> answerList = answerRepository.findAll();

        Collections.reverse(repliedQuestionsList);
        Collections.reverse(answerList);

        model.addAttribute("questionsList",repliedQuestionsList);





        /*Get unchecked Notifications count and indicate*/

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


    @GetMapping("/askedSolvedQuestions")
    public String askedSolvedQuestion(Model model){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();

        model.addAttribute("question",new Question());
        model.addAttribute("answer",new Answer());

        /*Question objects loads from database to html*/
        List<Question> questionsList = questionRepository.getSolvedQuestionsWithId();
        List<Answer> answerList = answerRepository.findAll();

        Collections.reverse(questionsList);
        Collections.reverse(answerList);

        model.addAttribute("questionsList",questionsList);



        /*Get unchecked Notifications count and indicate*/

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
    @GetMapping("/askedUnsolvedQuestions")
    public String loadAskedUnsolvedQuestions(Model model){


        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();

        model.addAttribute("question",new Question());
        model.addAttribute("answer",new Answer());

        /*Question objects loads from database to html*/
        List<Question> questionsList = questionRepository.getUnSolvedQuestionsWithId();
        List<Answer> answerList = answerRepository.findAll();

        Collections.reverse(questionsList);
        Collections.reverse(answerList);

        model.addAttribute("questionsList",questionsList);





        /*Get unchecked Notifications count and indicate*/

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



    @GetMapping("/advancedLevel")
    public String loadAdvancedLevelQuestions(Model model){

        model.addAttribute("question",new Question());
        model.addAttribute("answer",new Answer());

        /*Question objects loads from database to html*/
        List<Question> questionsList = questionRepository.getAdvancedLevelQuestions();
        List<Answer> answerList = answerRepository.findAll();

        Collections.reverse(questionsList);
        Collections.reverse(answerList);

        model.addAttribute("questionsList",questionsList);

        Map<Long, List<Answer>> questionAnswerMap =
                answerList.stream().collect(Collectors.groupingBy(answer -> answer.getQuestion().getQuestion_id()));


        model.addAttribute("questionAnswerMap", questionAnswerMap);




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



        return "forum.html";

    }

    @GetMapping("/advancedLevelBiology")
    public String loadAdvancedLevelBiologyQuestions(Model model){

        model.addAttribute("question",new Question());
        model.addAttribute("answer",new Answer());

        /*Question objects loads from database to html*/
        List<Question> questionsList = questionRepository.getAdvancedLevelBiologyQuestions();
        List<Answer> answerList = answerRepository.findAll();

        Collections.reverse(questionsList);
        Collections.reverse(answerList);

        model.addAttribute("questionsList",questionsList);

        Map<Long, List<Answer>> questionAnswerMap =
                answerList.stream().collect(Collectors.groupingBy(answer -> answer.getQuestion().getQuestion_id()));

        model.addAttribute("questionAnswerMap", questionAnswerMap);



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




        return "forum.html";

    }

    @GetMapping("/advancedLevelChemistry")
    public String loadAdvancedLevelChemistryQuestions(Model model){

        model.addAttribute("question",new Question());
        model.addAttribute("answer",new Answer());

        /*Question objects loads from database to html*/
        List<Question> questionsList = questionRepository.getAdvancedLevelChemistryQuestions();
        List<Answer> answerList = answerRepository.findAll();

        Collections.reverse(questionsList);
        Collections.reverse(answerList);

        model.addAttribute("questionsList",questionsList);

        Map<Long, List<Answer>> questionAnswerMap =
                answerList.stream().collect(Collectors.groupingBy(answer -> answer.getQuestion().getQuestion_id()));


        model.addAttribute("questionAnswerMap", questionAnswerMap);




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


        return "forum.html";

    }


    @GetMapping("/ordinaryLevel")
    public String loadOrdinaryLevelQuestions(Model model){

        model.addAttribute("question",new Question());
        model.addAttribute("answer",new Answer());

        /*Question objects loads from database to html*/
        List<Question> questionsList = questionRepository. getOrdinaryLevelQuestions();
        List<Answer> answerList = answerRepository.findAll();

        Collections.reverse(questionsList);
        Collections.reverse(answerList);

        model.addAttribute("questionsList",questionsList);

        Map<Long, List<Answer>> questionAnswerMap =
                answerList.stream().collect(Collectors.groupingBy(answer -> answer.getQuestion().getQuestion_id()));


        model.addAttribute("questionAnswerMap", questionAnswerMap);





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




        return "forum.html";

    }
    @Autowired
    private PreviousContributionService previousContributionService;

    @GetMapping("/viewProfile")
    public String loadProfile(Long questionId,Model model){


        Long questionAuthorId = questionRepository.getQuestionAuthorId(questionId);

        String fullName = questionRepository.getQuestionAuthorName(questionId);
        String userEmail = questionRepository.getQuestionAuthorEmail(questionId);
        String role = previousContributionService.retrieveRole(questionAuthorId);
        String joinedDate = previousContributionService.retrieveJoinedDate(questionAuthorId);
        Long numberOfQuestions = previousContributionService.retrieveNumberOfQuestions(questionAuthorId);
        Long numberOfAnswers = previousContributionService.retrieveNumberOfAnswers(questionAuthorId);
        Long numberOfUpVotes = previousContributionService.retrieveNumberOfUpVotes(questionAuthorId);
        Long numberOfDownVotes = previousContributionService.retrieveNumberOfDownVotes(questionAuthorId);
        Long numberOfAcceptedAnswers = previousContributionService.retrieveNumberOfAcceptedAnswers(questionAuthorId);

        Profile profile = new Profile();

        profile.setQuestionAuthorId(questionAuthorId);

        profile.setFullName(fullName);
        profile.setEmail(userEmail);
        profile.setRole(role);
        profile.setJoinedDateAndTime(joinedDate);
        profile.setNumberOfAcceptedAnswers(numberOfAcceptedAnswers);
        profile.setNumberOfQuestionAsked(numberOfQuestions);
        profile.setNumberOfAnswersGiven(numberOfAnswers);
        profile.setNumberOfUpVotes(numberOfUpVotes);
        profile.setNumberOfDownVotes(numberOfDownVotes);


        ArrayList<Profile> profileList = new ArrayList<Profile>();

        profileList.add(profile);

        model.addAttribute( "profileList", profileList);



        return "profile.html";
    }



  }


