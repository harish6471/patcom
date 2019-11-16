package com.example.expo.blogapp.Activities.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.expo.blogapp.Activities.BlogDetails;
import com.example.expo.blogapp.Activities.ProfileActivity;
import com.example.expo.blogapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.RegEx;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class NotificationViewHolder extends RecyclerView.ViewHolder {

    private View view;
    private FirebaseAuth mAuth;
    private String user_id;
    // private TextView view_count;
    //  private ImageView views_icon;
    //  private int views = 0;
    private String details = "";
    private String user;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        mAuth = FirebaseAuth.getInstance();
        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = FirebaseAuth.getInstance().getCurrentUser().getUid();

    }

    public void setImage(final Activity activity, String url, final Context ctx,String title) {
        final ImageView post_image = view.findViewById(R.id.post_image);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection("Notifications").document(title);
        documentReference.get().addOnCompleteListener(activity, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String url = (String) task.getResult().get("image");
                   // username.setText(name);
                    Glide.with(ctx)
                            .applyDefaultRequestOptions(new RequestOptions()
                                    .placeholder(R.color.com_facebook_device_auth_text)
                                    .error(R.color.com_facebook_device_auth_text)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))

                            .load(url)
                            .into(post_image);
                } else {

                }
            }
        });


    }





   /* public void setView(String post_id){
       // view_count.setText(String.valueOf(views));
        DocumentReference post = FirebaseFirestore.getInstance().collection("Posts").document(post_id);
        post.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    details = task.getResult().get("Details").toString();
                    //views = Integer.parseInt(String.valueOf(task.getResult().get("Views")));
                   // view_count.setText(String.valueOf(views));
                }else {
                    //view_count.setText(String.valueOf(views));
                }
            }
        });*/





    public void setTitle(Activity activity,String title,final Context ctx) {
        final TextView post_title1 = view.findViewById(R.id.post_title);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        /*Glide.with(ctx)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.color.com_facebook_device_auth_text)
                        .error(R.color.com_facebook_device_auth_text)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .load(url)
                .into(post_image);*/
        DocumentReference documentReference = firebaseFirestore.collection("Notifications").document(title);
        documentReference.get().addOnCompleteListener(activity, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String name1 = (String) task.getResult().get("title");
                    post_title1.setText(name1);
                    // username.setText(name);

                } else {

                }
            }
        });
        //post_desc.setText("BookMarked your photo");
        //post_title.setText(title);
    }

    public void setDeleteBtn(final String user_id, final String title) {
        ImageView delete_post = view.findViewById(R.id.delete_btn);


        if (user.equals(user_id)) {
            delete_post.setVisibility(View.VISIBLE);
            delete_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseFirestore.getInstance().collection("Notifications" ).document(title)
                            .delete();
                }
            });
        } else {
            delete_post.setVisibility(View.GONE);
        }
    }

    public void setBody(Activity activity,String title,final Context ctx) {
      final TextView post_body1= view.findViewById(R.id.post_body);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        /*Glide.with(ctx)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.color.com_facebook_device_auth_text)
                        .error(R.color.com_facebook_device_auth_text)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .load(url)
                .into(post_image);*/
        DocumentReference documentReference = firebaseFirestore.collection("Notifications").document(title);
        documentReference.get().addOnCompleteListener(activity, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String name1 = (String) task.getResult().get("body");
                     post_body1.setText(name1);
                } else {

                }
            }
        });
        //post_desc.setText("BookMarked your photo");
    }
}



