package com.example.expo.blogapp.Activities.Modal;


public class Blog {
    private String postid;
    public static final int VIEW_PAGER = 0;
    public static final int IMAGE_TYPE = 1;
    public static final int AUDIO_TYPE = 2;
    public int type;
    public int data;
    public String text;

   // public String getPost;
    private String User;
    private int Views;
    private String Image;
    private long Time;
    private String Title;
    private String review;
    private String Desc;
    private String Upi;
    private String  Phonenumber;
    private  String Hospitalname;
    private  String Hospitalbill;
    private String Details;
    private String publisher;
    private String Amount;
    private String Amountgoal;



    public Blog(){}

    public Blog(String postid,String publisher,String user, int views, String image, long time, String title, String desc,int type,int data,String text, String amount, String details,String upi,String phonenumber,String hospitalname,String hospitalbill,String review,String amountgoal) {

        User = user;
        Views = views;
        Image = image;
       this.review=review;
        Time = time;
        Title = title;
        Hospitalname=hospitalname;
        Amountgoal=amountgoal;
        Hospitalbill=hospitalbill;

        Upi=upi;
        Phonenumber=phonenumber;
        Desc = desc;
        this.type = type;
        this.data = data;
        this.text = text;
        Amount=amount;
        Details = details;
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
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review ){
       this.review = review;
    }

    public String getUpi() {
        return Upi;
    }

    public void setUpi(String upi) {
        Upi = upi;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber=phonenumber;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }
    public String getHospitalname() {
        return Hospitalname;
    }

    public void setHospitalname(String hospitalname) {
      Hospitalname=hospitalname;
    }


    public void setHospitalbill(String hospitalbill) {
        Hospitalbill=hospitalbill;
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
    public String getHospitalbill() {
        return Hospitalbill;
    }

}