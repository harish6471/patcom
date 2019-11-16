package com.example.expo.blogapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expo.blogapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ChangeEmailAcitivity extends AppCompatActivity  implements View.OnClickListener {

    private static final String TAG = "ManageUserActivity";
    private TextView mTextViewProfile;
    private FirebaseAuth mAuth;
    private ProgressDialog pdialog;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText updateemail;
    private Button update;
    private Toolbar toolbar;
    private ImageView bck_btn;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email_acitivity);
        updateemail = findViewById(R.id.changeemail);
        update = findViewById(R.id.Update);
        findViewById(R.id.Update).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        mTextViewProfile = findViewById(R.id.mtext);
        bck_btn = findViewById(R.id.back_btn);
        bck_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                ChangeEmailAcitivity.super.onBackPressed();
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
            case R.id.Update:
                if (validateEmail(updateemail)) {

                    //  updateEmail(user)
                    updateEmail(user);

                    break;
                }
        }

    }


    private void updateEmail(FirebaseUser user) {
        //   showProgressDialog();
        user.updateEmail(updateemail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mTextViewProfile.setTextColor(Color.DKGRAY);
                    //  Toast.makeText(getApplicationContext(), "New Email was Updated", Toast.LENGTH_LONG).show();
                    mTextViewProfile.setText(getString(R.string.updated, "User email"));
                    //  startActivity(new Intent(ChangeEmailAcitivity.this,MainActivity.class));


                } else {
                    //  mTextViewProfile.setTextColor(Color.RED);
                    mTextViewProfile.setTextColor(Color.RED);
                    mTextViewProfile.setText(task.getException().getMessage());
                    // mTextViewProfile.setText(task.getException().getMessage());
                }
                //  hideProgressDialog();
            }
        });
    }

    public void getMessage()
    {
        Toast.makeText(getApplicationContext(), "please login again with email not facebook or google", Toast.LENGTH_LONG).show();
    }
    private boolean validateEmail(EditText edt) {
        String email = edt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            edt.setError("Required.");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt.setError("Invalid.");
            return false;
        } else {
            edt.setError(null);
            return true;
        }
    }

}
