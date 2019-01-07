package com.example.imeshranawaka.styleomega.Models;

import com.orm.SugarRecord;

public class Orders extends SugarRecord<Orders> {
    public String orderStatus;
    public String useEmail;
    public String userAddress;

    public Orders(){

    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setUseEmail(String useEmail) {
        this.useEmail = useEmail;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getUseEmail() {
        return useEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }
}
