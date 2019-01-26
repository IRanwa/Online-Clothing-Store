package com.example.imeshranawaka.styleomega.Models;

import com.orm.SugarRecord;

public class Questions extends SugarRecord<Questions> {
    private long prodId;
    private String userEmail;
    private String question;
    private String answer;

    public Questions(){

    }

    public Questions(long prodId, String userEmail, String question) {
        this.prodId = prodId;
        this.userEmail = userEmail;
        this.question = question;
    }

    public void setProdId(long prodId) {
        this.prodId = prodId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public long getProdId() {
        return prodId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
