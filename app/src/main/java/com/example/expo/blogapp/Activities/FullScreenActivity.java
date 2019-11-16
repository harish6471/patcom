package com.example.expo.blogapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.expo.blogapp.R;

public class FullScreenActivity extends AppCompatActivity  {

 private ImageView fullscreenImageView;
    private Bundle bundle;
    TextView header1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
      //  getSupportActionBar().setTitle("Document");
        Typeface pacifico = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        header1 = findViewById(R.id.header1);
        header1.setTypeface(pacifico);
        fullscreenImageView = (ImageView) findViewById(R.id.fullimage);
        bundle = getIntent().getExtras();


        String ImageUrl = bundle.getString("ImageUrl");
        setFullImage(ImageUrl);

    }
    private void setFullImage(String url){
        Glide.with(this)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.color.com_facebook_device_auth_text)
                        .error(R.color.com_facebook_device_auth_text)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .load(url)
                .into(fullscreenImageView);
    }
}


