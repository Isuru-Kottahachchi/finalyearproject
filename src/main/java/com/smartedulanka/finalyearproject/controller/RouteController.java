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

import java.util.Collections;
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
    public String loadGrade5SubjectsSinhalaMedium() {
        return  "grade5MathematicsSinhalaMedium.html";
    }

   /* @RequestMapping("/grade5MathematicsSinhalaMedium.html")
    public String loadGrade5SubjectsSinhalaMedium() {
        return  "grade5MathematicsSinhalaMedium.html";
    }*/

    @RequestMapping("/grade5EnvironmentSinhalaMedium.html")
    public String loadGrade5ScienceSinhalaMedium() {
        return  "grade5EnvironmentSinhalaMedium.html";
    }




    @RequestMapping("/uploadStatus.html")
    public String loadScienceSectionEnglishMedium() {
        return  "uploadStatus.html";
    }








    @RequestMapping("/englishMedium.html")
    public String loadEnglishMdium() {
        return  "englishMedium";
    }

    @RequestMapping("/sinhalaMedium.html")
    public String loadSinhalaMedium() {
        return  "sinhalaMedium";
    }

    @RequestMapping("/tamilMedium.html")
    public String loadTamilMedium() {
        return  "tamilMedium";
    }








    @RequestMapping("/primarySectionEnglishMedium.html")
    public String loadEnglishMediumPrimarySection() {
        return  "primarySectionEnglishMedium.html";
    }

    @RequestMapping("/primarySectionSinhalaMedium.html")
    public String loadSinhalaMediumPrimarySection() {
        return  "primarySectionSinhalaMedium.html";
    }

    @RequestMapping("/primarySectionTamilMedium.html")
    public String loadTamilMediumPrimarySection() {
        return  "primarySectionTamilMedium.html";
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

        Collections.reverse(pendingFileRecords);
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







  /* File upload Approved files Load*/
    @GetMapping("/advancedLevelScienceEnglishMedium.html")
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


        return "advancedLevelScienceEnglishMedium.html";
    }

    @RequestMapping("/advancedLevelScienceSinhalaMedium.html")
    public String addFilesToAdvancedLevelsScienceSinhala(Model model) {

        List<UploadRecords> sMCombinedMathsRecords =  fileRetrieve.listSmCombinedMaths("AdvancedLevelCMathsSinhalaMedium");
        model.addAttribute("sMCombinedMathsRecords", sMCombinedMathsRecords);

        List<UploadRecords> sMBiologyRecords = fileRetrieve.listSmBiology("AdvancedLevelBioSinhalaMedium");
        model.addAttribute("sMBiologyRecords", sMBiologyRecords);

        List<UploadRecords> sMChemistryRecords = fileRetrieve.listSmChemistry("AdvancedLevelChemistrySinhalaMedium");
        model.addAttribute("sMChemistryRecords", sMChemistryRecords);

        List<UploadRecords> sMPhysicsRecords = fileRetrieve.listSmPhysics("AdvancedLevelPhysicsSinhalaMedium");
        model.addAttribute("sMPhysicsRecords", sMPhysicsRecords);


        return  "advancedLevelScienceSinhalaMedium";
    }


    @RequestMapping("/advancedLevelScienceTamilMedium.html")
    public String advancedLevelScienceTamil(Model model) {

        List<UploadRecords> tMCombinedMathsRecords =  fileRetrieve.listTmCombinedMaths("CombinedMathsTamilMedium");
        model.addAttribute("tMCombinedMathsRecords", tMCombinedMathsRecords);

        List<UploadRecords> tMBiologyRecords = fileRetrieve.listTmBiology("BiologyTamilMedium");
        model.addAttribute("tMBiologyRecords", tMBiologyRecords);

        List<UploadRecords> tMChemistryRecords = fileRetrieve.listTmChemistry("ChemistryTamilMedium");
        model.addAttribute("tMChemistryRecords", tMChemistryRecords);

        List<UploadRecords> tMPhysicsRecords = fileRetrieve.listTmPhysics("PhysicsTamilMedium");
        model.addAttribute("tMPhysicsRecords", tMPhysicsRecords);


        return  "advancedLevelScienceTamilMedium";
    }

    @GetMapping("/advancedLevelITEnglishMedium.html")
    public String addAdvancedLevelITEnglishMedium(Model model){


        List<UploadRecords> eMInformationTechnologyRecords = fileRetrieve.listEmInformationTechnology("AdvancedLevelITEnglishMedium");
        model.addAttribute("eMInformationTechnologyRecords", eMInformationTechnologyRecords);


        return "advancedLevelITEnglishMedium";
    }

    @GetMapping("/advancedLevelITSinhalaMedium.html")
    public String addAdvancedLevelITSinhalaMedium(Model model){

        List<UploadRecords> sMInformationTechnologyRecords = fileRetrieve.listSmInformationTechnology("AdvancedLevelITSinhalaMedium");
        model.addAttribute("sMInformationTechnologyRecords", sMInformationTechnologyRecords);


        return "advancedLevelITSinhalaMedium";
    }

    @GetMapping("/advancedLevelITTamilMedium.html")
    public String addAdvancedLevelITTamilMedium(Model model){

        List<UploadRecords> tMInformationTechnologyRecords = fileRetrieve.listTmInformationTechnology("AdvancedLevelITTamilMedium");
        model.addAttribute("tMInformationTechnologyRecords", tMInformationTechnologyRecords);


        return "advancedLevelITSinhalaMedium";
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




    @RequestMapping("/advancedLevelCommerceEnglishMedium")
    public String advancedLevelCommerceEnglish(Model model) {


        List<UploadRecords> eMAdvancedLevelBStudiesRecords =  fileRetrieve.listEmBStudiesAdvancedLevel("AdvancedLevelBusinessStudiesEnglishMedium");
        model.addAttribute("eMAdvancedLevelBStudiesRecords", eMAdvancedLevelBStudiesRecords);

        List<UploadRecords> eMAdvancedLevelAccountingRecords =  fileRetrieve.listEmAccountingAdvancedLevel("AdvancedLevelAccountingEnglishMedium");
        model.addAttribute("eMAdvancedLevelAccountingRecords", eMAdvancedLevelAccountingRecords);

        List<UploadRecords> eMAdvancedLevelEconRecords =  fileRetrieve.listEmEconAdvancedLevel("AdvancedLevelEconomicsEnglishMedium");
        model.addAttribute("eMAdvancedLevelEconRecords", eMAdvancedLevelEconRecords);


        return "advancedLevelCommerceEnglishMedium.html";

    }
    @RequestMapping("/advancedLevelCommerceSinhalaMedium.html")
    public String advancedLevelCommerceSinhala(Model model) {

        List<UploadRecords> sMAdvancedLevelBStudiesRecords =  fileRetrieve.listSmBStudiesAdvancedLevel("AdvancedLevelBStudiesSinhalaMedium");
        model.addAttribute("sMAdvancedLevelBStudiesRecords", sMAdvancedLevelBStudiesRecords);

        List<UploadRecords> sMAdvancedLevelAccountingRecords =  fileRetrieve.listSmAccountingAdvancedLevel("AdvancedLevelAccountingSinhalaMedium");
        model.addAttribute("sMAdvancedLevelAccountingRecords", sMAdvancedLevelAccountingRecords);

        List<UploadRecords> sMAdvancedLevelEconRecords =  fileRetrieve.listSmEconAdvancedLevel("AdvancedLevelEconomicsSinhalaMedium");
        model.addAttribute("sMAdvancedLevelEconRecords", sMAdvancedLevelEconRecords);


        return "advancedLevelCommerceSinhalaMedium.html";

    }
    @RequestMapping("/advancedLevelCommerceTamilMedium.html")
    public String advancedLevelCommerceTamil(Model model) {


        List<UploadRecords> tMAdvancedLevelBStudiesRecords =  fileRetrieve.listTmBStudiesAdvancedLevel("AdvancedLevelBusinessStudiesTamilMedium");
        model.addAttribute("tMAdvancedLevelBStudiesRecords", tMAdvancedLevelBStudiesRecords);

        List<UploadRecords> tMAdvancedLevelAccountingRecords =  fileRetrieve.listTmAccountingAdvancedLevel("AdvancedLevelAccountingTamilMedium");
        model.addAttribute("tmAdvancedLevelAccountingRecords", tMAdvancedLevelAccountingRecords);

        List<UploadRecords> tMAdvancedLevelEconRecords =  fileRetrieve.listTmEconAdvancedLevel("AdvancedLevelEconomicsTamilMedium");
        model.addAttribute("tMAdvancedLevelEconRecords", tMAdvancedLevelEconRecords);


        return "advancedLevelCommerceTamilMedium.html";

    }


    @RequestMapping("/advancedLevelArtSinhalaMedium.html")
    public String advancedLevelArtSinhalaMedium(Model model) {

        List<UploadRecords> sMAdvancedLevelGeographyRecords =  fileRetrieve.listSmGeographyAdvancedLevel("AdvancedLevelGeographySinhalaMedium");
        model.addAttribute("sMAdvancedLevelGeographyRecords", sMAdvancedLevelGeographyRecords);

        List<UploadRecords> sMAdvancedLevelHomeScienceRecords =  fileRetrieve.listSmHomeScienceAdvancedLevel("AdvancedLevelHomeScienceSinhalaMedium");
        model.addAttribute("sMAdvancedLevelHomeScienceRecords", sMAdvancedLevelHomeScienceRecords);

/*        List<UploadRecords> sMAdvancedLevelEconRecords =  fileRetrieve.listSmEconAdvancedLevel("AdvancedLevelEconomicsEnglishMedium");
        model.addAttribute("sMAdvancedLevelEconRecords", sMAdvancedLevelEconRecords);*/


        return "advancedLevelArtSinhalaMedium.html";

    }
    @RequestMapping("/advancedLevelArtTamilMedium.html")
    public String advancedLevelArtTamilMedium(Model model) {

        List<UploadRecords> tMAdvancedLevelGeographyRecords =  fileRetrieve.listTmGeographyAdvancedLevel("AdvancedLevelGeographyTamilMedium");
        model.addAttribute("tMAdvancedLevelGeographyRecords", tMAdvancedLevelGeographyRecords);

        List<UploadRecords> tMAdvancedLevelHomeScienceRecords =  fileRetrieve.listTmHomeScienceAdvancedLevel("AdvancedLevelHomeScienceTamilMedium");
        model.addAttribute("tMAdvancedLevelHomeScienceRecords", tMAdvancedLevelHomeScienceRecords);

/*        List<UploadRecords> sMAdvancedLevelEconRecords =  fileRetrieve.listSmEconAdvancedLevel("AdvancedLevelEconomicsEnglishMedium");
        model.addAttribute("sMAdvancedLevelEconRecords", sMAdvancedLevelEconRecords);*/


        return "advancedLevelArtTamilMedium.html";

    }



    @RequestMapping("/ordinaryLevelSinhalaMedium.html")
    public String OLevelEnglishSubjects(Model model) {

        List<UploadRecords> sMOlMathematicsRecords =  fileRetrieve.listSmOrdinaryLevelMaths("OrdinaryLevelMathematicsSinhalaMedium");
        model.addAttribute("sMOrdinaryLevelMathematicsRecords", sMOlMathematicsRecords);

        List<UploadRecords> sMOrdinaryLevelScienceRecords =  fileRetrieve.listSmOrdinaryLevelScience("OrdinaryLevelScienceSinhalaMedium");
        model.addAttribute("sMOrdinaryLevelScienceRecords", sMOrdinaryLevelScienceRecords);

        List<UploadRecords> sMOrdinaryLevelITRecords =  fileRetrieve.listSmOrdinaryLevelIT("OrdinaryLevelITSinhalaMedium");
        model.addAttribute(" sMOrdinaryLevelITRecords", sMOrdinaryLevelITRecords);

        List<UploadRecords> sMOrdinaryLevelBusinessSRecords =  fileRetrieve.listSmOrdinaryLevelBusinessS("OrdinaryLevelBusinessSSinhalaMedium");
        model.addAttribute("sMOrdinaryLevelBusinessSRecords", sMOrdinaryLevelBusinessSRecords);

        List<UploadRecords> sMOrdinaryLevelHistoryRecords =  fileRetrieve.listSmOrdinaryLevelHistory("OrdinaryLevelHistorySinhalaMedium");
        model.addAttribute("sMOrdinaryLevelHistoryRecords", sMOrdinaryLevelHistoryRecords);

        return  "ordinaryLevelSinhalaMedium.html";
    }

    @RequestMapping("/ordinaryLevelEnglishMedium.html")
    public String OLevelSinhalaSubjects(Model model) {

        List<UploadRecords> eMOrdinaryLevelMathematicsRecords =  fileRetrieve.listEmOrdinaryLevelMaths("OrdinaryLevelMathsEnglishMedium");
        model.addAttribute("eMOrdinaryLevelMathematicsRecords", eMOrdinaryLevelMathematicsRecords);

        List<UploadRecords> eMOrdinaryLevelScienceRecords =  fileRetrieve.listEmOrdinaryLevelScience("OrdinaryLevelScienceEnglishMedium");
        model.addAttribute("eMOrdinaryLevelScienceRecords", eMOrdinaryLevelScienceRecords);

        List<UploadRecords> eMOrdinaryLevelITRecords =  fileRetrieve.listEmOrdinaryLevelIT("OrdinaryLevelITEnglishMedium");
        model.addAttribute("eMOrdinaryLevelITRecords", eMOrdinaryLevelITRecords);

        List<UploadRecords> eMOrdinaryLevelBusinessSRecords =  fileRetrieve.listEmOrdinaryLevelBusinessS("OrdinaryLevelBusinessSEnglishMedium");
        model.addAttribute("eMOrdinaryLevelBusinessSRecords", eMOrdinaryLevelBusinessSRecords);

        List<UploadRecords> eMOrdinaryLevelHistoryRecords =  fileRetrieve.listEmOrdinaryLevelHistory("OrdinaryLevelHistoryEnglishMedium");
        model.addAttribute("eMOrdinaryLevelHistoryRecords", eMOrdinaryLevelHistoryRecords);



        return  "ordinaryLevelEnglishMedium.html";
    }

    @RequestMapping("/ordinaryLevelTamilMedium.html")
    public String OLevelTamilSubjects(Model model) {

        List<UploadRecords> tMOrdinaryLevelMathematicsRecords =  fileRetrieve.listTmOrdinaryLevelMaths("OrdinaryLevelMathematicsTamilMediumTamilMedium");
        model.addAttribute("tMOrdinaryLevelMathematicsRecords", tMOrdinaryLevelMathematicsRecords);

        List<UploadRecords> tMOrdinaryLevelScienceRecords =  fileRetrieve.listTmOrdinaryLevelScience("OrdinaryLevelScienceTamilMedium");
        model.addAttribute("tMOrdinaryLevelScienceRecords", tMOrdinaryLevelScienceRecords);

        return  "ordinaryLevelTamilMedium.html";
    }



    @RequestMapping("/otherGovernmentExamsEnglishMedium.html")
    public String loadEmOtherGovernmentExams(Model model) {




        return "otherGovernmentExamsEnglishMedium.html";

    }
    @RequestMapping("/otherGovernmentExamsSinhalaMedium.html")
    public String loadSmOtherGovernmentExams(Model model) {

        List<UploadRecords> sMGramaniladariRecords =  fileRetrieve.listTmOrdinaryLevelScience("OrdinaryLevelScienceTamilMedium");
        model.addAttribute("sMGramaniladariRecords", sMGramaniladariRecords);

        return "otherGovernmentExamsSinhalaMedium.html";

    }
    @RequestMapping("/otherGovernmentExamsTamilMedium.html")
    public String loadTmOtherGovernmentExams(Model model) {

        return "otherGovernmentExamsTamilMedium.html";

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

    @GetMapping("/profile.html")
    public String profile() {
        return "profile.html";
    }

    @GetMapping("/editDirect")
    public String editDirect(){

       return "editRedirect.html";
    }






}

