package com.example.expo.blogapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.expo.blogapp.Activities.Modal.Blog;
import com.example.expo.blogapp.Models.Api;
import com.example.expo.blogapp.Models.Checksum;
import com.example.expo.blogapp.Models.Constants;
import com.example.expo.blogapp.Models.Paytm;
import com.example.expo.blogapp.Models.User;
import com.example.expo.blogapp.PayUmoney;
import com.example.expo.blogapp.R;
import com.example.expo.blogapp.StartPaymentActivity;
import com.example.expo.blogapp.cardpayment;
import com.example.expo.blogapp.paytmActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Upiactivity extends AppCompatActivity {
    private EditText amountEt;
    private EditText noteEt;
    private EditText nameEt;
    private EditText upiIdEt;
    private String phoneEt;
    private EditText hospitalnameEt;
    private EditText billET;
    private EditText userphoneEt;
    String TAG = "Payment Error";
    private String username;
    private ImageView close;
    float amount;
    private String useremail;
    FirebaseUser firebaseUser;
    private Bundle bundle;
    FirebaseAuth mAuth;
    private FirebaseUser user;
    DialogInterface dialog;
    private FirebaseFirestore firebaseFirestore;
    StorageReference storageRef;
    private StorageTask uploadTask;
    //  private final FirebaseFirestore db;
    Button send, pay;

    private Toolbar toolbar;
    private EditText Amoun1, Phonenumber1, Fullname1, Hospitalname1, Hospitalbill1, Sharenote1;
    private TextInputLayout Amount, Phonenumber, Fullname, Hospitalname, Hospitalbill, Sharenote;
    private Button donate;

    final int UPI_PAYMENT = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upiactivity1);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Amount = (TextInputLayout) findViewById(R.id.amount);
        Phonenumber = (TextInputLayout) findViewById(R.id.userphone);
        Fullname = (TextInputLayout) findViewById(R.id.username1);
        Hospitalname = (TextInputLayout) findViewById(R.id.hospitalname);
        Hospitalbill = (TextInputLayout) findViewById(R.id.hospitalbill);
        Sharenote = (TextInputLayout) findViewById(R.id.note11);
        Amoun1 = (EditText) findViewById(R.id.amount12);
        Phonenumber1 = (EditText) findViewById(R.id.userphone1);
        Fullname1 = (EditText) findViewById(R.id.username2);
        Hospitalname1 = (EditText) findViewById(R.id.hospitalname1);
        Hospitalbill1 = (EditText) findViewById(R.id.hospitalbill1);
        Sharenote1 = (EditText) findViewById(R.id.note);

        donate = (Button) findViewById(R.id.donate);
        Amoun1.addTextChangedListener(new MyTextWatcher(Amoun1));
        Phonenumber1.addTextChangedListener(new MyTextWatcher(Phonenumber));
        Fullname1.addTextChangedListener(new MyTextWatcher(Fullname1));
       Hospitalname1.addTextChangedListener(new MyTextWatcher(Hospitalname1));
        Hospitalbill1.addTextChangedListener(new MyTextWatcher(Hospitalbill1));
      Sharenote1.addTextChangedListener(new MyTextWatcher(Sharenote1));
        //.addTextChangedListener(new MyTextWatcher(Fullname1));
        initializeViews();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            // upiIdEt.setText("patcom@ybl");
            //.setText(" " + bundle.getString("Phonenumber1"));
            Hospitalname1.setText(" " + bundle.getString("Hospitalname1"));
            Hospitalbill1.setText(" " + bundle.getString("HospitalBill1"));
        //    Phonenumber1.setText(" " + bundle.getString("Title"));


            // setUser(User);
            //   nameEt.setText(""+bundle.getString("UserId1"));
            ;
            //nameEt.setText(""+bundle.getString("User"));
            ;

        }

        //setUser(User);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //  storageRef = FirebaseStorage.getInstance().getReference("uploads");
        mAuth = FirebaseAuth.getInstance();
        // String user = mAuth.getCurrentUser().getUid();
        String user = mAuth.getCurrentUser().getUid();

      /*  send.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Getting the values from the EditTexts
            String amount = amountEt.getText().toString();
            String note = noteEt.getText().toString();
            String name = nameEt.getText().toString();
            String upiId = upiIdEt.getText().toString();
            payUsingUpi(amount, upiId, name, note);
        }
    });*/
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // submit();
                submit();


                //Getting the values from the EditTexts
              /*  AlertDialog.Builder mBuilder = new AlertDialog.Builder(Upiactivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_linear_layout, null);
                Button razorpay = (Button) mView.findViewById(R.id.razorpay);
                Button paytm = (Button) mView.findViewById(R.id.paytmButton);
                Button upi = (Button) mView.findViewById(R.id.upi);
                Button payu = (Button) mView.findViewById(R.id.payu);
            //  ImageView close =  mView.findViewById(R.id.close1);

                //  Button cancel = (Button) mView.findViewById(R.id.cancel);

                //   AlertDialog mBuilder= builder.create();


                mBuilder.setView(mView);
               // mBuilder.setMessage("Pay using below Payment Methods");
                mBuilder.setIcon(android.R.drawable.ic_dialog_alert);
                final AlertDialog dialog = mBuilder.create();

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

                wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
                wmlp.x = 300;   //x position
                wmlp.y = 0;   //y position
                dialog.show();


                razorpay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        amount=Integer.parseInt(amountEt.getText().toString().trim());
                        amount=amount*100;

                        startPayment(amount);
                        //  startActivity(new Intent(Upiactivity.this,Razorpay.class));
                    }
                });
                paytm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // startActivity(new Intent(Upiactivity.this, paytmActivity.class));
                        generateCheckSum();
                    }
                });
                upi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // startActivity(new Intent(Upiactivity.this, paytmActivity.class));
                        String amount = amountEt.getText().toString();
                        String note = noteEt.getText().toString();
                        String name = nameEt.getText().toString();
                        String upiId = upiIdEt.getText().toString();
                        payUsingUpi(amount, upiId, name, note);

                    }
                });

                payu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // startActivity(new Intent(Upiactivity.this, paytmActivity.class));
                        Intent intent = new Intent(Upiactivity.this, StartPaymentActivity.class);
                        intent.putExtra("phone", phoneEt.getText().toString());
                        intent.putExtra("amount", amountEt.getText().toString());
                        startActivity(intent);

                    }
                });
               /* close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        dialog.setMessage("cancelling");
                        //startActivity(new Intent(Upiactivity.this, paytmActivity.class));
                    }
                });*/

                //payUsingUpi(amount, upiId, name, note);
                /*
            }
        });*/
                // savePostDetails();
                // getProfileDetails();
                //setPostImage(ImageUrl);
                // setUser(User);
                // upiIdEt.setText("Upi id :"+Upi);
                //    upiIdEt.setText(Upi);

                // nameEt.setText(User);

            }


        });
    }

    private boolean submit() {
        if (!validateName()) {
        }

        if (!validateHospitalname()) {
        }

        if (!validateHospitalbill()) {
        }
        if (!validateamount()) {
        }
        if (!validatephone()) {
        }else{
            newdone();

        }
        return true;
       // Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateHospitalbill() {
        if (Hospitalbill1.getText().toString().trim().isEmpty()) {
            Hospitalbill.setError(getString(R.string.err_msg_bill));
            requestFocus(Hospitalbill1);
            return false;
        } else {
            Hospitalbill.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatephone() {
      //  String phone = Phonenumber1.getText().toString();
        if (!isValidMobile(Phonenumber1.getText().toString())) {
            Phonenumber.setError(getString(R.string.err_msg_phone));
            requestFocus(Phonenumber1);
            return false;
           /* if (!Pattern.matches("[a-zA-Z]+", phone)) {
                return phone.length() > 6 && phone.length() <= 13;

            }
            return false;*/
        } return true;
    }

    private boolean isValidMobile(String phone) {

        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 6 && phone.length() <= 13;

        }
        return false;
    }

    private boolean validateamount() {
        if (Amoun1.getText().toString().trim().isEmpty()) {
            Amount.setError(getString(R.string.err_msg_amoun1));
            requestFocus(Amoun1);
            return false;
        } else {
            Amount.setErrorEnabled(false);
        }

        return true;

    }

    private boolean validateHospitalname() {
        if (Hospitalname1.getText().toString().trim().isEmpty()) {
            Hospitalname.setError(getString(R.string.err_msg_hospital));
            requestFocus(Hospitalname1);
            return false;
        } else {
          Hospitalname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateName() {
        if (Fullname1.getText().toString().trim().isEmpty()) {
            Fullname.setError(getString(R.string.err_msg_name));
            requestFocus(Fullname1);
            return false;
        } else {
            Fullname.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private void initializeViews() {
        //  send = findViewById(R.id.send1);
      /*  pay = findViewById(R.id.next);
        amountEt = findViewById(R.id.amount_et1);
        noteEt = findViewById(R.id.note1);
        nameEt = findViewById(R.id.name1);
        hospitalnameEt = findViewById(R.id.hospitalname);
        billET= findViewById(R.id.hospitalbill);
        userphoneEt = findViewById(R.id.userphone);
        upiIdEt = findViewById(R.id.upi_id1);
        phoneEt = findViewById(R.id.phone12);*/
    }
    private void newdone() {
        Intent intent = new Intent(Upiactivity.this, cardpayment.class);
        //intent.putExtra("phone", phoneEt.getText().toString());
        intent.putExtra("amount", Amoun1.getText().toString());
        intent.putExtra("Hospitalname", Hospitalname1.getText().toString());
        intent.putExtra("HospitalBill", Hospitalbill1.getText().toString());
        intent.putExtra("name", "Patcom");
        //  intent.putExtra("Title", userphoneEt.getText().toString());
         intent.putExtra("upiid", "8309763051@ybl");
          intent.putExtra("note",Sharenote1.getText().toString() );
        startActivity(intent);
    }


    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.amount12:
                    validateamount();
                    break;
                case R.id.userphone1:
                    validatephone();
                    break;
                case R.id.username2:
                    validateName();
                    break;
                case R.id.hospitalname1:
                    validateHospitalname();
                    break;
                case R.id.hospitalbill1:
                    validateHospitalbill();
                    break;
            }
        }
    }

}



