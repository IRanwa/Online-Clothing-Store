package com.example.imeshranawaka.styleomega.Models;

import com.orm.SugarRecord;

import java.text.DateFormat;
import java.util.Date;

public class Orders extends SugarRecord<Orders> {
    private String orderStatus;
    private String userEmail;
    private long userAddress;
    private Date purchasedDate;

    public Orders(){

    }

    public Orders(String orderStatus, String userEmail, long userAddress) {
        this.orderStatus = orderStatus;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.purchasedDate = null;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserAddress(long userAddress) {
        this.userAddress = userAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public long getUserAddress() {
        return userAddress;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }
}
