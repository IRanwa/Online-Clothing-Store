package com.example.imeshranawaka.styleomega.Models;

import com.orm.SugarRecord;

public class Login extends SugarRecord<Login> {
    private String email;
    private String pass;

    public Login(){

    }

    public Login(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
