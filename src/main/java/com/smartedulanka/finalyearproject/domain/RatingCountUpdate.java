package com.smartedulanka.finalyearproject.domain;

import com.smartedulanka.finalyearproject.datalayer.entity.Ratings;

import java.util.HashMap;

public class RatingCountUpdate {

    HashMap<Long, Long> rateUpdatedValue ;
    HashMap<Long, Long> rateUpdatedPositiveValue;
    HashMap<Long, Long> rateUpdatedNegativeValue;

    public HashMap<Long, Long> getRateUpdatedValue() {
        return rateUpdatedValue;
    }

    public void setRateUpdatedValue(HashMap<Long, Long> rateUpdatedValue) {
        this.rateUpdatedValue = rateUpdatedValue;
    }

    public HashMap<Long, Long> getRateUpdatedPositiveValue() {
        return rateUpdatedPositiveValue;
    }

    public void setRateUpdatedPositiveValue(HashMap<Long, Long> rateUpdatedPositiveValue) {
        this.rateUpdatedPositiveValue = rateUpdatedPositiveValue;
    }

    public HashMap<Long, Long> getRateUpdatedNegativeValue() {
        return rateUpdatedNegativeValue;
    }

    public void setRateUpdatedNegativeValue(HashMap<Long, Long> rateUpdatedNegativeValue) {
        this.rateUpdatedNegativeValue = rateUpdatedNegativeValue;
    }
}
