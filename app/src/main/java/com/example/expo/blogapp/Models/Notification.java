package com.example.expo.blogapp.Models;

import android.widget.ImageView;

public class Notification {

        private String userid;
        private String text;
        private String postid;
        private boolean ispost;
     private String Image;

        public Notification(String userid, String text, String postid, boolean ispost,String image) {
            this.userid = userid;
            this.text = text;
            this.postid = postid;
            this.ispost = ispost;
            this.Image = image;
        }

        public Notification() {
        }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getPostid() {
            return postid;
        }

        public void setPostid(String postid) {
            this.postid = postid;
        }

        public boolean isIspost() {
            return ispost;
        }

        public void setIspost(boolean ispost) {
            this.ispost = ispost;
        }
    }

