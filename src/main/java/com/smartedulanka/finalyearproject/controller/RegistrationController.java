package com.smartedulanka.finalyearproject.controller;


import com.smartedulanka.finalyearproject.datalayer.entity.User;
import com.smartedulanka.finalyearproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class RegistrationController {

                @Autowired
                private UserRepository userRepo;

               @RequestMapping("/signUpForm.html")
               public String loadRegistration(Model model) {

                   model.addAttribute("user",new User());

                   return "signUpForm";
               }

                @PostMapping("/register_process")
                public String processRegister(User user) {

                   //EncodePassword
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

                    String encodedPassword = passwordEncoder.encode(user.getPassword());
                    user.setPassword(encodedPassword);


                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();

                    //Giving users role as USER
                    user.setRole("USER");
                    user.setRegisteredTime(dtf.format(now));
                    user.setFullName(user.getFirstName() + " " + user.getLastName());

                    userRepo.save(user);

                    return "register_success";
                }


                @GetMapping("/users.html")
                public String listUsers(Model model) {

                    List<User> usersList = userRepo.findAll();
                    model.addAttribute("usersList", usersList);

                    return "users";
                }




                @GetMapping("/removeUser")
                public String removeUser(@RequestParam Long userId) {

                   User user = userRepo.getById(userId);
                   userRepo.delete(user);

                    return "redirect:/users.html";
                }
                @GetMapping("/changeRole")
                public String changeRole(@RequestParam Long userId){

                    User user = userRepo.getById(userId);


                    if(user.getRole().equals("USER")){

                        user.setRole("ADMIN");

                        userRepo.save(user);

                    }else if(user.getRole().equals("ADMIN")){


                        user.setRole("USER");

                        userRepo.save(user);


                    }



                    return "redirect:/users.html";
                }


    }
