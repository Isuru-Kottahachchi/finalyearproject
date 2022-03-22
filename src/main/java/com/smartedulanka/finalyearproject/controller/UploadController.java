package com.smartedulanka.finalyearproject.controller;

import com.amazonaws.HttpMethod;
import com.smartedulanka.finalyearproject.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



/*@Controller*/
@RestController
/*@RequestMapping("/storage")*/


public class UploadController {
    private AmazonClient amazonClient;

    @Autowired
    UploadController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }



    @PostMapping("/upload")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
     /*   return "uploadStatus";*/
    }

   /* @PostMapping ("/test")
        public  String testMethod(@RequestPart(value = "text") String text){
            return text;

        }*/


    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}
