package com.example.imeshranawaka.styleomega.Models;

import com.orm.SugarRecord;

public class Product extends SugarRecord<Product> {
    private int catId;
    private String title;
    private String desc;
    private double price;
    private int quantity;

    public Product(){

    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCatId() {
        return catId;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
