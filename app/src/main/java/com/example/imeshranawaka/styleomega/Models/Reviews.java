package com.example.imeshranawaka.styleomega.Models;

import com.orm.SugarRecord;

public class Reviews extends SugarRecord<Reviews> {
    private long orderProdId;
    private long prodId;
    private String userEmail;
    private String revDesc;
    private float rating;

    public Reviews(){

    }

    public Reviews(long orderProdId, long prodId, String userEmail, String revDesc, float rating) {
        this.orderProdId = orderProdId;
        this.prodId = prodId;
        this.userEmail = userEmail;
        this.revDesc = revDesc;
        this.rating = rating;
    }

    public void setOrderProdId(long orderProdId) {
        this.orderProdId = orderProdId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setRevDesc(String revDesc) {
        this.revDesc = revDesc;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getOrderProdId() {
        return orderProdId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getRevDesc() {
        return revDesc;
    }

    public float getRating() {
        return rating;
    }

    public long getProdId() {
        return prodId;
    }

    public void setProdId(long prodId) {
        this.prodId = prodId;
    }
}
