package com.example.imeshranawaka.styleomega.Models;

import com.orm.SugarRecord;

public class Address extends SugarRecord<Address> {
    private String userEmail;
    private String fName;
    private String lName;
    private String address;
    private String city;
    private int contact;
    private String province;
    private boolean def;

    public Address(){

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

    public void setDef(boolean def) {
        this.def = def;
    }
}
