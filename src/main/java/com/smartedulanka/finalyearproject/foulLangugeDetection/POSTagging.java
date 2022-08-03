package com.smartedulanka.finalyearproject.foulLangugeDetection;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class POSTagging {

    POSTaggerME tagger = null;
    POSModel model = null;

    public void initialize(String lexiconFileName) {
        try {
            InputStream modelStream =  getClass().getResourceAsStream(lexiconFileName);
            model = new POSModel(modelStream);
            tagger = new POSTaggerME(model);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String tag (String text){
        initialize("/en-pos-maxent.bin");
        try {

            ArrayList<String> nounsList = new ArrayList<>();
            ArrayList<String> adjectiveList = new ArrayList<>();
            ArrayList<String> verbBaseList = new ArrayList<>();
            ArrayList<String> fullTextList = new ArrayList<>();


            Resource resource = new ClassPathResource("nouns.txt");
            File nounFile = resource.getFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(nounFile));

            String[] offensiveNounsArray = bufferedReader.lines().collect(Collectors.joining()).split(",");


            Resource adjectivesResource = new ClassPathResource("adjectives.txt");
            File adjectivesFile = adjectivesResource.getFile();
            BufferedReader adjectivesFileBufferedReader = new BufferedReader(new FileReader(adjectivesFile));

            String[] offensiveAdjectiveArray = adjectivesFileBufferedReader.lines().collect(Collectors.joining()).split(",");


            Resource vbgResource = new ClassPathResource("verbBase.txt");
            File vbgFile = vbgResource.getFile();
            BufferedReader vbgFileBufferedReader = new BufferedReader(new FileReader(vbgFile));

            String[] vbgArray = vbgFileBufferedReader.lines().collect(Collectors.joining()).split(",");



            Resource offensiveLangResource = new ClassPathResource("offensivelang.txt");
            File offensiveLangResourceFile = offensiveLangResource.getFile();
            BufferedReader offensiveLangBufferedReader = new BufferedReader(new FileReader(offensiveLangResourceFile));

            String[] offensiveLangArray = offensiveLangBufferedReader.lines().collect(Collectors.joining()).split(",");





            if (model != null) {
                POSTaggerME tagger = new POSTaggerME(model);
                if (tagger != null) {

                    //Detect sentences using en-sent OpenNLP model
                    String[] sentences = detectSentences(text);
                    for (String sentence : sentences) {
                        String tokenizedText[] = WhitespaceTokenizer.INSTANCE
                                .tokenize(sentence);

                        //Getting tags of words
                        String[] tagsArray = tagger.tag(tokenizedText);

                        for (int i = 0; i < tokenizedText.length; i++) {

                            String word = tokenizedText[i].trim();

                            word = word.replace(".", "");
                            word = word.replace(",", "");
                            word = word.replace("?", "");

                            String tag = tagsArray[i].trim();

                            fullTextList.add(word);

                            if(tag.equals("NN")||(tag.equals("NNS"))){

                                nounsList.add(word);

                            }else if(tag.equals("JJ")){

                                adjectiveList.add(word);

                            }else if(tag.equals("VB") ||tag.equals("VBZ") ||(tag.equals("VBG"))||(tag.equals("VBN"))||(tag.equals("VBD"))){

                                verbBaseList.add(word);
                            }

                            System.out.print(tag + ":" + word + "  ");
                        }

                    }

                    boolean nounFlag = false;
                    for(String noun : nounsList){
                         for(String offensiveNoun : offensiveNounsArray){
                             if(offensiveNoun.equalsIgnoreCase(noun)){
                                 nounFlag = true;
                                 System.out.print("User have used :- " + offensiveNoun);
                             }
                         }
                    }

                    boolean adjFlag = false;
                    for(String adjective : adjectiveList){
                        for(String offensiveAdjective : offensiveAdjectiveArray){
                            if(offensiveAdjective.equalsIgnoreCase(adjective)){
                                adjFlag = true;
                                System.out.print(" User have used adjective:- " + offensiveAdjective);
                            }
                        }
                    }

                    boolean vbBaseFlag = false;
                    for(String verbBase : verbBaseList ){
                        for(String vbg : vbgArray){
                            if(vbg.equalsIgnoreCase(verbBase)){
                                vbBaseFlag = true;
                                System.out.print(" User have used vbg :- " + vbg);
                            }
                        }
                    }

                    if(nounFlag || adjFlag || vbBaseFlag){

                        return "Detected";

                    }else{

                        boolean flagAnything = false;
                        for(String fullText : fullTextList){
                            for(String offensiveWord : offensiveLangArray){
                                if(offensiveWord .equalsIgnoreCase(fullText)){
                                    flagAnything = true;
                                    System.out.println(" User have used offensiveWord :-  " + offensiveWord );
                                }
                            }
                        }

                        if(flagAnything) {

                            return "Detected";
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Not Detected";

    }



    public String[] detectSentences(String paragraph) throws IOException {

        InputStream modelIn = getClass().getResourceAsStream("/en-sent.bin");
        final SentenceModel sentenceModel = new SentenceModel(modelIn);
        modelIn.close();

        SentenceDetector sentenceDetector = new SentenceDetectorME(sentenceModel);
        String sentences[] = sentenceDetector.sentDetect(paragraph);

        /*for (String sent : sentences) {
            System.out.println(sent);
        }*/
        return sentences;
    }
}
