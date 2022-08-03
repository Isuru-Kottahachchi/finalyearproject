package com.smartedulanka.finalyearproject.foulLangugeDetection;

import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import org.springframework.stereotype.Service;

@Service
public class QuestionFoulLanguageDetect {

    public String detectFoulLanguage(Question question){

        String fullQuestionWithoutTags = question.getFullQuestion().replaceAll("<[^>]*>", "");


        POSTagging tagging = new POSTagging();

        String status = tagging.tag(fullQuestionWithoutTags);

        return status;
    }
}
