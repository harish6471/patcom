package com.example.expo.blogapp.Activities;

import android.content.Intent;
import android.graphics.Typeface;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expo.blogapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class WelcomeScreenActivity extends AppCompatActivity {

    TextView toptext, quotetext;
    private FirebaseAuth mAuth;
    Button signup, signin;

 @Override
   protected void onStart() {
       super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null){
            Intent intent = new Intent(WelcomeScreenActivity.this,MainActivity.class);
            startActivity(intent);
        }
     /*if (mAuth.getCurrentUser() == null){
         Intent intent = new Intent(WelcomeScreenActivity.this,Login.class);
         startActivity(intent);
     }*/
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Typeface pacifico = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");


        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        quotetext = findViewById(R.id.quotetext);
        toptext = findViewById(R.id.toptext);
        toptext.setTypeface(pacifico);
        quotetext.setTypeface(pacifico);

        showquotes();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeScreenActivity.this, LoginActivity1.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeScreenActivity.this, RegisterActivity1.class));
            }
        });
    }

    private void showquotes(){
        String quote1 ="Fundraisers are the catalysts of change.";
        String quote2 ="No one is useless in this world who lightens the burdens of another.";

        String[] quotes = {quote1,quote2};
        Random r = new Random();
        int n = r.nextInt(2);
        quotetext.setText("\""+quotes[n]+"\"");

    }
}
