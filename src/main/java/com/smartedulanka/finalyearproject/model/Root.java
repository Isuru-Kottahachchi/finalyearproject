package com.smartedulanka.finalyearproject.model;

import java.util.ArrayList;

public class Root {

    public ArrayList<BadWordsList> bad_words_list;
    public int bad_words_total;
    public String censored_content;
    public String content;

    public ArrayList<BadWordsList> getBad_words_list() {
        return bad_words_list;
    }

    public void setBad_words_list(ArrayList<BadWordsList> bad_words_list) {
        this.bad_words_list = bad_words_list;
    }

    public int getBad_words_total() {
        return bad_words_total;
    }

    public void setBad_words_total(int bad_words_total) {
        this.bad_words_total = bad_words_total;
    }

    public String getCensored_content() {
        return censored_content;
    }

    public void setCensored_content(String censored_content) {
        this.censored_content = censored_content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
