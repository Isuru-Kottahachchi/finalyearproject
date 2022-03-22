package com.smartedulanka.finalyearproject.controller;

import com.smartedulanka.finalyearproject.datalayer.entity.User;
import com.smartedulanka.finalyearproject.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping("/forum.html")
    public String getForum() {
        return "forum";
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

    @RequestMapping("/advancedlevelenglish.html")
    public String advancedLevelEnglish() {
        return  "advancedlevelenglish";
    }



    @Autowired
    private UserRepository userRepo;

    @GetMapping("/indexpage")
    public String viewHomePage() {
        return "indexpage";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        //Encodepassword
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        //Giving users role as USER
        user.setRole("USER");

        userRepo.save(user);

        return "register_success";
    }
    @GetMapping("/users.html")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }





  /*login page customization  */
  @GetMapping("/403")
    public String error403() {
        return "403";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    /*s3bucket file uploading */















}

