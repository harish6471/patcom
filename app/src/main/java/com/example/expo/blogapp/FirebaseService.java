package com.example.expo.blogapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioAttributes;
import android.net.Uri;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;


import com.example.expo.blogapp.Activities.MainActivity;
import com.example.expo.blogapp.Activities.Transcation;
import com.example.expo.blogapp.Fragments.NotificationFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class FirebaseService extends FirebaseMessagingService {
    Bitmap bitmap;
    private Switch nbtn;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private SharedPreferences  sharedPref;
    @Override
    public void onNewToken(String s) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w( "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.e("My Token",token);
                    }
                });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        int type = getSharedPreferences("login_info", MODE_PRIVATE).getInt("usertype", -1);


        Map<String, String> data = remoteMessage.getData();
        String body = data.get("body");
        String title = data.get("title");
        String imageUrl = data.get("image");
        bitmap = getBitmapfromUrl(imageUrl);

      // sendNotification(body, title, bitmap);
        data.put("body", body);


        data.put("title", title);
        data.put("image" , imageUrl);



        FirebaseFirestore.getInstance().collection("Notifications").document(title).set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

//

                       // startActivity(new Intent(FirebaseService.this, MainActivity.class));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
                    }
                });








        if (user != null) {

            SharedPreferences sharedPreferences = getSharedPreferences("notification", MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            if (!sharedPreferences.contains("on")) {
                editor.putBoolean("on", true);
                editor.apply();
            }

            if (sharedPreferences.getBoolean("on", true)) {
                nbtn.setChecked(true);

                sendNotification(body, title, bitmap);
                // notification_text.setText("Notifications ON");
            } else {
                nbtn.setChecked(false);
                // notification_text.setText("Notifications OFF");
            }



        }
    }



    private void sendNotification(String body, String title, Bitmap image) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 101, intent, 0);

        NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            channel = new NotificationChannel("222", "my_channel", NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        getApplicationContext(), "222")
                        .setContentTitle(title)
                        .setAutoCancel(true)
                        .setLargeIcon(((BitmapDrawable)getDrawable(R.drawable.logo1)).getBitmap())
                       // .setLargeIcon(image)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image))
                        .setSmallIcon(R.drawable.logo1)
                        .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification))
                        .setContentText(body)
                        .setContentIntent(pi)
                ;

        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        nm.notify(101, builder.build());
    }

    private Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }

    }
}
