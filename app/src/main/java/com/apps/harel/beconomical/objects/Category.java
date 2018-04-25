package com.apps.harel.beconomical.objects;

import android.graphics.drawable.Drawable;


public class Category {

    String title;
    Drawable image;

    public Category(int String, int drawable) {

    }

    public Category(String title, Drawable image) {
        this.title = title;
        this.image = image;
    }


    public Drawable getImageView() {
        return image;
    }

    public void setImageView(Drawable image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
