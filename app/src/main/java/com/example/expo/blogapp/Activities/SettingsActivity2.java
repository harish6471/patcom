package com.example.expo.blogapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expo.blogapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.rpc.Help;
import com.webianks.easy_feedback.EasyFeedback;

public class SettingsActivity2 extends AppCompatActivity {

    private CardView account1,profile1,payment1,help1,logout,feedback;
    private TextView title;
    private TextView account,profile,payment,help;
 private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Toolbar toolbar;
    private ImageView bck_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);

        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        account = findViewById(R.id.account);
       feedback = findViewById(R.id.feedback);
        profile = findViewById(R.id.profile);
      payment= findViewById(R.id.payment);
        help= findViewById(R.id.help);
        bck_btn=findViewById(R.id.back_btn);
        account1 = findViewById(R.id.accountcardview);
        profile1 = findViewById(R.id.profilecardview);
        payment1= findViewById(R.id.paymentcardview);

        help1= findViewById(R.id.helpcardview);
        logout= findViewById(R.id.logoutcard
        );


        account1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(SettingsActivity2.this,AccountActivity.class);
                startActivity(i);

            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new EasyFeedback.Builder(SettingsActivity2.this)
                        .withEmail("patcom.h@gmail.com")
                        .withSystemInfo()
                        .build()
                        .start();

            }
        });

        mAuth = FirebaseAuth.getInstance();

       logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

               // Intent i=new Intent(SettingsActivity2.this,AccountActivity.class);
               // startActivity(i);

            }
        });
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    SettingsActivity2.this.finish();
                    Intent main =  new Intent(SettingsActivity2.this, LoginActivity1.class);
                    main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                           Intent.FLAG_ACTIVITY_CLEAR_TASK |
                   Intent.FLAG_ACTIVITY_NEW_TASK);
                  //  startActivity(intent);
                  startActivity(main);
                    finish();
                }


            }
        });
       profile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SettingsActivity2.this,SettingsActivity.class);
                startActivity(i);


            }
        });
        payment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
       help1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SettingsActivity2.super.onBackPressed();
            }
        });

    }
}
