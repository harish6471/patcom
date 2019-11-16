package com.example.expo.blogapp.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.expo.blogapp.Activities.BlogDetails;
import com.example.expo.blogapp.Activities.Modal.Blog;
import com.example.expo.blogapp.Activities.Modal.Post;
import com.example.expo.blogapp.Activities.ProfileActivity;
import com.example.expo.blogapp.Fragments.NotificationFragment;
import com.example.expo.blogapp.Models.Notification;
import com.example.expo.blogapp.Models.User;
import com.example.expo.blogapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Notification> mNotification;
    String user;
    String title;

    public NotificationAdapter(Context context, List<Notification> notification){
        mContext = context;
        mNotification = notification;
    }

    @NonNull
    @Override
    public NotificationAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent, false);
        return new NotificationAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationAdapter.ImageViewHolder holder, final int position) {

        final Notification notification = mNotification.get(position);

        holder.text.setText(notification.getText());

        getUserInfo(holder.image_profile, holder.username, notification.getUserid());
      //  show(holder.image_profile, holder.username, notification.getUserid());


        getDeleteNotify(holder.image_profile,holder.username,notification.getUserid(),notification.getPostid());
     //   getDeleteNotify(holder.image_profile,holder.username,notification.getUserid(),notification.getPostid());

        if (notification.isIspost()) {
            holder.post_image.setVisibility(View.VISIBLE);
           // getPostImage(holder.post_image,notification.getUserid());
        } else {
            holder.post_image.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notification.isIspost()) {
                    SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                    editor.putString("postid", notification.getPostid());
                    editor.apply();

                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new NotificationFragment()).commit();
                } else {
                    SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                    editor.putString("profileid", notification.getUserid());
                    editor.apply();

                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new NotificationFragment()).commit();
                }
            }
        });



    }




    //
    @Override
    public int getItemCount() {
        return mNotification.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_profile, post_image,delete_notify;
        public TextView username, text;


        public ImageViewHolder(View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            post_image = itemView.findViewById(R.id.post_image);
            username = itemView.findViewById(R.id.username);
          //  user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            user = FirebaseAuth.getInstance().getCurrentUser().getUid();
            text = itemView.findViewById(R.id.comment);

        }
    }

    private void getUserInfo(final ImageView imageView, final TextView username, String user) {

        // final TextView username = findViewById(R.id.user_name);
        //  final CircleImageView userimage = view.findViewById(R.id.profile_image);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        DocumentReference documentReference = firebaseFirestore.collection("Users").document(user);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String name = (String) task.getResult().get("name");
                    String url = (String) task.getResult().get("url");
                    username.setText(name);
                    Glide.with(mContext)
                            .applyDefaultRequestOptions(new RequestOptions()
                                    .placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                                    .error(R.drawable.com_facebook_profile_picture_blank_square)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .load(url)
                            .into(imageView);
                } else {
                    username.setText("");
                    imageView.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                }
            }
        });
    }
        private void getDeleteNotify(ImageView delete_notify, TextView username,final String userid,final String post_title) {

           // delete_notify = findViewById(R.id.delete_not);
            //if (user.equals(userid)) {
             //  delete_notify .setVisibility(View.VISIBLE);
               delete_notify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseDatabase.getInstance().getReference("Posts")
                                .child(post_title).removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                           // deleteNotifications(id, .getUid());
                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid);
                                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                                        if (snapshot.child("posttitle").getValue().equals(post_title)){
                                                            snapshot.getRef().removeValue()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Toast.makeText(mContext, "Deleted!", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    }
                                });


                    }
                });
            



        }

   /* private void getPostImage(final ImageView post_image, String user ,String title){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        DocumentReference documentReference = firebaseFirestore.collection("Posts").document(title);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                  //  String name = (String) task.getResult().get("name");
                    String url = (String) task.getResult().get("Image");
                  //  username.setText(name);
                    Glide.with(mContext)
                            .load(url)
                            .into(post_image);
                }
            }
        });


     /*   DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Posts").child(postid);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                Glide.with(mContext).load(post.getPostimage()).into(post_image);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/





}
