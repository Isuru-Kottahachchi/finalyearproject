package com.smartedulanka.finalyearproject.controller;

import com.smartedulanka.finalyearproject.datalayer.entity.UploadRecord;
import com.smartedulanka.finalyearproject.repository.UploadRepository;
import com.smartedulanka.finalyearproject.service.AmazonClient;
import com.smartedulanka.finalyearproject.service.FileUploadRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
/*@RestController*/
/*@RequestMapping("/storage")*/


public class UploadController {
    private AmazonClient amazonClient;

    @Autowired
    UploadController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }
    @Autowired
   public FileUploadRecord fileuploadrecord;

/* Getting hidden inputs using hidden input fields(requestparam)*/
    @PostMapping("/upload")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file,@RequestParam(value = "userfileupload",required = true)String subjectAreaSelect) {

        try{
            fileuploadrecord.saveUploadRecord(amazonClient.uploadFile(file),subjectAreaSelect,file.getOriginalFilename());


            return "uploadStatus";

        }catch(Exception e){

            return "403";

        }


        //return this.amazonClient.uploadFile(file);

    }

   /* @PostMapping ("/test")
        public  String testMethod(@RequestPart(value = "text") String text){
            return text;

        }*/


    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }







/*Question review delete record*/
    @Autowired
    private UploadRepository uploadRepo;

    @GetMapping("/updateFileRecord")
    public String updateFileRecord(@RequestParam Long recordId) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        UploadRecord uploadRecord = uploadRepo.getById(recordId);
        uploadRecord.setReviewStatus("ACCEPTED");
        uploadRecord.setFileReviewedTime(dtf.format(now));
        uploadRepo.save(uploadRecord);
        return "recordDeleteConfirmation.html";
    }

    @GetMapping("/deleteFileRecord")
    public String deleteFileRecord(@RequestParam Long recordId) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        UploadRecord uploadRecord = uploadRepo.getById(recordId);
        uploadRecord.setReviewStatus("REJECTED");
        uploadRecord.setFileReviewedTime(dtf.format(now));

        uploadRepo.save(uploadRecord);
        return "recordDeleteConfirmation.html";
    }













}
