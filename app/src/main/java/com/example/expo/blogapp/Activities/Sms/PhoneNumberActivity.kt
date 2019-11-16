package com.example.expo.blogapp.Activities.Sms

import android.content.Intent

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.expo.blogapp.R

 public class PhoneNumberActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number)
    }

    fun verifyPhoneClick(view: View) {
        // TODO: Send the phone number to the backend service
        startActivity(Intent(this, CodeVerificationActivity::class.java))
    }

}