package com.example.expo.blogapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expo.blogapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ManageUserActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String DummyEmail = "harishprabha647@gmail.com";
    private CardView chchange;
    private Toolbar toolbar;
    private ImageView bck_btn;
    private EditText newpassword;
    private Button update;
    private TextView title;
    private TextView mTextViewProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        // chchange = findViewById(R.id.chchange);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        newpassword=(findViewById(R.id.newpassword));
        mTextViewProfile = findViewById(R.id.mtext1);
        update = findViewById(R.id.Update1);
        mAuth = FirebaseAuth.getInstance();

        bck_btn = findViewById(R.id.back_btn);
        findViewById(R.id.Update1).setOnClickListener(this);

        bck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword.super.onBackPressed();
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                //    updateUI(user);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onClick(View view) {
        final FirebaseUser user = mAuth.getCurrentUser();
        switch (view.getId()) {
            case R.id.Update1:

                if (validatePassword()) {
                    updatePassword(user);
                }
                break;
        }
    }
    private void updatePassword(FirebaseUser user) {
        // showProgressDialog();
        user.updatePassword(newpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (task.isSuccessful()) {
                        mTextViewProfile.setTextColor(Color.DKGRAY);
                        // mTextViewProfile.setText(getString(R.string.updated, "User password"));
                    } else {
                        mTextViewProfile.setTextColor(Color.RED);
                        mTextViewProfile.setText(task.getException().getMessage());
                    }
                    // hideProgressDialog();
                }
            }
        });
    }

    private boolean validatePassword() {
        String password = newpassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            newpassword.setError("Required.");
            return false;
        } else {
            newpassword.setError(null);
            return true;
        }
    }

}



