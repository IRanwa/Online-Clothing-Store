package com.example.imeshranawaka.styleomega.Models;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Address extends SugarRecord<Address> implements Serializable {
    private String userEmail;
    private String fName;
    private String lName;
    private String address;
    private String city;
    private int contact;
    private String province;
    private int zipCode;
    private boolean def;

    public Address(){

    }

    public Address(String userEmail, String fName, String lName, String address, String city, int contact, String province, int zipCode, boolean def) {
        this.userEmail = userEmail;
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.city = city;
        this.contact = contact;
        this.province = province;
        this.zipCode = zipCode;
        this.def = def;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public int getContact() {
        return contact;
    }

    public String getProvince() {
        return province;
    }

    public int getZipCode() {
        return zipCode;
    }

    public boolean isDef() {
        return def;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public void setDef(boolean def) {
        this.def = def;
    }
}
