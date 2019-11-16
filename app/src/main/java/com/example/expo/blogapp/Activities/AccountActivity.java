package com.example.expo.blogapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expo.blogapp.PrivacyActivity;
import com.example.expo.blogapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    private CardView cemail,cpass,cphone,cprivacy,chupdate1;
    private TextView title;
    private TextView chemail,chpass,chphone,privacy;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
   // private Button logout_btn;
    private Toolbar toolbar;
    private ImageView bck_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
       cpass = findViewById(R.id.chcard);


        cemail = findViewById(R.id.ecard);

       cprivacy= findViewById(R.id.cprivacy);
       bck_btn=findViewById(R.id.back_btn);
      //  logout_btn = findViewById(R.id.logout_btn);
        Typeface extravaganzza = Typeface.createFromAsset(getAssets(), "fonts/extravaganzza.ttf");
     //   logout_btn.setTypeface(extravaganzza);
     /*   mAuth = FirebaseAuth.getInstance();
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                   AccountActivity.this.finish();
                    Intent main =  new Intent(AccountActivity.this, LoginActivity1.class);
                    main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main);
                }
            }
        });*/

        cemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AccountActivity.this,ChangeEmailAcitivity.class);
                startActivity(i);



            }
        });

        cpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AccountActivity.this,ResetPasswordActivity.class);
                startActivity(i);



            }
        });

        cprivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AccountActivity.this, PrivacyActivity.class);
                startActivity(i);


            }
        });
        bck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountActivity.super.onBackPressed();
            }
        });

    }
}
