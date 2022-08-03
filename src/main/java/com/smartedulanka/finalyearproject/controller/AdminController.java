package com.smartedulanka.finalyearproject.controller;

import com.smartedulanka.finalyearproject.domain.AdminDashboardDetails;
import com.smartedulanka.finalyearproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ReportedQuestionsRepository reportedQuestionsRepository;

    @Autowired
    private ReportedAnswersRepository reportedAnswersRepository;

    @Autowired
    private UploadRepository uploadRepository;



    @PostMapping("/loadAdminPanel")
    public AdminDashboardDetails loadAdminPanel(){

        Long numberOfPendingFiles = uploadRepository.getPendingFilesCount();
        Long numberOfReportedQuestions = reportedQuestionsRepository.getNumberOfReportedQuestions();
        Long numberOfReportedAnswers = reportedAnswersRepository.getNumberOfReportedAnswers();

        AdminDashboardDetails adminDashboardDetails = new AdminDashboardDetails();

        adminDashboardDetails.setNumberOfReportedQuestions(numberOfReportedQuestions);
        adminDashboardDetails.setNumberOfReportedAnswers(numberOfReportedAnswers);
        adminDashboardDetails.setNumberOfPendingFiles(numberOfPendingFiles);

        return adminDashboardDetails;
    }
}
