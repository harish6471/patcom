package com.example.expo.blogapp;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by kamal_bunkar on 28-01-2019.
 */

public class PayUmoney extends AppCompatActivity {

    EditText phone, amount;
    Button btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_umoney);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Button btn = (Button) findViewById(R.id.start_transaction);
        phone = (EditText) findViewById(R.id.phone);
        amount = (EditText) findViewById(R.id.amountid);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PayUmoney.this, StartPaymentActivity.class);
                intent.putExtra("phone", phone.getText().toString());
                intent.putExtra("amount", amount.getText().toString());
                startActivity(intent);
            }
        });
    }
}
