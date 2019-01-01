package com.example.imeshranawaka.styleomega.Models;

import com.orm.SugarRecord;

public class Order extends SugarRecord<Order> {
    public String status;
    public String useEmail;
    public String userAddress;

    public Order(){

    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUseEmail(String useEmail) {
        this.useEmail = useEmail;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getStatus() {
        return status;
    }

    public String getUseEmail() {
        return useEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }
}
