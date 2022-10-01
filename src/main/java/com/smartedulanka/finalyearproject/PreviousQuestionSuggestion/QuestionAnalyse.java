package com.smartedulanka.finalyearproject.PreviousQuestionSuggestion;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionAnalyse {

    POSTaggerME tagger = null;
    POSModel model = null;

    //checking NLP model is available or not
    public void initialize(String lexiconFileName) {
        try {

            InputStream modelStream =  getClass().getResourceAsStream(lexiconFileName);
            model = new POSModel(modelStream);
            tagger = new POSTaggerME(model);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String tag (String searchBarInput){

        initialize("/en-pos-maxent.bin");

        try {

            ArrayList<String> unwantedWordsList = new ArrayList<>();

            if (model != null) {

                POSTaggerME tagger = new POSTaggerME(model);

                if (tagger != null) {

                    //Calling detect sentence method
                    String[] sentencesArray = detectSentences(searchBarInput);

                    for (String sentence : sentencesArray) {

                        //Tokenizing sentences
                        String tokenizedTextArray[] = WhitespaceTokenizer.INSTANCE
                                .tokenize(sentence);

                        //Converting tokenized words arrays to a ArrayList
                        ArrayList<String> wordsList = new ArrayList<String>(Arrays.asList(tokenizedTextArray));

                        //Get POS tags in tokenizedText array
                        String[] tagsArray = tagger.tag(tokenizedTextArray);

                        //Looping tokenized text array
                        for (int i = 0; i < tokenizedTextArray.length; i++) {

                            //Get words according to the tokenizedText arrayIndex
                            String word = tokenizedTextArray[i].trim();

                            word = word.replace(".", "").replace("?", "");


                            //Get tags according to the tag arrayIndex
                            String tag = tagsArray[i].trim();

                           //Remove adverbs wh adverbs when, where,why
                            if(tag.equals("WRB")){

                                unwantedWordsList.add(word);

                            }
                            //Remove to
                            else if(tag.equals("TO")){

                                unwantedWordsList.add(word);

                            }
                            /*(Determiners :- a,an,the,these,those)*/
                            else if(tag.equals("DT")){

                                unwantedWordsList.add(word);

                            }
                            //Remove interrogative pronouns who, whom, whose, what, which
                            else if(tag.equals("WP")){

                                unwantedWordsList.add(word);

                            }
                            //Remove prepositions ( for,but,since,in,pn,under)
                            else if(tag.equals("IN")){

                                unwantedWordsList.add(word);

                            }
                            //Remove interjection Ex- Hurray,Wow,Alas
                            else if(tag.equals("UH")){

                                unwantedWordsList.add(word);

                            }
                            //Remove coordinating conjunction and,nor,but,or,yet,so
                            else if(tag.equals("CC")){

                                unwantedWordsList.add(word);
                            }
                            //Remove pre-determiners all,both
                            else if(tag.equals("PDT")){

                                unwantedWordsList.add(word);
                            }
                            /* Personal pronouns I,He,She,Me,you,they,yourself,herself,ours (hers,yours can be catogorized as adjectives*/
                            else if(tag.equals("PRP")){

                                unwantedWordsList.add(word);
                            }
                            /*Verbs*/
                            else if(tag.equals("VB")||tag.equals("VBG")||tag.equals("VBD")||tag.equals("VBN")||tag.equals("VBP")||tag.equals("VBZ")){

                                unwantedWordsList.add(word);
                            }
                            //Adjectives yours hers
                            /*else if(tag.equals("JJ")){

                                unwantedWordsList.add(word);

                            }*/

                            System.out.print(tag + ":" + word + "  ");

                        }

                        wordsList.removeAll(unwantedWordsList);


                        StringBuffer sb = new StringBuffer();

                        //save arrayList's string to string
                        for (String word : wordsList) {
                            sb.append(word);
                            sb.append(" ");
                        }

                        //Converting StringBuffer sb object to String using toString method
                        String processedText = sb.toString();

                        System.out.println(processedText);

                        return processedText;

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Not Found";


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
