package com.smartedulanka.finalyearproject.controller;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.smartedulanka.finalyearproject.PreviousQuestionSuggestion.QuestionSuggester;
import com.smartedulanka.finalyearproject.datalayer.entity.*;
import com.smartedulanka.finalyearproject.foulLangugeDetection.AnswerFoulLanguageDetect;
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

        Long countOfUncheckedNotifications = notificationsRepository.getNumberOfUncheckedNotifications(userId,recentNotificationCheckedTime,userName);

        /*Get unchecked Notifications*/




        ArrayList<Long> loggedUserAnsweredQuestionIdList = notificationsRepository.getLoggedUserAnsweredQuestionIds(userName);


        List<Long> previouslyAnsweredQuestionsNotificationIdsList = new ArrayList<Long>();


        List<Long> numberOfNotificationsList = new ArrayList<>();


        for (Long loggedUserAnsweredQuestionId: loggedUserAnsweredQuestionIdList) {


            List<String> answerSubmittedTimeList = notificationsRepository.getFirstansweredTime(userName,loggedUserAnsweredQuestionId);

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





       /*Question Id and Answer hash map mapping*/
        Map<Long, List<Answer>> questionAnswerMap =
                answerList.stream().collect(Collectors.groupingBy(answer -> answer.getQuestion().getQuestion_id()));


        model.addAttribute("questionAnswerMap", questionAnswerMap);

        return "forum.html";
    }


    @Autowired
    private QuestionFoulLanguageDetect questionFoulLanguageDetect;


    @Autowired
    private ImageModeration imageModeration;


        /*when image submission this will be called*/
    @PostMapping("/questionSave")
    public String addQuestion(Question question,@RequestPart(value = "file") MultipartFile file) throws IOException {

        //imageModeration.uploadFile(file);

        String photo1 = "Image name" + file.getOriginalFilename();

        /*Foul language detection*/

        if(questionFoulLanguageDetect.detectFoulLanguage(question).equals("Detected")){

            return "foulLanguage.html";

        }else {

            imageModeration.uploadFile(file);
            //amazonClient.uploadImage(file);

            //String photo = "input.jpg";
            String photo = file.getOriginalFilename();
            String bucket = "mbitfyproject";

            AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

            DetectModerationLabelsRequest request = new DetectModerationLabelsRequest()
                    .withImage(new Image().withS3Object(new S3Object().withName(photo).withBucket(bucket)))
                    .withMinConfidence(60F);
            try
            {
                DetectModerationLabelsResult result = rekognitionClient.detectModerationLabels(request);
                List<ModerationLabel> labels = result.getModerationLabels();
                System.out.println("Detected labels for " + photo);
                for (ModerationLabel label : labels)
                {
                    System.out.println("Label: " + label.getName()
                            + "\n Confidence: " + label.getConfidence().toString() + "%"
                            + "\n Parent:" + label.getParentName());
                }
            }
            catch (AmazonRekognitionException e)
            {
                e.printStackTrace();
                //System.out.println(e);
            }

        }

        /*Foul language detection*/

        try {

            imageUploadRecord.saveImageUploadRecord(question,amazonClient.uploadImage(file),file.getOriginalFilename());

        }catch(Exception e){

            return "403.html";

        }

       /* question.setImageName();
        question.setUploadedImageName();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        question.setQuestionstatus(false);
        question.setQuestionSubmittedTime(dtf.format(now));
       *//* question.setQuestionAuthorName();*//*

        questionRepository.save(question);*/

        return "questionStatus.html";
    }



    @Autowired
    UserRepository userRepository;

    @Autowired
    private AnswerFoulLanguageDetect answerFoulLanguageDetect;

    @PostMapping("/answerSave")
    public String addAnswer(Answer answer,@RequestParam(value = "questionId",required = true)Long questionId,Model model){


        /*Foul language detection*/

        if(answerFoulLanguageDetect.detectFoulLanguageInAnswer(answer).equals("Detected")){

            return "foulLanguage.html";

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

        return "redirect:/forum.html";
    }


    @Autowired
    private NotificationService notificationService;

    @GetMapping("/showNotifications")
    public String showNotifications(Model model){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();
        String userName = customUserDetails.getFullName();

        /*Retrieve all notification except currently logged in users answer notification in own questions*/
        List<Notifications> notificationsList = notificationService.findNotificationsInId(userId,userName);

        Collections.reverse(notificationsList);

        model.addAttribute("notificationsList", notificationsList);




      /*Show notifications when someone answered on the question that logged user answered previously*/

       /*Getting logged user answered questionIds*/
        ArrayList<Long> loggedUserAnsweredQuestionIdList = notificationsRepository.getLoggedUserAnsweredQuestionIds(userName);


        ArrayList<Notifications> loggedUserAnsweredQuestionNotificationList = new ArrayList<Notifications>();

        for (Long loggedUserAnsweredQuestionId: loggedUserAnsweredQuestionIdList) {


            List<String> answerSubmittedTimeList = notificationsRepository.getFirstansweredTime(userName,loggedUserAnsweredQuestionId);

            String firstAnswerSubmittedTime = Collections.min(answerSubmittedTimeList);


            List<Long> notificationIdsList = notificationsRepository.getNotificationIds(userName,loggedUserAnsweredQuestionId,firstAnswerSubmittedTime);

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

        Long countOfUncheckedNotifications = notificationsRepository.getNumberOfUncheckedNotifications(userId,recentNotificationCheckedTime,userName);

        /*Get unchecked Notifications*/
        ArrayList<Long> loggedUserAnsweredQuestionIdList = notificationsRepository.getLoggedUserAnsweredQuestionIds(userName);


        List<Long> previouslyAnsweredQuestionsNotificationIdsList = new ArrayList<Long>();


        List<Long> numberOfNotificationsList = new ArrayList<>();


        for (Long loggedUserAnsweredQuestionId: loggedUserAnsweredQuestionIdList) {


            List<String> answerSubmittedTimeList = notificationsRepository.getFirstansweredTime(userName,loggedUserAnsweredQuestionId);

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

        Long countOfUncheckedNotifications = notificationsRepository.getNumberOfUncheckedNotifications(userId,recentNotificationCheckedTime,userName);

        /*Get unchecked Notifications*/
        ArrayList<Long> loggedUserAnsweredQuestionIdList = notificationsRepository.getLoggedUserAnsweredQuestionIds(userName);


        List<Long> previouslyAnsweredQuestionsNotificationIdsList = new ArrayList<Long>();


        List<Long> numberOfNotificationsList = new ArrayList<>();


        for (Long loggedUserAnsweredQuestionId: loggedUserAnsweredQuestionIdList) {


            List<String> answerSubmittedTimeList = notificationsRepository.getFirstansweredTime(userName,loggedUserAnsweredQuestionId);

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

        Long countOfUncheckedNotifications = notificationsRepository.getNumberOfUncheckedNotifications(userId,recentNotificationCheckedTime,userName);

        /*Get unchecked Notifications*/
        ArrayList<Long> loggedUserAnsweredQuestionIdList = notificationsRepository.getLoggedUserAnsweredQuestionIds(userName);


        List<Long> previouslyAnsweredQuestionsNotificationIdsList = new ArrayList<Long>();


        List<Long> numberOfNotificationsList = new ArrayList<>();


        for (Long loggedUserAnsweredQuestionId: loggedUserAnsweredQuestionIdList) {


            List<String> answerSubmittedTimeList = notificationsRepository.getFirstansweredTime(userName,loggedUserAnsweredQuestionId);

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

        Long countOfUncheckedNotifications = notificationsRepository.getNumberOfUncheckedNotifications(userId,recentNotificationCheckedTime,userName);

        /*Get unchecked Notifications*/
        ArrayList<Long> loggedUserAnsweredQuestionIdList = notificationsRepository.getLoggedUserAnsweredQuestionIds(userName);


        List<Long> previouslyAnsweredQuestionsNotificationIdsList = new ArrayList<Long>();


        List<Long> numberOfNotificationsList = new ArrayList<>();


        for (Long loggedUserAnsweredQuestionId: loggedUserAnsweredQuestionIdList) {


            List<String> answerSubmittedTimeList = notificationsRepository.getFirstansweredTime(userName,loggedUserAnsweredQuestionId);

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


        return "forum.html";

    }



  }


