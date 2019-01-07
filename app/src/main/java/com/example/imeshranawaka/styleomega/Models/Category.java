package com.example.imeshranawaka.styleomega.Models;

import android.icu.util.ULocale;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Category extends SugarRecord<Category> implements Serializable {
    private int catId;
    private String title;
    private String desc;
    private String image;

    public Category(){

    }

    public Category(int catId, String title, String desc, String image) {
        this.catId = catId;
        this.title = title;
        this.desc = desc;
        this.image = image;
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

    public void setImage(String image) {
        this.image = image;
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

    public String getImage() {
        return image;
    }
}
