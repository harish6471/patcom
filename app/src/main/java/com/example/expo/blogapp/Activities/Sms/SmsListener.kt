package com.example.expo.blogapp.Activities.Sms

interface SmsListener {
    fun onSuccess(code: String)
    fun onError()
}