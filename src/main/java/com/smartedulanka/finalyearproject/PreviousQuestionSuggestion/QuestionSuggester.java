package com.smartedulanka.finalyearproject.PreviousQuestionSuggestion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionSuggester {

    @Autowired
    private QuestionAnalyse questionAnalyse;

    public  String analyseQuestion(String questionText){

        String processedText = questionAnalyse.tag(questionText);

        //System.out.println("Question suggester " + questionText);

       return processedText;
    }
}
