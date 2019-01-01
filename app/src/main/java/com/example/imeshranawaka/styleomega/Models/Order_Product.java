package com.example.imeshranawaka.styleomega.Models;

import com.orm.SugarRecord;

import java.util.Date;

public class Order_Product extends SugarRecord<Order_Product> {
    private int orderNo;
    private int prodId;
    private Date purchasedDate;
    private int quantity;

    public Order_Product(){

    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public int getProdId() {
        return prodId;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public int getQuantity() {
        return quantity;
    }
}
