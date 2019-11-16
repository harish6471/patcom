package com.example.expo.blogapp.Models;

public class Slide {
    private String imageurl;

    public Slide( String imageurl) {
        this.imageurl = imageurl;

    }
    public Slide() {
    }

    public String getImageId() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
