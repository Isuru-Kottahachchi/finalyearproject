package com.smartedulanka.finalyearproject.controller;


import com.smartedulanka.finalyearproject.datalayer.entity.*;

import com.smartedulanka.finalyearproject.domain.*;
import com.smartedulanka.finalyearproject.repository.AnswerRepository;
import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import com.smartedulanka.finalyearproject.repository.RatingRepository;
import com.smartedulanka.finalyearproject.repository.UserRepository;
import com.smartedulanka.finalyearproject.security.CustomUserDetails;
import com.smartedulanka.finalyearproject.service.DeleteAnswersHideButtonsService;
import com.smartedulanka.finalyearproject.service.PreviouslySavedObjectService;
import com.smartedulanka.finalyearproject.service.PreviousContributionService;
import com.smartedulanka.finalyearproject.service.RatingCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class StarRatingRestController {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private PreviouslySavedObjectService previouslySavedObjectService;

    @Autowired
    private PreviousContributionService previousContribution;

    @Autowired
    private RatingCountService ratingCountService;

    @Autowired
    private DeleteAnswersHideButtonsService deleteAnswersHideButtonsService;

 /* @PostMapping("/addRating")
    public String addRating(Ratings ratings,@RequestParam Long questionId,@RequestParam Long answerId){
        User user;
        try{

            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = customUserDetails.getUserId();
            String userEmail = customUserDetails.getUsername();
            String userName = customUserDetails.getFullName();

            user =userRepository.getById(userId);
            ratings.setQuestion_Author_name(userName);
            ratings.setUser(user);
            *//*ratings.setRatingValue(rateValue);*//*

            Question question = questionRepository.getById(questionId);
            ratings.setQuestion(question);

            Answer answer = answerRepository.getById(answerId);
            ratings.setAnswer(answer);
        }catch (Exception e){

            System.out.println(e);
        }
      ratingRepository.save(ratings);
        return "redirect:/forum.html";
  }
*/



    @PostMapping("/saveAndDisplayRatings")
    public RatingCountUpdate saveRating(@RequestParam Long answerId,@RequestParam Short rateValue){

        User user;
        try{

            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = customUserDetails.getUserId();

            String userName = customUserDetails.getFullName();

            user = userRepository.getById(userId);

            /*Checking for previously saved object (check whether logged user already rated for particular answer)*/
            Ratings rating =  previouslySavedObjectService.retrievePreviouslySavedRating(userId,answerId);

            /*If user already rated for this answer*/
            if(rating!= null){

                    /*Remove rating if rating value is +1 and user clicks on the green star button again Or remove rating if rating value is -1 and user clicks on red star button again*/
                            if(rateValue.equals(rating.getRatingValue())){

                                ratingRepository.deleteById(rating.getRating_id());

                            }else if(rateValue == 1){

                                rating.setRatingValue((short) 1);
                                ratingRepository.save(rating);

                            }else{

                                rating.setRatingValue((short) -1);
                                ratingRepository.save(rating);
                            }

                /*If user not rated for this answer*/
            }else{

                            Ratings ratings = new Ratings();

                            ratings.setRater_name(userName);
                            ratings.setUser(user);

                            ratings.setRatingValue(rateValue);


                            Answer answer = answerRepository.getById(answerId);

                            Question question = questionRepository.getQuestionById(answer.getQuestion().getQuestion_id());

                            ratings.setAnswer(answer);

                            ratings.setQuestion(question);

                            ratings.setAnswer_Author_id(answer.getAnswerAuthorId());
                            ratingRepository.save(ratings);


            }

        }catch (Exception e){

            System.out.println(e);
        }

        Long rateUpdatedValue = ratingCountService.retrieveUpdatedRatingValue(answerId);
        Long rateUpdatedPositiveValue = ratingCountService.retrieveUpdatedPositiveRatingValue(answerId);
        Long rateUpdatedNegativeValue = ratingCountService.retrieveUpdatedNegativeRatingValue(answerId);

        HashMap<Long, Long> rateUpdatedValueMap = new HashMap<Long,Long>();
        HashMap<Long, Long> rateUpdatedPositiveValueMap = new HashMap<Long,Long>();
        HashMap<Long, Long> rateUpdatedNegativeValueMap = new HashMap<Long,Long>();

        rateUpdatedValueMap.put(answerId,rateUpdatedValue);
        rateUpdatedPositiveValueMap.put(answerId,rateUpdatedPositiveValue);
        rateUpdatedNegativeValueMap.put(answerId,rateUpdatedNegativeValue);

        RatingCountUpdate ratingCountUpdate = new RatingCountUpdate();

        ratingCountUpdate.setRateUpdatedValue(rateUpdatedValueMap);
        ratingCountUpdate.setRateUpdatedPositiveValue(rateUpdatedPositiveValueMap);
        ratingCountUpdate.setRateUpdatedNegativeValue(rateUpdatedNegativeValueMap);


      return ratingCountUpdate;
    }


    @PostMapping("/changeRatingButtonsAccordingToClick")
    public Long changeRatingButtons(@RequestParam Long answerId){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getUserId();

        Long currentRatingValue = ratingCountService.retrieveCurrentRatingValue(userId,answerId);

        return currentRatingValue;
    }


}
