package com.example.expo.blogapp.Activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expo.blogapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Map;

public class Transcation extends AppCompatActivity  {
    PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
    //declare paymentParam object
    PayUmoneySdkInitializer.PaymentParam paymentParam = null;


    static String emailto = "", subject = "", message12 = "", url_hotel, mUserId;
    private CardView paytm1,upi2,pau2,razorpay1;
    private TextView title;
    private TextView account,profile,payment,help,lblStatus,statusMessage,responseTitle ,amounstatus,orderid,Amount,name,bill,hospitalname,title12;;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button done;
    private Toolbar toolbar;
    private ImageView bck_btn,iconStatus;
    LinearLayout layoutOrderPlaced;

    private Bundle bundle;
    private AVLoadingIndicatorView loader;
    String amount1;
    String name1;
    String hospitalname1;
    String hospitalbill1;
    String title1;
    String phonenumber;
    String orderId;
    String total;
   // int totalAmount=0;
    int  totalAmount=0;
    //int total=0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcation);
      //  bundle = getIntent().getExtras();


        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        account = findViewById(R.id.account);
        profile = findViewById(R.id.profile);
        payment = findViewById(R.id.payment);
        help = findViewById(R.id.help);
        bck_btn = findViewById(R.id.back_btn);
        Amount=findViewById(R.id.price);

       // loader = findViewById(R.id.loader);
       // lblStatus=findViewById(R.id.title_status);
        iconStatus = findViewById(R.id.icon_status);

        statusMessage = findViewById(R.id.status_message);
       done = findViewById(R.id.next);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        amount1 = intent.getExtras().getString("amount");
        name1 = intent.getExtras().getString("name");
        hospitalname1= intent.getExtras().getString("Hospitalname");
        title1= intent.getExtras().getString("Title");
       hospitalbill1 = intent.getExtras().getString("HospitalBill");
      orderId = intent.getExtras().getString("orderId");
        Amount.setText(amount1);
//        amounstatus.setText(amount1);

       /* name.setText(name1);
        bill.setText(hospitalbill1);
        title12.setText(title1);*/
//        showOrderStatus(true);

       /* emailto = email;
        subject = "Payment Confirmation by Bon Voyage";
        message12 = "Hi,\nYour payment is confirmed for the hotel " + name + "\non "
                + date + "\nfor " + day + " days.\nThe amount received by us is "
                + price + ".\nThanking you,\nBon Voyage Team.";*/
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails();
               // saveAmount();
              //  sendEmail();
            //    startActivity(new Intent(Transcation.this,MainActivity.class));


            }
        });





    }




    private void saveDetails() {


        String amount12 = amount1;
        String title = title1;

        String name=name1;
        String hospitalbill=hospitalbill1;

        String user = mAuth.getCurrentUser().getUid();

        Map<String, Object> post = new HashMap<>();
        post.put("User", user);
     //   post.put("Views", 0);


        post.put("hospitalbill", hospitalbill);
        post.put("Title", title);
       // post.put("")
        post.put("Name",name1);
        post.put("Amountpaid", amount12);

       // post.put("Require Amount", amountgoal);

      //  post.put("Desc", desc);
       /// post.put("Pending", pending);
      //  post.put("Phonenumber", phonenumber);
        // post.put("Upi", upi);
     //   post.put("Details", details);

        FirebaseFirestore.getInstance().collection("Bills").document(hospitalbill).collection("Users").document(name)
                .set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        startActivity(new Intent(Transcation.this, MainActivity.class));
                        finish();
                      //  Toast.makeText(Transcation.this, "Post uploaded successfully :)", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void saveAmount() {

        int sum = 0;
        int x=Integer.valueOf(Amount.getText().toString());

        for (int i = 0; i>=0  ;i++) {
            sum = sum +x;

        }


            // String bestAmount=String.valueOf(totalAmount); String hospitalbill = bill.getText().toString();
            String bestAmount = String.valueOf(sum);
            String hospitalbill = bill.getText().toString();
            String title = title12.getText().toString();


            Map<String, Object> post = new HashMap<>();
            // post.put("User", user);
            //   post.put("Views", 0);


            post.put("hospitalbill", hospitalbill);
            // post.put("Title", title);
            // post.put("")
            //   post.put("Name",name12);
            post.put("Amountpaid", bestAmount);
            post.put("Title", title);
            FirebaseFirestore.getInstance().collection("Bills").document(hospitalbill).collection("TotalAmount").document(hospitalbill)
                    .set(post)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

//                        hospitalname.setText("");
                            bill.setText("");
                            Amount.setText("");
                            title12.setText("");
                            //   name.setText("");
                            //   post_phonenumber.setText("");
                            //   post_upi.setText("");

                            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        /*FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        getFragmentManager().beginTransaction().replace(R.id.home1, new HomeFragment1()).commitNowAllowingStateLoss();;*/


// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
                            startActivity(new Intent(Transcation.this, MainActivity.class));
                            finish();
                            //  Toast.makeText(Transcation.this, "Post uploaded successfully :)", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
                        }
                    });

            // String amount12 = name.getText().toString();
            // String title = title12.getText().toString();
            //  final String pending="pending";
            String name1 = name.getText().toString();



        }
    private void sendEmail() {
        //Creating SendMail object
       // SendMail sm = new SendMail(this, emailto, subject, message12);

        //Executing sendmail to send email
       // sm.execute();
    }


    }
