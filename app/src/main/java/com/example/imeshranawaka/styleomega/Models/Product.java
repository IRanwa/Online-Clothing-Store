package com.example.imeshranawaka.styleomega.Models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orm.SugarRecord;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product extends SugarRecord<Product> implements Serializable {
    private int catId;
    private String title;
    private String desc;
    private double price;
    private int quantity;
    private String images;//This is json object

    public Product(){

    }

    public Product(int catId, String title, String desc, double price, int quantity, String images) {
        this.catId = catId;
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
        this.images = images;
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

    public void setImages(String images) {
        this.images = images;
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

    public List<String> getImages() {
        return new Gson().fromJson(this.images, new TypeToken<List<String>>(){}.getType());
    }
}
