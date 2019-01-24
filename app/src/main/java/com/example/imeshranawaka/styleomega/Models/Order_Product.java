package com.example.imeshranawaka.styleomega.Models;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

public class Order_Product extends SugarRecord<Order_Product> implements Serializable {
    private long orderNo;
    private long prodId;
    private String purchasedDate;
    private int quantity;

    public Order_Product(){

    }

    public Order_Product(long orderNo, long prodId, int quantity) {
        this.orderNo = orderNo;
        this.prodId = prodId;
        this.quantity = quantity;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public void setPurchasedDate(String purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getOrderNo() {
        return orderNo;
    }

    public long getProdId() {
        return prodId;
    }

    public String getPurchasedDate() {
        return purchasedDate;
    }

    public int getQuantity() {
        return quantity;
    }
}
