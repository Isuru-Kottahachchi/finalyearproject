package com.smartedulanka.finalyearproject.controller;

import com.smartedulanka.finalyearproject.datalayer.entity.UploadRecords;
import com.smartedulanka.finalyearproject.datalayer.entity.User;
import com.smartedulanka.finalyearproject.repository.UploadRepository;

import com.smartedulanka.finalyearproject.service.FileRetrieve;
import com.smartedulanka.finalyearproject.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RouteController {

    @RequestMapping("/")
    public String getHomepage() {
        return "index";
    }

    @RequestMapping("/blog.html")
    public String getBlog() {
        return "blog";
    }


    @RequestMapping("/index.html")
    public String getHome(){
        return "index";
    }


    @RequestMapping("/grade5MathematicsSinhalaMedium.html")
    public String loadgrade5SubjectsSinhalaMedium() {
        return  "grade5MathematicsSinhalaMedium.html";
    }

   /* @RequestMapping("/grade5MathematicsSinhalaMedium.html")
    public String loadgrade5SubjectsSinhalaMedium() {
        return  "grade5MathematicsSinhalaMedium.html";
    }*/

    @RequestMapping("/grade5EnvironmentSinhalaMedium.html")
    public String loadgrade5ScienceSinhalaMedium() {
        return  "grade5EnvironmentSinhalaMedium.html";
    }


    @RequestMapping("/OlympiadEnglishMediumUploadStatus.html")
    public String loadOlympiadPhysicsEnglishMediumUploadstatustest() {
        return  "OlympiadEnglishMediumUploadStatus.html";
    }

    @RequestMapping("/advancedLevelScienceEnglishMediumUploadStatus.html")
    public String loadScienceSectionEnglishMedium() {
        return  "advancedLevelScienceEnglishMediumUploadStatus.html";
    }

    @RequestMapping("/OlympiadSinhalaMediumUploadStatus.html")
    public String loadSmUploadStatus() {
        return  "OlympiadSinhalaMediumUploadStatus.html";
    }

    @RequestMapping("/OlympiadTamilMediumUploadStatus.html")
    public String loadTmUploadStatus() {
        return  "OlympiadTamilMediumUploadStatus.html";
    }

    @RequestMapping("/olympiadEmUploadStatus.html")
    public String loadEmMedium() {
        return  "olympiadEmUploadStatus.html";
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








    @RequestMapping("/PrimarySectionEnglishMedium.html")
    public String loadEnglishMediumPrimarySection() {
        return  "PrimarySectionEnglishMedium.html";
    }

    @RequestMapping("/PrimarySectionSinhalaMedium.html")
    public String loadSinhalaMediumPrimarySection() {
        return  "PrimarySectionSinhalaMedium.html";
    }

    @RequestMapping("/PrimarySectionTamilMedium.html")
    public String loadTamilMediumPrimarySection() {
        return  "PrimarySectionTamilMedium.html";
    }

    @RequestMapping("/admin.html")
    public String loadAdmin() {
        return  "admin";
    }


    @RequestMapping("/advancedLevelSinhalaStreams.html")
    public String ALevelSinhalaSubjects() {
        return  "advancedLevelSinhalaStreams.html";
    }

    @RequestMapping("/advancedLevelEnglishStreams.html")
    public String advancedLevelStreams() {
        return  "advancedLevelEnglishStreams.html";
    }

    @RequestMapping("/advancedLevelTamilStreams.html")
    public String advancedLevelTamilStreams() {
        return  "advancedLevelTamilStreams.html";
    }






    @RequestMapping("/recordDeleteConfirmation.html")
    public String  recordDeleteConfirmation() {
        return  "recordDeleteConfirmation.html";
    }


    @RequestMapping("/recordUpdateConfirmation.html")
    public String recordUpdateConfirmation() {
        return  "recordUpdateConfirmation.html";
    }




    @Autowired
    private UploadRepository uploadRepo;

    @Autowired
    private SearchService searchService;

    @Autowired
    private FileRetrieve fileRetrieve;





    @GetMapping("/indexpage")
    public String viewHomePage() {
        return "indexpage";
    }



    /*Load fileReview Html*/
    @GetMapping("/fileReview.html")
    public String listUploadRecord(Model model) {

        List<UploadRecords> pendingFileRecords = uploadRepo.retrieveAllPendingFiles();
        model.addAttribute("pendingFileRecords", pendingFileRecords);

        return "fileReview";
    }

    /*Load approvedFiles Html*/
    @GetMapping("/approvedFiles.html")
    public String listApprovedRecord(Model model) {

        List<UploadRecords> approvedFilesRecords = uploadRepo.retrieveAllApprovedFiles();
        model.addAttribute("approvedFilesRecords", approvedFilesRecords);

        return "approvedFiles.html";
    }

    /*Load rejectedFiles Html*/
    @GetMapping("/rejectedFiles.html")
    public String listRejectedFilesRecord(Model model) {

        List<UploadRecords> rejectedFilesRecords = uploadRepo.retrieveAllRejectedFiles();
        model.addAttribute("rejectedFilesRecords",  rejectedFilesRecords);

        return "rejectedFiles.html";
    }








    /*Search  pending upload records*/
    @GetMapping("/searchPendingRecords")
    public String listSearchRecords(Model model,@Param("keyword") String keyword){

        List<UploadRecords> pendingFileRecords = searchService.listAllSearchedPendingFiles(keyword);
        model.addAttribute("pendingFileRecords", pendingFileRecords);

        return "fileReview";

    }

    /*Search  approved files records*/
    @GetMapping("/searchApprovedFilesRecords")
    public String listSubmittedFilesRecords(Model model,@Param("keyword") String keyword){

        List<UploadRecords> approvedFilesRecords = searchService.listAllSearchedApprovedFilesRecords(keyword);
        model.addAttribute("approvedFilesRecords", approvedFilesRecords);

        return "approvedFiles.html";

    }


    /*Search  rejected files records*/
    @GetMapping("/searchRejectedFilesRecords")
    public String searchRejectedFilesRecords(Model model,@Param("keyword") String keyword){

        List<UploadRecords> rejectedFilesRecords = searchService.listAllSearchedRejectedFilesRecords(keyword);
        model.addAttribute("rejectedFilesRecords", rejectedFilesRecords);

        return "rejectedFiles.html";

    }

    /*Search  registered users records*/
    @GetMapping("/searchRegisteredUsers")
    public String listRegisteredUsers(Model model,@Param("keyword") String keyword){

        List<User> usersList = searchService.listAllSearchedUsers(keyword);
        model.addAttribute("usersList", usersList);

        return "users.html";

    }







  /* File upload*/
    @GetMapping("/advancedlevelScienceEnglish.html")
    public String addFilesSystem(Model model){

        /*List<UploadRecord> records = uploadRepo.findAll();
        model.addAttribute("records", records);*/

        List<UploadRecords> eMCombinedMathsRecords =  fileRetrieve.listCombinedMaths("AdvancedLevelCMathsEnglishMedium");
        model.addAttribute("eMCombinedMathsRecords", eMCombinedMathsRecords);

        List<UploadRecords> eMBiologyRecords = fileRetrieve.listBiology("AdvancedLevelBioEnglishMedium");
        model.addAttribute("eMBiologyRecords", eMBiologyRecords);

        List<UploadRecords> eMChemistryRecords = fileRetrieve.listChemistry("AdvancedLevelChemistryEnglishMedium");
        model.addAttribute("eMChemistryRecords", eMChemistryRecords);

        List<UploadRecords> eMPhysicsRecords = fileRetrieve.listPhysics("AdvancedLevelPhysicsEnglishMedium");
        model.addAttribute("eMPhysicsRecords", eMPhysicsRecords);

        List<UploadRecords> eMInformationTechnologyRecords = fileRetrieve.listInformationTechnology("AdvancedLevelITEnglishMedium");
        model.addAttribute("eMInformationTechnologyRecords", eMInformationTechnologyRecords);


        return "advancedlevelScienceEnglish.html";
    }

    @RequestMapping("/advancedlevelScienceSinhala.html")
    public String addFilesToAdvancedLevelsScienceSinhala(Model model) {

        List<UploadRecords> sMCombinedMathsRecords =  fileRetrieve.listSmCombinedMaths("AdvancedLevelCMathsSinhalaMedium");
        model.addAttribute("sMCombinedMathsRecords", sMCombinedMathsRecords);

        List<UploadRecords> sMBiologyRecords = fileRetrieve.listSmBiology("AdvancedLevelBioSinhalaMedium");
        model.addAttribute("sMBiologyRecords", sMBiologyRecords);

        List<UploadRecords> sMChemistryRecords = fileRetrieve.listSmChemistry("AdvancedLevelChemistrySinhalaMedium");
        model.addAttribute("sMChemistryRecords", sMChemistryRecords);

        List<UploadRecords> sMPhysicsRecords = fileRetrieve.listSmPhysics("AdvancedLevelPhysicsSinhalaMedium");
        model.addAttribute("sMPhysicsRecords", sMPhysicsRecords);

        List<UploadRecords> sMInformationTechnologyRecords = fileRetrieve.listSmInformationTechnology("IT_SinhalaMedium");
        model.addAttribute("sMInformationTechnologyRecords", sMInformationTechnologyRecords);


        return  "advancedlevelScienceSinhala";
    }


    @RequestMapping("/advancedlevelScienceTamil.html")
    public String advancedLevelScienceTamil(Model model) {

        List<UploadRecords> tMCombinedMathsRecords =  fileRetrieve.listTmCombinedMaths("CombinedMathsTamilMedium");
        model.addAttribute("tMCombinedMathsRecords", tMCombinedMathsRecords);

        List<UploadRecords> tMBiologyRecords = fileRetrieve.listTmBiology("BiologyTamilMedium");
        model.addAttribute("tMBiologyRecords", tMBiologyRecords);

        List<UploadRecords> tMChemistryRecords = fileRetrieve.listTmChemistry("ChemistryTamilMedium");
        model.addAttribute("tMChemistryRecords", tMChemistryRecords);

        List<UploadRecords> tMPhysicsRecords = fileRetrieve.listTmPhysics("PhysicsTamilMedium");
        model.addAttribute("tMPhysicsRecords", tMPhysicsRecords);

        List<UploadRecords> tMInformationTechnologyRecords = fileRetrieve.listTmInformationTechnology("IT_TamilMedium");
        model.addAttribute("tMInformationTechnologyRecords", tMInformationTechnologyRecords);


        return  "advancedlevelScienceTamil";
    }

    @RequestMapping("/olympiadEnglishMedium.html")
    public String olympiadEnglishMedium(Model model) {

        List<UploadRecords> eMOlympiadBioRecords =  fileRetrieve.listEmBioOlympiad("OlympiadBioEnglishMedium");
        model.addAttribute("eMOlympiadBioRecords", eMOlympiadBioRecords);

        List<UploadRecords> eMOlympiadChemistryRecords =  fileRetrieve.listEmChemistryOlympiad("OlympiadChemistryEnglishMedium");
        model.addAttribute("eMOlympiadChemistryRecords", eMOlympiadChemistryRecords);

        List<UploadRecords> eMOlympiadPhysicsRecords =  fileRetrieve.listEmPhysicsOlympiad("OlympiadPhysicsEnglishMedium");
        model.addAttribute("eMOlympiadPhysicsRecords", eMOlympiadPhysicsRecords);


        return "olympiadEnglishMedium.html";

    }

    @RequestMapping("/olympiadSinhalaMedium.html")
    public String olympiadSinhalaMedium(Model model) {


        List<UploadRecords> sMOlympiadBioRecords =  fileRetrieve.listSmBioOlympiad("OlympiadBioSinhalaMedium");
        model.addAttribute("sMOlympiadBioRecords", sMOlympiadBioRecords);

        List<UploadRecords> sMOlympiadChemistryRecords =  fileRetrieve.listSmChemistryOlympiad("OlympiadSinhalaEnglishMedium");
        model.addAttribute("sMOlympiadChemistryRecords", sMOlympiadChemistryRecords);

        List<UploadRecords> sMOlympiadPhysicsRecords =  fileRetrieve.listSmPhysicsOlympiad("OlympiadSinhalaEnglishMedium");
        model.addAttribute("sMOlympiadPhysicsRecords", sMOlympiadPhysicsRecords);


        return "olympiadSinhalaMedium.html";

    }

    @RequestMapping("/olympiadTamilMedium.html")
    public String olympiadTamilMedium(Model model) {

        List<UploadRecords> tMOlympiadBioRecords =  fileRetrieve.listTmBioOlympiad("OlympiadBioTamilMedium");
        model.addAttribute("tMOlympiadBioRecords", tMOlympiadBioRecords);

        List<UploadRecords> tMOlympiadChemistryRecords =  fileRetrieve.listTmChemistryOlympiad("OlympiadChemistryTamilMedium");
        model.addAttribute("tMOlympiadChemistryRecords", tMOlympiadChemistryRecords);

        List<UploadRecords> tMOlympiadPhysicsRecords =  fileRetrieve.listTmPhysicsOlympiad("OlympiadPhysicsTamilMedium");
        model.addAttribute("tMOlympiadPhysicsRecords", tMOlympiadPhysicsRecords);


        return "olympiadTamilMedium.html";

    }




    @RequestMapping("/advancedlevelCommerceEnglish")
    public String advancedlevelCommerceEnglish(Model model) {


        List<UploadRecords> eMBStudiesRecords =  fileRetrieve.listEmBStudiesAdvancedLevel("AdvancedLevelBusinessStudiesEnglishMedium");
        model.addAttribute("eMBStudiesRecords", eMBStudiesRecords);

        List<UploadRecords> emAccountingRecords =  fileRetrieve.listEmAccountingAdvancedLevel("AdvancedLevelAccountingEnglishMedium");
        model.addAttribute("emAccountingRecords", emAccountingRecords);

        List<UploadRecords> emEconRecords =  fileRetrieve.listEmEconAdvancedLevel("AdvancedLevelEconEnglishMedium");
        model.addAttribute("emEconRecords", emEconRecords);




        return "advancedlevelCommerceEnglish.html";

    }
    @RequestMapping("/advancedlevelCommerceSinhala.html")
    public String advancedlevelCommerceSinhala(Model model) {

        List<UploadRecords> smStudiesRecords =  fileRetrieve.listSmBStudiesAdvancedLevel("AdvancedLevelBusinessStudiesEnglishMedium");
        model.addAttribute("eMBStudiesRecords", smStudiesRecords);

        List<UploadRecords> smAccountingRecords =  fileRetrieve.listSmAccountingAdvancedLevel("AdvancedLevelAccountingEnglishMedium");
        model.addAttribute("smAccountingRecords", smAccountingRecords);

        List<UploadRecords> smEconRecords =  fileRetrieve.listSmEconAdvancedLevel("AdvancedLevelEconEnglishMedium");
        model.addAttribute("smEconRecords", smEconRecords);




        return "advancedlevelCommerceSinhala.html";

    }
    @RequestMapping("/advancedlevelCommerceTamil.html")
    public String advancedlevelCommerceTamil(Model model) {


        List<UploadRecords> tmBStudiesRecords =  fileRetrieve.listEmBStudiesAdvancedLevel("AdvancedLevelBusinessStudiesEnglishMedium");
        model.addAttribute("tmBStudiesRecords", tmBStudiesRecords);

        List<UploadRecords> tmAccountingRecords =  fileRetrieve.listTmAccountingAdvancedLevel("AdvancedLevelAccountingEnglishMedium");
        model.addAttribute("tmAccountingRecords", tmAccountingRecords);

        List<UploadRecords> tmEconRecords =  fileRetrieve.listEmEconAdvancedLevel("AdvancedLevelEconEnglishMedium");
        model.addAttribute("tmEconRecords", tmEconRecords);




        return "advancedlevelCommerceTamil.html";

    }



    @RequestMapping("/ordinarylevelsinhalamediam.html")
    public String OLevelEnglishSubjects(Model model) {

        List<UploadRecords> sMOlMathematicsRecords =  fileRetrieve.listSmOrdinaryLevelMaths("OlMathematicsSinhalaMedium");
        model.addAttribute("sMOlMathematicsRecords", sMOlMathematicsRecords);

        List<UploadRecords> sMOlScienceRecords =  fileRetrieve.listSmOrdinaryLevelScience("OlScienceSinhalaMedium");
        model.addAttribute("sMOlScienceRecords", sMOlScienceRecords);

        return  "ordinarylevelsinhalamediam.html";
    }

    @RequestMapping("/ordinarylevelenglishmediam.html")
    public String OLevelSinhalaSubjects(Model model) {

        List<UploadRecords> eMOlMathematicsRecords =  fileRetrieve.listEmOrdinaryLevelMaths("OlMathematicsEnglishMedium");
        model.addAttribute("eMOlMathematicsRecords", eMOlMathematicsRecords);

        List<UploadRecords> eMOlScienceRecords =  fileRetrieve.listEmOrdinaryLevelScience("OlScienceEnglishMedium");
        model.addAttribute("eMOlScienceRecords", eMOlScienceRecords);
        return  "ordinarylevelenglishmediam.html";
    }

    @RequestMapping("/ordinaryleveltamilmediam.html")
    public String OLevelTamilSubjects(Model model) {

        List<UploadRecords> tMOlMathematicsRecords =  fileRetrieve.listTmOrdinaryLevelMaths("OlMathematicsTamilMedium");
        model.addAttribute("tMOlMathematicsRecords", tMOlMathematicsRecords);

        List<UploadRecords> tMOlScienceRecords =  fileRetrieve.listTmOrdinaryLevelScience("OlScienceTamilMedium");
        model.addAttribute("tMOlScienceRecords", tMOlScienceRecords);

        return  "ordinaryleveltamilmediam.html";
    }



    @RequestMapping("/OtherGovernmentExamsEnglishmedium.html")
    public String loadEmOtherGovernmentExams(Model model) {

        return "OtherGovernmentExamsEnglishmedium.html";

    }
    @RequestMapping("/OtherGovernmentExamsSinhalamedium.html")
    public String loadSmOtherGovernmentExams(Model model) {

        return "OtherGovernmentExamsSinhalamedium.html";

    }
    @RequestMapping("/OtherGovernmentExamsTamilmedium.html")
    public String loadTmOtherGovernmentExams(Model model) {

        return "OtherGovernmentExamsTamilmedium.html";

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

