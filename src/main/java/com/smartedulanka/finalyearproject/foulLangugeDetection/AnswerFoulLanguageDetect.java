package com.smartedulanka.finalyearproject.foulLangugeDetection;


import com.smartedulanka.finalyearproject.datalayer.entity.Answer;
import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import org.springframework.stereotype.Service;

@Service
public class AnswerFoulLanguageDetect {

    public String detectFoulLanguageInAnswer(Answer answer){

        String fullAnswerWithoutTags = answer.getFullAnswer().replaceAll("<[^>]*>", "");


        POSTagging tagging = new POSTagging();

        String status = tagging.tag(fullAnswerWithoutTags);

        return status;
    }
}
