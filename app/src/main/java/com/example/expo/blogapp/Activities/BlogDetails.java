package  com.example.expo.blogapp.Activities;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.example.expo.blogapp.Activities.Helper.CommentViewHolder;
import com.example.expo.blogapp.Activities.Helper.TimeFormatter;
import com.example.expo.blogapp.Activities.Modal.Blog;
import com.example.expo.blogapp.Activities.Modal.Comment;
import com.example.expo.blogapp.Activities.ProfileActivity;
import com.example.expo.blogapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BlogDetails extends AppCompatActivity {

    private ImageView post_image,hospital_image1,hospital_image2, user_image, bookmark_btn, close_btn, like_btn, comment_btn;
    private ImageView share_btn,imageView;
    private TextView user_name, date, post_title, post_desc,phonenumber,upi, like_count, comment_count,hospitalbill1,hospitalname1;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private Bundle bundle;
    private TextView donate;
    //private FirestoreRecyclerAdapter<Comment, CommentViewHolder> adapter;
    String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);


        post_image = findViewById(R.id.post_image);

       hospital_image1 = findViewById(R.id.hospitalimage1);

       hospital_image2 = findViewById(R.id.hospitalimage2);
        user_image = findViewById(R.id.user_image);
        user_name = findViewById(R.id.user_name);
        user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        date = findViewById(R.id.date);
        post_title = findViewById(R.id.post_title);
        phonenumber = findViewById(R.id.phonenumber);
        hospitalbill1 = findViewById(R.id.hospital_bill);
        hospitalname1 = findViewById(R.id.hospital_name);
       upi = findViewById(R.id.upi1);
       donate = findViewById(R.id.donate);
        post_desc = findViewById(R.id.post_desc);
        bookmark_btn = findViewById(R.id.bookmark_btn);
        ImageView deletebtn = findViewById(R.id.delete_btn);


        close_btn = findViewById(R.id.close_btn);

        share_btn = findViewById(R.id.share_btn);
        String imageUrl="";
        String imageurl="";
        bundle = getIntent().getExtras();
        String ImageUrl = bundle.getString("ImageUrl");
       // String ImageUrl1 = bundle.getString("ImageUrl1");
       // String ImageUrl2 = bundle.getString("ImageUrl2");
        String User = bundle.getString("User");
        long Time = bundle.getLong("Time");
        String Title = bundle.getString("Title");
        String hospitalname = bundle.getString("Hospitalname");
        String hospitalbill = bundle.getString("Hospitalbill" +
                "");
        String Phonenumber = bundle.getString("Phonenumber");
        String Upi = bundle.getString("Upi");
        String Details = bundle.getString("Details");
        int views = bundle.getInt("Views");


        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        String user = mAuth.getCurrentUser().getUid();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlogDetails.super.onBackPressed();
            }
        });
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pTitle = post_title.getText().toString().trim();
                String pDescription = post_desc.getText().toString().trim();
                //shareTextOnly(pTitle,pDescription);
                //shareImageandText(pTitle,pDescription,bitmap);


              //  final ImageView post_image = view.findViewById(R.id.post_image);
                //BitmapDrawable bitmapDrawable = (BitmapDrawable).
              BitmapDrawable bitmapDrawable=(BitmapDrawable)post_image.getDrawable();
                if (bitmapDrawable == null) {
                   // shareTextOnly(post_title, post_desc);
                    shareTextOnly(pTitle,pDescription);
                } else {
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                   // shareTextOnly(pTitle,pDescription);
                    shareImageandText(pTitle,pDescription,bitmap);


                }

            }

        });
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(BlogDetails.this, Upiactivity.class);
                startActivity(i);
            }
        });










        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }



        setPostImage(ImageUrl);

       // setHospitalImag(ImageUrl);
        setUser(User);
        setTime(Time);
        post_title.setText(Title);
        phonenumber.setText("PhoneNumber:"+Phonenumber);
        hospitalname1.setText("HospitalName:"+hospitalname);
        hospitalbill1.setText("HospitalBill" +hospitalbill);
//        upi.setText("Upi id :"+"patcom@ybl");
        post_desc.setText(Details);
        setBookmark(Title, user);
        setViews(views, Title);
       // setLikes(user, Title);
        setHospitalImage( this,imageUrl,Title,getApplicationContext(),hospitalname);
        //setHospitalBill( this,imageUrl,Title,getApplicationContext(),hospitalname,hospitalbill);
        //setHospitalImage1( this,imageurl,Title,getApplicationContext(),hospitalbill);
    }

    private void setHospitalImage(Activity activity, String imageUrl,String title, final Context ctx, String hospitalname) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        final ImageView hospital_image2 = findViewById(R.id.hospitalimage1);
        DocumentReference documentReference = firebaseFirestore.collection("Posts").document(title).
                collection("HospitalRelated").document(hospitalname);
        documentReference.get().addOnCompleteListener(activity, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    //  String name = (String) task.getResult().get("name");
                    String imageUrl = (String) task.getResult().get("HospitalImage1");
                    //  username.setText(name);
                    Glide.with(ctx)
                            .applyDefaultRequestOptions(new RequestOptions()
                                    .placeholder(R.color.com_facebook_device_auth_text)
                                    .error(R.color.com_facebook_device_auth_text)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .load(imageUrl)
                            .into(hospital_image2);
                } else {
                    // username.setText("");
                    hospital_image2.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                }
            }
        });

    }

    private void setHospitalImage1(Activity activity, String imageurl,String title, final Context ctx, String hospitalbill) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        final ImageView hospital_image2 = findViewById(R.id.hospitalimage2);
        DocumentReference documentReference = firebaseFirestore.collection("Posts").document(title).
                collection("HospitalBills").document(hospitalbill);
        documentReference.get().addOnCompleteListener(activity, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    //  String name = (String) task.getResult().get("name");
                    String imageurl = (String) task.getResult().get("HospitalImage2");
                    //  username.setText(name);
                    Glide.with(ctx)
                            .applyDefaultRequestOptions(new RequestOptions()
                                    .placeholder(R.color.com_facebook_device_auth_text)
                                    .error(R.color.com_facebook_device_auth_text)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .load(imageurl)
                            .into(hospital_image2);
                } else {
                    // username.setText("");
                    hospital_image2.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                }
            }
        });
    }




        private void setTime(long time){
        TimeFormatter timeFormatter = new TimeFormatter();
        date.setText(timeFormatter.getTime(time));
    }

    private void setPostImage(String url){
        Glide.with(this)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.color.com_facebook_device_auth_text)
                        .error(R.color.com_facebook_device_auth_text)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .load(url)
                .into(post_image);
    }

    private void setUser(final String user_id){

        DocumentReference documentReference = firebaseFirestore.collection("Users").document(user_id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    String name = (String) task.getResult().get("name");
                    String url = (String) task.getResult().get("url");
                    user_name.setText(name);
                    Glide.with(getApplicationContext())
                            .applyDefaultRequestOptions(new RequestOptions()
                                    .placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                                    .error(R.drawable.com_facebook_profile_picture_blank_square)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .load(url)
                            .into(user_image);

                    user_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent profile = new Intent(BlogDetails.this, ProfileActivity.class);
                            profile.putExtra("UserId", user_id);
                            BlogDetails.this.startActivity(profile);
                        }
                    });

                    user_name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent profile = new Intent(BlogDetails.this, ProfileActivity.class);
                            profile.putExtra("UserId", user_id);
                            BlogDetails.this.startActivity(profile);
                        }
                    });
                }else {
                    user_name.setText("");
                    user_image.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                }
            }
        });

    }

    private void setBookmark(final String post_id, String user_id){

        final CollectionReference collection = FirebaseFirestore.getInstance().collection("Users").document(user_id)
                .collection("Bookmarks");

        collection.document(post_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (documentSnapshot.exists()){
                    bookmark_btn.setImageResource(R.drawable.bookmarked);
                }else {
                    bookmark_btn.setImageResource(R.drawable.bookmark);
                }
            }
        });


        bookmark_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collection.document(post_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                collection.document(post_id).delete();
                            } else {
                                Map<String, Object> bookmark = new HashMap<>();
                                bookmark.put("Post", post_id);
                                bookmark.put("Time",System.currentTimeMillis());
                                collection.document(post_id)
                                        .set(bookmark);

                                Toast.makeText(BlogDetails.this, "Post Bookmarked", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


        });

    }

    private void setViews(final int views, String post_id){
        final DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Posts").document(post_id);

        int totalviews = views+1;

        documentReference.update("Views", totalviews)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            Log.d("Views Error", "Error : " + task.getException());
                        }
                    }
                });


    }



    private Uri saveImageToShare(Bitmap bitmap) {
        File imagefolder=new File(getCacheDir(),"images");
        Uri uri=null;
        try{
            imagefolder.mkdirs();
            File file=new File(imagefolder,"shared_image.png");
            FileOutputStream stream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,90,stream);
            stream.flush();
            stream.close();
            uri= FileProvider.getUriForFile(this,"com.example.expo.blogapp.fileprovider",file);

        }catch(Exception e){
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

        }return uri;
    }

    private  void shareImageandText(String pTitle,String pDescription,Bitmap bitmap){
        String shareBody=pTitle+"\n"+pDescription;
        Uri uri=saveImageToShare(bitmap);
        Intent sIntent=new Intent(Intent.ACTION_SEND);
        sIntent.setType("text/plain");
        sIntent.putExtra(Intent.EXTRA_SUBJECT,"share");
        sIntent.putExtra(Intent.EXTRA_STREAM,uri);
        sIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(sIntent,"share via"));

    }

    private void shareTextOnly(String pTitle,String pDescription) {
        String shareBody=pTitle+"\n"+pDescription;
        Intent sIntent=new Intent(Intent.ACTION_SEND);
        sIntent.setType("text/plain");
        sIntent.putExtra(Intent.EXTRA_SUBJECT,"share");
        sIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(sIntent,"share via"));

    }


  /*  private void setLikes(final String user_id, String post_id) {
        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        final Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);


        final CollectionReference collection = FirebaseFirestore.getInstance().collection("Posts").document(post_id)
                .collection("Likes");


        final EventListener<DocumentSnapshot> checkifLiked = new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                if (documentSnapshot.exists()) {
                    like_btn.setImageResource(R.drawable.liked);
                } else {
                    like_btn.setImageResource(R.drawable.unliked);
                }

            }
        };

        final EventListener<QuerySnapshot> likeEvent = new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                String likes = String.valueOf(documentSnapshots.getDocuments().size());
                if (likes.equals("0")) {
                    like_count.setText("");
                } else {
                    like_count.setText(likes);
                }
            }
        };

        collection.addSnapshotListener(likeEvent);

        collection.document(user_id).addSnapshotListener(checkifLiked);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                like_btn.startAnimation(animation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


/*        like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                collection.document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            if (task.getResult().exists()){
                                collection.document(user_id).delete();

                            }else {
                                like_btn.startAnimation(animation);
                                final MediaPlayer sound = MediaPlayer.create(getApplicationContext(), R.raw.like_btn_click);
                                sound.start();
                                Map<String, Object> like = new HashMap<>();
                                like.put("UserId",user_id);
                                collection.document(user_id)
                                        .set(like)
                                        .addOnCompleteListener(
                                                new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        sound.stop();
                                                    }
                                                }
                                        );
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }*/



  //  public void setDeleteBtn(final String user_id, final String title) {
        //  ImageView delete_post = view.findViewById(R.id.delete_btn);
/*
        ImageView deletebtn = findViewById(R.id.delete_btn);
        // if (System.currentTimeMillis()-time <= 300000){
      //  if (user.equals(user_id)) {
            //deletebtn.setVisibility(View.VISIBLE);
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
            if (user.equals(user_id)) {
                deletebtn.setVisibility(View.VISIBLE);
            }FirebaseFirestore.getInstance().collection("Posts").document(title)
                            .delete();
                }
            });
      //  } else {
            deletebtn.setVisibility(View.GONE);
       // }


*/


}