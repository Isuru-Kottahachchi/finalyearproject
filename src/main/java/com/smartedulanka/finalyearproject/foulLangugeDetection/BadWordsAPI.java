package com.smartedulanka.finalyearproject.foulLangugeDetection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartedulanka.finalyearproject.datalayer.entity.Answer;
import com.smartedulanka.finalyearproject.datalayer.entity.Question;

//import com.sun.mail.iap.Response;
//import org.springframework.http.MediaType;

import com.smartedulanka.finalyearproject.model.Root;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.concurrent.TimeUnit;

import okhttp3.*;
/*import org.springframework.web.bind.annotation.RequestBody;*/


@Service
public class BadWordsAPI {

    public String detectFoulLanguageWords(Question question) throws IOException {


        String fullQuestionWithoutTags = question.getFullQuestion().replaceAll("<[^>]*>", "");

        String fullQuestion = fullQuestionWithoutTags.trim().replaceAll("&nbsp;", " ").replaceAll("&minus;", "").replaceAll("[-+=^]*", "").replaceAll("[(){}]","").replaceAll("\r\n", "").replaceAll("&Sigma;","").replaceAll("\\s+", " ").replaceAll(";","").replaceAll("&","").replaceAll(",","").replaceAll("\"","").replaceAll("pi"," ");



        OkHttpClient client = new OkHttpClient().newBuilder().build();

    /*    OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(30, TimeUnit.MINUTES) // write timeout
                .readTimeout(30, TimeUnit.MINUTES);*/

        OkHttpClient client1 = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.MINUTES)
                .writeTimeout(30, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.MINUTES)
                .build();

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, fullQuestion);

        Request request = new Request.Builder()
                .url("https://api.apilayer.com/bad_words?censor_character=censor_character")
                .addHeader("apikey", "drKcN9wqrj86qNonWlOHtdayG5m2Bk44")
                .method("POST", body)
            .build();
        Response response = client.newCall(request).execute();
        //System.out.println(response.body().string());


        ObjectMapper objectMapper = new ObjectMapper();

        String json = response.body().string();
        Root root = objectMapper.readValue(json, Root.class);

        Integer badWordsCount = root.getBad_words_total();

        if(badWordsCount == 0){

            return "NotDetected";
        }
        else if(badWordsCount > 0){

            return "Detected";
        }

         return "Badwords";
    }



    public String detectFoulLanguageWordsInAnswer(Answer answer) throws IOException {


        String fullAnswerWithoutTags = answer.getFullAnswer().replaceAll("<[^>]*>", "");

        String fullAnswer = fullAnswerWithoutTags.trim().replaceAll("&nbsp;", "").replaceAll("&minus;", "").replaceAll("[-+=^]*", "").replaceAll("[(){}]","").replaceAll("\r\n", "").replaceAll("&Sigma;","").replaceAll("\\s+", " ").replaceAll(";","").replaceAll("&","").replaceAll(",","").replaceAll("\"","").replaceAll("pi"," ");

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, fullAnswer);

        Request request = new Request.Builder()
                .url("https://api.apilayer.com/bad_words?censor_character=censor_character")
                .addHeader("apikey", "drKcN9wqrj86qNonWlOHtdayG5m2Bk44")
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        //System.out.println(response.body().string());


        ObjectMapper objectMapper = new ObjectMapper();

        String json = response.body().string();
        Root root = objectMapper.readValue(json, Root.class);

        Integer badWordsCount = root.getBad_words_total();

        if(badWordsCount == 0){

            return "NotDetected";
        }
        else if(badWordsCount > 0){

            return "Detected";
        }

        return "Badwords";
    }


}
