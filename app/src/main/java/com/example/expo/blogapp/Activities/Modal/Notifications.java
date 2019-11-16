package com.example.expo.blogapp.Activities.Modal;


public class Notifications {


   // public String getPost;

    private String Image;


    private long Time;
   // private String Title;
   private String User;



    private String Title;
    private String Body;



    public Notifications(){}

    public Notifications(String postid, String website, String publisher, String user, int views, String image, long time, String desc, int type, int data, String text, String body,  String title) {



        Time = time;
        User = user;

        Image = image;


        Title=title;


       Body=body;
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


    public String getUser() {
        return User;}
    public void setUser(String user) {
        User = user;
    }


    public String getBody() {
        return Body;
    }
    public void setBodyl(String body) {
      Body=body;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title=title;
    }


}