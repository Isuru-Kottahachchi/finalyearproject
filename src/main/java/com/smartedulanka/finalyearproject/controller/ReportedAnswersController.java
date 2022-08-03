package com.smartedulanka.finalyearproject.controller;

import com.smartedulanka.finalyearproject.datalayer.entity.*;
import com.smartedulanka.finalyearproject.repository.AnswerRepository;
import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import com.smartedulanka.finalyearproject.repository.ReportedAnswersRepository;
import com.smartedulanka.finalyearproject.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ReportedAnswersController {

    @Autowired
    private ReportedAnswersRepository reportedAnswersRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;



    @GetMapping("/reportedAnswers.html")
    public String loadReportQuestion(Model model) {

        List<ReportedAnswers> reportedAnswersList = reportedAnswersRepository.retrieveAllReportedAnswers();
        model.addAttribute("reportedAnswersList",reportedAnswersList);

        return "reportedAnswers.html";
    }




   /*Show the question with reported answer in admin panel*/
    @GetMapping("/showReportedAnswer")
    public String loadShowReportedAnswer(@RequestParam Long reportedAnswersQuestionId, Model model){

        model.addAttribute("question",new Question());
        model.addAttribute("answer",new Answer());

        /*Question objects loads from database to html*/
        List<Question> questionsList = questionRepository.getReportedQuestion(reportedAnswersQuestionId);
        List<Answer> answerList = answerRepository.findAll();

        Collections.reverse(questionsList);
        Collections.reverse(answerList);

        model.addAttribute("questionsList",questionsList);
        model.addAttribute("AnswersList",answerList);

        Map<Long, List<Answer>> questionAnswerMap =
                answerList.stream().collect(Collectors.groupingBy(answer -> answer.getQuestion().getQuestion_id()));


        model.addAttribute("questionAnswerMap", questionAnswerMap);


        return "forum.html";

    }
}
