package com.example.expo.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expo.blogapp.Activities.AccountActivity;
import com.example.expo.blogapp.Activities.ChangeEmailAcitivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PrivacyActivity extends AppCompatActivity {
    private CardView cdelete;
   private  TextView title;
  private Toolbar toolbar;
    private ImageView bck_btn;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        cdelete = findViewById(R.id.chcard);
        bck_btn=findViewById(R.id.back_btn);
        mAuth = FirebaseAuth.getInstance();


        cdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Intent i=new Intent(PrivacyActivity.this, ChangeEmailAcitivity.class);
              //  startActivity(i);

                final FirebaseUser user =mAuth.getInstance().getCurrentUser();


                AuthCredential credential = EmailAuthProvider
                        .getCredential("user@example.com", "password1234");

                // Prompt the user to re-provide their sign-in credentials
                assert user != null;
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    System.out.println("Successfully deleted user.");
                                                 //   Log.d(TAG, "User account deleted.");
                                                }
                                            }
                                        });

                            }
                        });
              //  FirebaseAuth.getInstance().deleteUser(uid);
              //  System.out.println("Successfully deleted user.");



            }
        });
    }
}
