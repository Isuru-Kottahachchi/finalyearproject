package com.smartedulanka.finalyearproject.controller;

import com.smartedulanka.finalyearproject.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NavigationBarButtonsRestController {

    /*Index(Home) page Onload Function*/
    @PostMapping("/hideSignUpButtonFromRegisteredUsers")
    public Long hideSignUpButtonFromRegisteredUsers(){

        try{
            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = customUserDetails.getUserId();

            if(userId == null){

                return 0L;

            }else{
                return userId;

            }

        }catch (Exception e){

            //System.out.println(e);
        }

        return 0L;

    }




    @PostMapping("/loadSinhalaMedium")
    public Long loadSinhalaMedium(){

        try{
            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = customUserDetails.getUserId();

            if(userId == null){

                return 0L;

            }else{
                return userId;

            }

        }catch (Exception e){

            //System.out.println(e);
        }

        return 0L;

    }


    @PostMapping("/loadTamilMedium")
    public Long loadTamilMedium(){

        try{
            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = customUserDetails.getUserId();

            if(userId == null){

                return 0L;

            }else{
                return userId;

            }

        }catch (Exception e){

            //System.out.println(e);
        }

        return 0L;

    }


    @PostMapping("/loadEnglishMedium")
    public Long loadEnglishMedium(){

        try{
            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = customUserDetails.getUserId();

            if(userId == null){

                return 0L;

            }else{
                return userId;

            }

        }catch (Exception e){

            //System.out.println(e);
        }

        return 0L;

    }
}
