package com.smartedulanka.finalyearproject.controller;


import com.smartedulanka.finalyearproject.datalayer.entity.Answer;
import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import com.smartedulanka.finalyearproject.datalayer.entity.ReportedQuestions;
import com.smartedulanka.finalyearproject.datalayer.entity.User;
import com.smartedulanka.finalyearproject.repository.AnswerRepository;
import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import com.smartedulanka.finalyearproject.repository.ReportedQuestionsRepository;
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
public class ReportedQuestionsController {

    @Autowired
    private ReportedQuestionsRepository reportedQuestionsRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

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


        Map<Long, List<Answer>> questionAnswerMap =
                answerList.stream().collect(Collectors.groupingBy(answer -> answer.getQuestion().getQuestion_id()));


        model.addAttribute("questionAnswerMap", questionAnswerMap);


        return "forum.html";

    }
}
