package com.smartedulanka.finalyearproject.controller;

import com.smartedulanka.finalyearproject.datalayer.entity.User;
import com.smartedulanka.finalyearproject.datalayer.entity.UploadRecord;
import com.smartedulanka.finalyearproject.repository.UploadRepository;
import com.smartedulanka.finalyearproject.repository.UserRepository;

import com.smartedulanka.finalyearproject.service.FileRetreive;
import com.smartedulanka.finalyearproject.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RouteController {

    @RequestMapping("/")
    public String getHomepage() {
        return "indexnew";
    }

    @RequestMapping("/blog.html")
    public String getBlog() {
        return "blog";
    }



    @RequestMapping("/indexnew.html")
    public String getHome(){
        return "indexnew";
    }

    @RequestMapping("/englishmedium.html")
    public String loadEnglishmedium() {
        return  "englishmedium";
    }

    @RequestMapping("/sinhalamedium.html")
    public String loadSinhalaMedium() {
        return  "sinhalamedium";
    }

    @RequestMapping("/tamilmedium.html")
    public String loadTamilMedium() {
        return  "tamilmedium";
    }

    @RequestMapping("/gradeonescience.html")
    public String gradeOnSceince() {
        return  "gradeonescience";
    }

    @RequestMapping("/admin.html")
    public String loadAdmin() {
        return  "admin";
    }

   /* @RequestMapping("/advancedlevelscienceenglish.html")
    public String advancedLevelEnglish() {
        return  "advancedlevelscienceenglish.html";
    }*/

    @RequestMapping("/advancedLevelSinhalaStreams.html")
    public String ALevelSinhalaSubjects() {
        return  "advancedLevelSinhalaStreams.html";
    }


    @RequestMapping("/ordinarylevelenglishmediam.html")
    public String OLevelSubjects() {
        return  "ordinarylevelenglishmediam.html";
    }


    @RequestMapping("/advancedLevelStreams.html")
    public String advancedLevelStreams() {
        return  "advancedLevelStreams.html";
    }


    @RequestMapping("/advancedlevelsciencesinhala.html")
    public String advancedlevelsciencesinhala() {
        return  "advancedlevelsciencesinhala";
    }

    @RequestMapping("/advancedlevelsciencetamil.html")
    public String advancedlevelsciencetamil() {
        return  "advancedlevelsciencetamil";
    }


    @RequestMapping("/recordDeleteConfirmation.html")
    public String  recordDeleteConfirmation() {
        return  "recordDeleteConfirmation.html";
    }




    @Autowired
    private UploadRepository uploadRepo;


    @Autowired
    private SearchService searchService;

    @Autowired
    private FileRetreive fileRetreive;





    @GetMapping("/indexpage")
    public String viewHomePage() {
        return "indexpage";
    }






    @GetMapping("/filereview.html")
    public String listUploadRecord(Model model) {

       /* List<UploadRecord> records = uploadRepo.findAll();
        model.addAttribute("records", records);*/

        List<UploadRecord> records = uploadRepo.retrievePendingFiles("PENDING");
        model.addAttribute("records", records);

        return "filereview";
    }

    /*Search  pending upload records*/
    @GetMapping("/searchUploadRecords")
    public String listSearchRecords(Model model,@Param("keyword") String keyword){

        List<UploadRecord> records = searchService.listAll(keyword);
        model.addAttribute("records", records);
        /*  model.addAttribute("keyword", keyword);*/


        return "filereview";

    }








    @GetMapping("/approvedFiles.html")
    public String listApprovedRecord(Model model) {

       /* List<UploadRecord> records = uploadRepo.findAll();
        model.addAttribute("records", records);*/

        List<UploadRecord> records = uploadRepo.retrieveAllApprovedFiles("APPROVED");
        model.addAttribute("records", records);

        return "approvedFiles.html";
    }

    /*Search  pending upload records*/
    @GetMapping("/searchSubmittedFilesRecords")
    public String listSubmittedFilesRecords(Model model,@Param("keyword") String keyword){

        List<UploadRecord> records = searchService.listSubmittedFilesRecords(keyword);
        model.addAttribute("records", records);
        /*  model.addAttribute("keyword", keyword);*/


        return "approvedFiles.html";

    }








    @GetMapping("/rejectedFiles.html")
    public String listRejectedFilesRecord(Model model) {

       /* List<UploadRecord> records = uploadRepo.findAll();
        model.addAttribute("records", records);*/

        List<UploadRecord> records = uploadRepo.retrieveAllRejectedFiles("REJECTED");
        model.addAttribute("records", records);

        return "rejectedFiles.html";
    }

    /*Search  rejected upload records*/
    @GetMapping("/searchRejectedFilesRecords")
    public String searchRejectedFilesRecords(Model model,@Param("keyword") String keyword){

        List<UploadRecord> records = searchService.listRejectedFilesRecords(keyword);
        model.addAttribute("records", records);
        /*  model.addAttribute("keyword", keyword);*/


        return "rejectedFiles.html";

    }














  /* File upload*/
    @GetMapping("/advancedlevelscienceenglish.html")
    public String addFilesSystem(Model model){

        List<UploadRecord> records = uploadRepo.findAll();
        model.addAttribute("records", records);

        List<UploadRecord> CombinedMathsRecords =  fileRetreive.listCombinedMaths("CombinedmathsEnglishMedium");
        model.addAttribute("CombinedMathsRecords", CombinedMathsRecords);

        List<UploadRecord> BiologyRecords = fileRetreive.listBiology("BiologyEnglishMedium");
        model.addAttribute("BiologyRecords", BiologyRecords);

        List<UploadRecord> ChemistryRecords = fileRetreive.listChemistry("ChemistryEnglishMedium");
        model.addAttribute("ChemistryRecords", ChemistryRecords);

        List<UploadRecord> PhysicsRecords = fileRetreive.listPhysics("PhysicsEnglishMedium");
        model.addAttribute("PhysicsRecords", PhysicsRecords);

        List<UploadRecord> InformationTechnologyRecords = fileRetreive.listInformationTechnology("IT_EnglishMedium");
        model.addAttribute("InformationTechnologyRecords", InformationTechnologyRecords);

        return "advancedlevelscienceenglish.html";
    }








  /*login page customization  */
   @GetMapping("/403")
    public String error403() {
        return "403";
    }

    @GetMapping("/login.html")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/message")
    public String message() {
        return "message";
    }





























}

