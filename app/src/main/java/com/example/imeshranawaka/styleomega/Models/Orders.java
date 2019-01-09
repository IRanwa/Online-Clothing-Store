package com.example.imeshranawaka.styleomega.Models;

import com.orm.SugarRecord;

public class Orders extends SugarRecord<Orders> {
    public String orderStatus;
    public String userEmail;
    public String userAddress;

    public Orders(){

    }

    public Orders(String orderStatus, String userEmail, String userAddress) {
        this.orderStatus = orderStatus;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }
}
