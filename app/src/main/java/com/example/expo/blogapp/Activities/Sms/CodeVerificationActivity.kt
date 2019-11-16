package com.example.expo.blogapp.Activities.Sms

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.expo.blogapp.R
import com.google.android.gms.auth.api.phone.SmsRetriever


class CodeVerificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      //  val etCode=
        setContentView(R.layout.activity_code_verification)

        startSmsRetriever()

    }

    private fun startSmsRetriever() {
        val client = SmsRetriever.getClient(this)

        val task = client.startSmsRetriever()

        task.addOnSuccessListener { _ ->
            Log.d("CodeActivity", "Sms listener started!")
            listenSms()
        }

        task.addOnFailureListener { e ->
            Log.e("CodeActivity", "Failed to start sms retriever: ${e.message}")
        }

    }

    private fun listenSms() {
      SMSBroadcastReceiver.bindListener(object : SmsListener {
            override fun onSuccess(code: String) {

                val etCode=findViewById<EditText>(R.id.etCode);
                etCode.setText(code)
                // TODO: Send the one-time code to the server
            }

            override fun onError() {
                // TODO: Display error message
            }
        })
    }
}