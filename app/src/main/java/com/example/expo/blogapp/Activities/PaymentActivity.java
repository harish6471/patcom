package com.example.expo.blogapp.Activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expo.blogapp.R;
import com.facebook.share.widget.ShareButton;
import com.google.firebase.auth.FirebaseAuth;

public class PaymentActivity extends AppCompatActivity {

    Button Upi;
    View view;
    FirebaseAuth mAuth;
    String user_id;
    TextView view_count;
    TextView head1;
    TextView head2;
    ImageView views_icon;
    String user;
    private ShareButton shareButton;
    //image
    private Bitmap image;
    private ImageView sad;
    //counter
    private int counter = 0;
    private Context context;
    private Object BitmapDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Upi =findViewById(R.id.paytm);
        mAuth = FirebaseAuth.getInstance();
        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Upi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PaymentActivity.this,Upiactivity.class);
                startActivity(i);



            }
        });
    }
}
