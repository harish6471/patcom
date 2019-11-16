package com.example.expo.blogapp.Activities;

import  android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expo.blogapp.Cateogeories;
import com.example.expo.blogapp.Phonenumber.Authentication1;
import com.example.expo.blogapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity1 extends AppCompatActivity {

    private TextView toptext, conditions, forgotpassword,Signup;
    private EditText email, password;
    private CheckBox check;
    private FirebaseAuth mAuth;
    private Button login, signingoogle, signinfacebook, signinphonenumber;
    private ProgressDialog pdialog;
    private final static int RC_SIGN_IN = 2;
    private GoogleSignInClient mGoogleSignInClient;
    CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        Typeface pacifico = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        //  toptext = findViewById(R.id.toptext);
//        toptext.setTypeface(pacifico);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mCallbackManager = CallbackManager.Factory.create();
        signinfacebook = findViewById(R.id.signinfacebook);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        check = (CheckBox) findViewById(R.id.checkBox);
        forgotpassword = findViewById(R.id.forgotpassword);
     Signup= findViewById(R.id.Signup);
        signingoogle = findViewById(R.id.signingoogle);
        signinphonenumber = findViewById(R.id.signinphone);



        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        String text = "By signing in, you accept our <a href='http://www.google.com'>Terms and Conditions</a><br>& <a href='http://www.facebook.com'>Privacy Policy</a>";
        check.setText(Html.fromHtml(text));
        check.setClickable(true);
        check.setMovementMethod(LinkMovementMethod.getInstance());


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinwithmail();

            }

        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity1.this, ResetPasswordActivity.class);
                startActivity(i);
            }
        });
       Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity1.this, RegisterActivity1.class);
                startActivity(i);
            }
        });
       signinphonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity1.this, Authentication1.class);
                startActivity(i);
            }
        });



        signingoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinwithgoogle();
            }
        });

        signinfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!check.isChecked()) {
                    Toast.makeText(LoginActivity1.this, "please  click the checkbox to allow to signin", Toast.LENGTH_LONG).show();
                }else{
                buttonsState(false);
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity1.this, Arrays.asList("email", "public_profile"));}
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        buttonsState(true);
                        Toast.makeText(LoginActivity1.this, "Facebook login Cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        buttonsState(true);
                        Toast.makeText(LoginActivity1.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                    }


                });
            }
        });
        }

    private void signinwithmail() {
        String useremail = email.getText().toString();
        String userpassword = password.getText().toString();

        if (TextUtils.isEmpty(useremail)) {
            email.setError("Please enter your email");
        } else if (!useremail.contains("@") || !useremail.contains(".")) {
            email.setError("Please enter a valid Email");
        } else if (TextUtils.isEmpty(userpassword)) {
            password.setError("Please enter your password");
        } else if (!check.isChecked()) {
            Toast.makeText(LoginActivity1.this, "please  click the checkbox to allow to signin", Toast.LENGTH_LONG).show();
        }

         else {
            pdialog = new ProgressDialog(LoginActivity1.this, R.style.MyAlertDialogStyle);
            pdialog.setMessage("Please wait...");
            pdialog.setIndeterminate(true);
            pdialog.setCancelable(false);
            pdialog.setCanceledOnTouchOutside(false);
            pdialog.show();

            mAuth.signInWithEmailAndPassword(useremail, userpassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getInstance().getCurrentUser();
                                assert user != null;
                                if (user.isEmailVerified()) {
                                        finish();
                                        //Toast.makeText(LoginActivity1.this, "Verfied Email", Toast.LENGTH_LONG).show();
                                        //Toast.makeText(LoginActivity1.this, "Verfied Email", Toast.LENGTH_LONG).show();
                                        startactivity();
                                    } else {
                                    //  mAuth.getInstance().signOut();
                                       FirebaseAuth.getInstance().getCurrentUser();

                                    //  startActivity(intent);
                                    //  startActivity(intent);
                                    finish();
                                    }




                                pdialog.dismiss();
                            } else {
                                String message = task.getException().toString();

                                if (message.contains("password is invalid")){
                                    pdialog.dismiss();
                                    Toast.makeText(LoginActivity1.this, "Email or Password is Incorrect", Toast.LENGTH_LONG).show();
                                }else if (message.contains("There is no user")){
                                    pdialog.dismiss();
                                    Toast.makeText(LoginActivity1.this, "Account doesn't exists", Toast.LENGTH_LONG).show();
                                }else {
                                    pdialog.dismiss();
                                    Toast.makeText(LoginActivity1.this, "Unable to Login !", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                       /* private void checkIfEmailVerified() {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            if (user.isEmailVerified())
                            {
                                // user is verified, so you can finish this activity or send user to activity which you want.
                                finish();
                                Toast.makeText(LoginActivity1.this, "Successfully verified", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                // email is not verified, so just prompt the message to the user and restart this activity.
                                // NOTE: don't forget to log out the user.
                                FirebaseAuth.getInstance().signOut();

                                //restart this activity

                            }
                        }
*/

                    });
        }
    }





    private void signinwithgoogle() {
        if (!check.isChecked()) {
            Toast.makeText(LoginActivity1.this, "please  click the checkbox to allow to signin", Toast.LENGTH_LONG).show();
        }else {
            buttonsState(false);
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

        }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            saveUserDetails(mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getPhotoUrl().toString());
                        } else {
                            if (task.getException().toString().contains("already exists with the same email address")){
                                buttonsState(true);
                                Toast.makeText(LoginActivity1.this, "Account with same Facebook email already exists !", Toast.LENGTH_LONG).show();

                            }else {
                                buttonsState(true);
                                Toast.makeText(LoginActivity1.this, "Unable to Login !", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                buttonsState(true);
                Toast.makeText(LoginActivity1.this, "Something went wrong :(", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveUserDetails(mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getPhotoUrl().toString());
                        } else {
                            buttonsState(true);
                            Toast.makeText(LoginActivity1.this, "Couldn't Sign In :(", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void saveUserDetails(String name, String url){
        Map<String, Object> user = new HashMap<>();
        user.put("name",name);
        user.put("url",url);


        FirebaseFirestore.getInstance().collection("Users").document(mAuth.getCurrentUser().getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startactivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong :(",Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void startactivity(){
        Intent intent = new Intent(LoginActivity1.this, Cateogeories.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void buttonsState(boolean option){
        login.setEnabled(option);
        signingoogle.setEnabled(option);
        signinfacebook.setEnabled(option);
    }
}

