package com.example.expo.blogapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expo.blogapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.HashMap;


public class RegisterActivity1 extends AppCompatActivity {
    private TextView toptext, conditions,login;
    private Button signup;
    private CheckBox check;
    private ImageView avatar;
    private EditText fullname,email,password,cnfpassword;
    private Uri mainImageUri;
    private FirebaseAuth mAuth;
    private ProgressDialog pdialog;
    private Uri photo_url;
    private FirebaseUser user;
    private StorageReference storageReference;
    public static final int PICK_IMAGE = 1;
    private DatabaseReference reference;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);



        Typeface pacifico = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
      //  avatar = findViewById(R.id.avatar);
        fullname = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        cnfpassword = findViewById(R.id.cnfpassword);
        signup = findViewById(R.id.signup);
        login=findViewById(R.id.login);

        check=findViewById(R.id.checkBox);

        String text = "By signing up, you accept our <a href='http://www.google.com'>Terms and Conditions</a><br>& <a href='http://www.facebook.com'>Privacy Policy</a>";
       check.setText(Html.fromHtml(text));
        check.setClickable(true);
        check.setMovementMethod(LinkMovementMethod.getInstance());

user=mAuth.getCurrentUser();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = fullname.getText().toString();
                final String mail = email.getText().toString();
                final String pwd = password.getText().toString();
                final String cnfpwd = cnfpassword.getText().toString();
                if (!check.isChecked()) {
                    Toast.makeText(RegisterActivity1.this, "please  click the checkbox to allow to signin", Toast.LENGTH_LONG).show();
                }
                if(checkfields(name, mail, pwd, cnfpwd)){
                    pdialog = new ProgressDialog(RegisterActivity1.this, R.style.MyAlertDialogStyle);
                    pdialog.setMessage("Signing up...");
                    pdialog.setIndeterminate(true);
                    pdialog.setCanceledOnTouchOutside(false);
                    pdialog.setCancelable(false);
                    pdialog.show();
                    createuser(mail,pwd,name);
                }
            }
        });


    }

    private void sendVerificationEmail() {
       final  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        user.sendEmailVerification()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {



                        Toast.makeText(RegisterActivity1.this,
                                "Check your email for verification " + user.getEmail(),


                                Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();

                        finish();

                    } else {
                        Log.e(TAG, "sendEmailVerification", task.getException());
                        Toast.makeText(RegisterActivity1.this,
                                "Failed to send verification email.",
                                Toast.LENGTH_SHORT).show();

                        overridePendingTransition(0, 0);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());

                    }
                }
            });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void createuser(final String email, final String password, final String name){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            signin(email,password,name);
                            sendVerificationEmail();
                        }

                         else {
                            if (task.getException().toString().contains("already in use")){
                                pdialog.dismiss();
                                Toast.makeText(RegisterActivity1.this, "User already exists, please sign in !", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(RegisterActivity1.this, "Something wrong happened :(",Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });

    }




    private boolean checkfields(String name, String mail, String pwd, String cnfpwd){

        if (TextUtils.isEmpty(name)){
            fullname.setError("Name can't be empty");
        }if (TextUtils.isEmpty(mail)){
            email.setError("Email can't be empty");
        }if (TextUtils.isEmpty(pwd)){
            password.setError("Please enter a password");
        }if (TextUtils.isEmpty(cnfpwd)){
            cnfpassword.setError("Please confirm your password");
        }else if (name.length()<3){
            fullname.setError("Name must be of at least 3 characters");
        }else if (!mail.contains("@") || !mail.contains(".")){
            email.setError("Please enter a valid email");
        }else if (pwd.length()<6){
            password.setError("Password must be of at least 5 characters");
        }else if (!cnfpwd.matches(pwd)){
            password.setError("Passwords don't match !");
            cnfpassword.setError("Passwords don't match !");
        }
        else {
            return true;
        }

        return false;
    }

    private void signin(String email, String password, final String name){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    String userID = firebaseUser.getUid();
                    photo_url=user.getPhotoUrl();

                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", userID);
                 //   map.put("username", username.toLowerCase());
                    map.put("fullname", name);
                    map.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/blog-7d44c12.appspot.com/o/userphoto1.png?alt=media&token=c5e268c0-050d-42e6-8b76-38b3326f1850");
                    //map.put("bio", "");
                   // map.put("imageurl",photo_url);

                    reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                pdialog.dismiss();
                               // Intent intent = new Intent(RegisterActivity1.this, MainActivity.class);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                //startActivity(intent);
                            }
                        }
                    });
                    updatename(name);
                }else {
                    pdialog.dismiss();
                    Toast.makeText(RegisterActivity1.this, "Something went wrong :(", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void updatename(final String name) {
        UserProfileChangeRequest profileupdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        mAuth.getCurrentUser().updateProfile(profileupdates).addOnCompleteListener(
              //  reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                        }else {
                            Toast.makeText(RegisterActivity1.this, "Something went wrong :(", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

    }


}


