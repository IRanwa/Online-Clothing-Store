package com.example.imeshranawaka.styleomega.Models;

import com.orm.SugarRecord;

import java.util.Date;

public class User extends SugarRecord<User> {
    private String email;
    private String fName;
    private String lName;
    private String dob;
    private String userGender;
    private int contactNo;

    public User(){

    }

    public User(String email, String fName, String lName) {
        this.email = email;
        this.fName = fName;
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getDob() {
        return dob;
    }

    public String getUserGender() {
        return userGender;
    }

    public int getContactNo() {
        return contactNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }
}
