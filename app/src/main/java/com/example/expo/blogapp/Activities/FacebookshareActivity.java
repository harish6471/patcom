package com.example.expo.blogapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.expo.blogapp.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FacebookshareActivity extends AppCompatActivity {


    Button link,photo,video;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    private  static  final int REQUEST_VIDEO_CODE=100;

    Target target=new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto photo=new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();
            if(ShareDialog.canShow(SharePhotoContent.class))
            {
                SharePhotoContent content=new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content);
            }
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==REQUEST_VIDEO_CODE){
                Uri selected=data.getData();
                ShareVideo video=new ShareVideo.Builder()
                        .setLocalUrl(selected)
                        .build();
                ShareVideoContent videoContent=new ShareVideoContent.Builder()
                        .setContentTitle("video")
                        .setContentDescription("funny")
                        .setVideo(video)
                        .build();
                if(shareDialog.canShow(ShareVideoContent.class))
                    shareDialog.show(videoContent);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        setContentView(R.layout.activity_facebookshare);
        link=findViewById(R.id.link);
       // photo=findViewById(R.id.photo);
        video=findViewById(R.id.video);
        callbackManager= CallbackManager.Factory.create();
        shareDialog=new ShareDialog(this);
        ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
        setImageShare();
     //   shareDialog.registerCallback(callbackManager, callback);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(FacebookshareActivity.this, "Share Success", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(FacebookshareActivity.this, "Share cancelled", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(FacebookshareActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("this is useful link")
                        .setContentUrl(Uri.parse("https://youtube.com"))
                        .build();
                if (shareDialog.canShow(ShareLinkContent.class)) {
                    shareDialog.show(linkContent);
                }
            }

        });

     /*  photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(FacebookshareActivity.this, "Share Success", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(FacebookshareActivity.this, "Share cancelled", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(FacebookshareActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });
              /*  Glide.with(getApplicationContext())
                        .asBitmap()
                        .load("https://en.wikipedia.org/wiki/Sai_Pallavi#/media/File:Sai_pallavi_interview.png")
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                SharePhoto photo=new SharePhoto.Builder()
                                    .setBitmap(resource)
                                    .build();
                                if(ShareDialog.canShow(SharePhotoContent.class))
                                {
                                    SharePhotoContent content=new SharePhotoContent.Builder()
                                            .addPhoto(photo)
                                            .build();
                                    shareDialog.show(content);
                                }}
                        });
            }*//*Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image)
                        .setCaption("#Tutorialwing")
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();

               ShareButton shareButton = (ShareButton) view.findViewById(R.id.fb_share_button);
               shareButton.setShareContent(content);
              /*Glide.with(getApplicationContext())
                      .load("https://en.wikipedia.org/wiki/Sai_Pallavi#/media/File:Sai_pallavi_interview.png")
                      .into(target);*/
            /*
        });*/
        video.setOnClickListener(new View.OnClickListener() {


            @Override

            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select video"),REQUEST_VIDEO_CODE);
            }
        });



        printKeyHash();
    }

    private void setImageShare() {
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("#Tutorialwing")
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
        shareButton.setShareContent(content);
    }




    private void printKeyHash() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.example.expo.blogapp.Activities", PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                MessageDigest md=MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));

            }
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
