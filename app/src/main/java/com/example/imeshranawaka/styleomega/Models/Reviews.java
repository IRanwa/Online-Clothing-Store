package com.example.imeshranawaka.styleomega.Models;

import com.orm.SugarRecord;

public class Reviews extends SugarRecord<Reviews> {
    private int prodId;
    private String userEmail;
    private String revDesc;
    private double rating;

    public Reviews(){

    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setRevDesc(String revDesc) {
        this.revDesc = revDesc;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getProdId() {
        return prodId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getRevDesc() {
        return revDesc;
    }

    public double getRating() {
        return rating;
    }
}
