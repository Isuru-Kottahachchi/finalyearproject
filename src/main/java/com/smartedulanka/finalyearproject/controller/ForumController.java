package com.smartedulanka.finalyearproject.controller;

import com.smartedulanka.finalyearproject.datalayer.entity.Answer;
import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import com.smartedulanka.finalyearproject.repository.AnswerRepository;
import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import com.smartedulanka.finalyearproject.service.AmazonClient;
import com.smartedulanka.finalyearproject.service.ImageUploadRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Controller
public class ForumController {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;



 @Autowired
 private ImageUploadRecord imageUploadRecord;
    private AmazonClient amazonClient;

    @Autowired
    ForumController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }







  /* Creating question object*/
    @RequestMapping("/forum.html")
    public String loadForum(Model model) {
        model.addAttribute("question",new Question());

        model.addAttribute("answer",new Answer());

         /*Question objects loads from database to html*/
        List<Question> listQuestions = questionRepository.findAll();

        /*Answers objects load from database to html*/
        List<Answer> listAnswer = answerRepository.findAll();

        /*Question set to descending order new questions first*/
        Collections.reverse(listQuestions);
        Collections.reverse(listAnswer);

        model.addAttribute("listQuestions", listQuestions);
        model.addAttribute("listAnswer", listAnswer);
        return "forum.html";
    }





        /*After for submission this will be called*/
    @PostMapping("/questionsave")
    public String addQuestion(Question question,@RequestPart(value = "file") MultipartFile file) throws IOException {


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


    @PostMapping("/answersave")
    public String addAnswer(Answer answer){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        answer.setAnswerSubmittedTime(dtf.format(now));

       answerRepository.save(answer);

        return "questionStatus.html";
    }

}


