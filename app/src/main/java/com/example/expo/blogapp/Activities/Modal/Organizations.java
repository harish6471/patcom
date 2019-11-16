package com.example.expo.blogapp.Activities.Modal;


import com.example.expo.blogapp.Activities.WelcomeScreenActivity;

public class Organizations {
    private String postid;
    public static final int VIEW_PAGER = 0;
    public static final int IMAGE_TYPE = 1;
    public static final int AUDIO_TYPE = 2;
    public int type;
    public int data;
    public String text;

   // public String getPost;
    private String User;
    private String Image;
    private int Views;

    private long Time;
   // private String Title;
    private String review;
    private String State;
    private String Website;
    private String City;



    private String Amount;
    private String Amountgoal;



    public Organizations(){}

    public Organizations(String postid,String website, String publisher, String user, int views, String image, long time, String title, String desc, int type, int data, String text, String amount, String state, String city, String phonenumber, String hospitalname, String hospitalbill, String review, String amountgoal) {

        User = user;
        Views = views;
       this.review=review;
        Time = time;
        Website=website;

       State = state;
        Image = image;
       City = city;

        Amountgoal=amountgoal;

        this.type = type;
        this.data = data;
        this.text = text;
        Amount=amount;
    }


    public String getUser() {
        return User;
    }

    public String getPostid() {
        return postid;
    }
    public void setPostid(String postid) {
        this.postid = postid;
    }

    public void setUser(String user) {
        User = user;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
    }
    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
    public void setFullImage(String image) {
        Image = image;
    }


    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
    }



    public String getReview() {
        return review;
    }

    public void setReview(String review ){
       this.review = review;
    }


    public String getAmountgoal() {
        return Amountgoal;
    }
    public void setAmountgoal(String amountgoal) {
      Amountgoal=amountgoal;
    }
    public String getAmount() {
        return Amount;
    }
    public void setAmount(String amount) {
        Amount=amount;
    }
    public String getCity() {
        return City;
    }
    public void setCity(String city) {
        City=city;
    }
    public String getState() {
        return State;
    }
    public void setState(String state) {
        State=state;
    }
    public String getWebsite() {
        return Website;
    }
    public void setWebsite(String website) {
        Website =website;
    }

}