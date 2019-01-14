package com.example.imeshranawaka.styleomega.Models;

import com.orm.SugarRecord;

public class Questions extends SugarRecord<Questions> {
    private int prodId;
    private String userEmail;
    private String question;
    private String answer;

    public Questions(){

    }

    public void setProdId(int prodId) {
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

    public int getProdId() {
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
