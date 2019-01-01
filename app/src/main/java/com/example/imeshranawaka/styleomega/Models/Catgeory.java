package com.example.imeshranawaka.styleomega.Models;

import android.icu.util.ULocale;

import com.orm.SugarRecord;

public class Catgeory extends SugarRecord<ULocale.Category> {
    private String title;
    private String desc;
    private String image;

    public Catgeory(){

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
