package com.example.expo.blogapp.Activities.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.expo.blogapp.Activities.BlogDetails;
import com.example.expo.blogapp.Activities.FullScreenActivity;
import com.example.expo.blogapp.Activities.PaymentActivity;
import com.example.expo.blogapp.Activities.ProfileActivity;
import com.example.expo.blogapp.Activities.Upiactivity;
import com.example.expo.blogapp.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.FacebookSdk.publishInstallAsync;

public class OrganizationsViewHolder extends RecyclerView.ViewHolder {

    View view;
    FirebaseAuth mAuth;
    String user_id;
    TextView view_count;
    TextView head1;
    TextView head2;
    TextView head3;
    TextView head4,head5;
    ImageView views_icon;
    String user;
    private ShareButton shareButton;
    //image
    private Bitmap image;
    private ImageView sad, more;
    //counter
    private int counter = 0;
    //    private Context context;
    private Object BitmapDrawable;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private static String TAG = OrganizationsViewHolder.class.getName();

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();
            if (ShareDialog.canShow(SharePhotoContent.class)) {
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content);
            }
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };


    public OrganizationsViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        mAuth = FirebaseAuth.getInstance();
        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        head1 = view.findViewById(R.id.phonenumber);
        head3 = view.findViewById(R.id.hospital_name);
        head4 = view.findViewById(R.id.hospital_bill);
       // head5 = view.findViewById(R.id.review);

        // final ImageView bitmapImageView = view.findViewById(R.id.bitmapImageView);
       // sad = (ImageView) view.findViewById(R.id.sadimage);
   //     more = (ImageView) view.findViewById(R.id.more);
      //  head2 = view.findViewById(R.id.upi1);

        //  delete_notify=view.findViewById(R.id.delete_not);
        shareButton = itemView.findViewById(R.id.fb_share_button);

        FacebookSdk.sdkInitialize(getApplicationContext());




//        Button donate =view.findViewById(R.id.donate);
        //  setShare(view);
        //  this.context=context;

  /*  private void postPicture() {
        if(counter == 0) {
            //save the screenshot
            View rootView = view.findViewById(android.R.id.content).getRootView();
            rootView.setDrawingCacheEnabled(true);
            // creates immutable clone of image
            image = Bitmap.createBitmap(rootView.getDrawingCache());
            // destroy
            rootView.destroyDrawingCache();

            //share dialog
            AlertDialog.Builder shareDialog = new AlertDialog.Builder(this);
            shareDialog.setTitle("Share Screen Shot");
            shareDialog.setMessage("Share image to Facebook?");
            shareDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //share the image to Facebook
                    SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
                    SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
                    shareButton.setShareContent(content);
                    counter = 1;
                    shareButton.performClick();
                }
            });
            shareDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            shareDialog.show();
        }
        else {
            counter = 0;
            shareButton.setShareContent(null);
        }*/
    }














    public void setImage(Activity activity, String url, final Context ctx, String title) {
        final ImageView post_image = view.findViewById(R.id.post_image);



        Glide.with(ctx)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.color.com_facebook_device_auth_text)
                        .error(R.color.com_facebook_device_auth_text)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .load(url)
                .into(post_image);


        post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (view_count.getVisibility() == View.VISIBLE) {
                    post_image.setColorFilter(null);
                    view_count.setVisibility(View.INVISIBLE);
                    views_icon.setVisibility(View.INVISIBLE);
                } else {
                    post_image.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
                    view_count.setVisibility(View.VISIBLE);
                    views_icon.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    public void setUser(Activity activity, final String user, final Context ctx) {
        final TextView username = view.findViewById(R.id.user_name);
        final CircleImageView userimage = view.findViewById(R.id.profile_image);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(ctx, ProfileActivity.class);
                profile.putExtra("UserId", user);
                ctx.startActivity(profile);
            }
        });

        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(ctx, ProfileActivity.class);
                profile.putExtra("UserId", user);
                //ctx.startActivity(profile);
            }
        });

        DocumentReference documentReference = firebaseFirestore.collection("Users").document(user);
        documentReference.get().addOnCompleteListener(activity, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String name = (String) task.getResult().get("name");
                    String url = (String) task.getResult().get("url");
                    username.setText(name);
                    Glide.with(ctx)
                            .applyDefaultRequestOptions(new RequestOptions()
                                    .placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                                    .error(R.drawable.com_facebook_profile_picture_blank_square)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .load(url)
                            .into(userimage);
                } else {
                    username.setText("");
                    userimage.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                }
            }
        });

    }

    public void setRevieww(Activity activity, final String title, final Context ctx,final String state,final String city) {

      final TextView head5 = view.findViewById(R.id.review);

     //   Button donate = view.findViewById(R.id.donate);
      //  final TextView username = view.findViewById(R.id.user_name);
       // final CircleImageView userimage = view.findViewById(R.id.profile_image);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();





        DocumentReference documentReference = firebaseFirestore.collection("Organizations").document(state).collection("Part of").document(city);
        documentReference.get().addOnCompleteListener(activity, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String review = (String) task.getResult().get("Pending");
                  //  String url = (String) task.getResult().get("url");
                    head5.setText(review);


                } else {
                    head5.setText("");
                  //  userimage.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                }
            }
        });

    }


      //  donate.setVisibility(View.GONE);
        // .orderBy("Pending", Query.Direction.DESCENDING)






        // .orderBy("Pending", Query.Direction.DESCENDING)




    public void setAmount(Activity activity, final String state, final Context ctx) {

        final TextView amount1 = view.findViewById(R.id.compliance_percentage);
        final TextView amount2 = view.findViewById(R.id.compliance_total_count);
        //  final TextView username = view.findViewById(R.id.user_name);
        // final CircleImageView userimage = view.findViewById(R.id.profile_image);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();



        //String id = firebaseFirestore.collection("Amount").document().getId();

        DocumentReference documentReference = firebaseFirestore.collection("Organizations").document(state);

        documentReference.get().addOnCompleteListener(activity, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String amount11 = (String) task.getResult().get("Amount");
                    String amount21 = (String) task.getResult().get("Required Amount");
                    //  String url = (String) task.getResult().get("url");
                    amount1.setText(amount11);
                    amount2.setText(amount21);
                    Double a= Double.valueOf(amount11);
                    Double b=Double.valueOf(amount21);
                   int c = (int) ((a/b) * 100);
                    ProgressBar progress = (ProgressBar)view. findViewById(R.id.circle_progress_bar);
                    progress.setProgress((c));







                } else {
                   amount1.setText("");
                   amount2.setText("");
                    //  userimage.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                }
            }
        });

    }

    public void setAmountgoal(Activity activity,final String state,  final Context ctx) {

        final TextView amount2 = view.findViewById(R.id.compliance_total_count);
        //  final TextView username = view.findViewById(R.id.user_name);
        // final CircleImageView userimage = view.findViewById(R.id.profile_image);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();





        DocumentReference documentReference = firebaseFirestore.collection("Organizations").document(state);
        documentReference.get().addOnCompleteListener(activity, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String amount1 = (String) task.getResult().get("Require Amount");
                    //  String url = (String) task.getResult().get("url");
                    amount2.setText(amount1);

                } else {
                    amount2.setText("");
                    //  userimage.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                }
            }
        });

    }




    public void setDate(long time) {
        TextView post_date = view.findViewById(R.id.date);
        TimeFormatter timeFormatter = new TimeFormatter();
        post_date.setText(timeFormatter.getTime(time));
    }

    public void setWebsite(String url)  {
        TextView head = view.findViewById(R.id.post_title);

        String urlString = "https://" + url ;

     //   String text = "Donate By clicking this <a href='https://wwww.google.com'></a>";
     //   check.setText(Html.fromHtml(text));
      //  String hyperlinkText = "<a href=\""+urlString  + "</a>";
        // = Uri.parse(urlString);
        //
        // URL  myURL = new URL(urlString);
        head.setText(urlString);
        head.setTextColor(Color.RED);
    }

       // (myURL);}









   /* public void setUpi(String upi) {
        //  head2.setText(Integer.valueOf(upi));
        head2.setText("UPI Id:" + upi);
    }*/



    private FacebookCallback<Sharer.Result> callback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onSuccess(Sharer.Result result) {
            Log.v(TAG, "Successfully posted");
            // Write some code to do some operations when you shared content successfully.
        }

        @Override
        public void onCancel() {
            Log.v(TAG, "Sharing cancelled");
            // Write some code to do some operations when you cancel sharing content.
        }

        @Override
        public void onError(FacebookException error) {
            Log.v(TAG, error.getMessage());
            // Write some code to do some operations when some error occurs while sharing content.
        }
    };

   /* public void setShareButton(final String url, final Context ctx) {

        // final ShareButton shareButton = itemView.findViewById(R.id.fb_share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.get()
                        .load(url)
                        .into(target);
            }
        });
    }*/


    public void setBookmark(final Activity activity, final String state, final long post_time, final CoordinatorLayout coordinatorLayout, final String user_id) {
        final ImageView bookmark_btn = view.findViewById(R.id.bookmark_btn);


            final CollectionReference collection = FirebaseFirestore.getInstance().collection("Users").document(mAuth.getCurrentUser().getUid())
                    .collection("Bookmarks");


            collection.document(state).addSnapshotListener(activity, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                    if (documentSnapshot.exists()) {
                        bookmark_btn.setImageResource(R.drawable.bookmarked);
                    } else {
                        bookmark_btn.setImageResource(R.drawable.bookmark);
                    }
                }
            });
            if (user.equals(user_id)) {
                bookmark_btn.setVisibility(View.GONE);
                bookmark_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        collection.document(state).get().addOnCompleteListener(activity, new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        collection.document(state).delete();
                                    } else {
                                        Map<String, Object> bookmark = new HashMap<>();
                                     //   bookmark.put("Title", post_title);
                                      //  bookmark.put("Time", post_time);
                                        bookmark.put("text", "bookmarked your post");
                                        //  bookmark.put("Desc", post_desc);
                                   //     bookmark.put("Image1",pr)
                                       // bookmark.put("User", user);
                                        bookmark.put("BookmarkTime", System.currentTimeMillis());
                                        collection.document(state)
                                                .set(bookmark);

                                        Snackbar snackbar = Snackbar.make(coordinatorLayout,
                                                "Event  Bookmarked", Snackbar.LENGTH_SHORT);
                                        snackbar.show();
                                        //addNotification(Blog.getUser(), Blog.getImage());
                                        // getNotification(user_id, post_title, post_img);
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }

                });


        }else {
           bookmark_btn.setVisibility(View.VISIBLE);
            bookmark_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    collection.document(state).get().addOnCompleteListener(activity, new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    collection.document(state).delete();
                                } else {
                                    Map<String, Object> bookmark = new HashMap<>();
                                    bookmark.put("State", state);
                                    bookmark.put("Time", post_time);
                                    bookmark.put("text", "bookmarked your event");
                                    //  bookmark.put("Desc", post_desc);
                                    bookmark.put("User", user);
                                    bookmark.put("BookmarkTime", System.currentTimeMillis());
                                    collection.document(state)
                                            .set(bookmark);

                                    Snackbar snackbar = Snackbar.make(coordinatorLayout,
                                            "Event Bookmarked", Snackbar.LENGTH_SHORT);
                                    snackbar.show();
                                    //addNotification(Blog.getUser(), Blog.getImage());
                                    // getNotification(user_id, post_title, post_img);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            });

        }

    }

    /*public void setMore(final String user_id, final Context ctx) {
        //ImageView more = view.findViewById(R.id.more);


            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(ctx,view);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.edit:
                                    Toast.makeText(ctx, "Reported clicked!", Toast.LENGTH_SHORT).show();
                                    //editPost(post.getPostid());
                                    return true;
                                case R.id.report:
                                    Toast.makeText(ctx, "Reported clicked!", Toast.LENGTH_SHORT).show();
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popupMenu.inflate(R.menu.menu_main1);
                    popupMenu.show();
                }
            });*/


    public void setDeleteBtn(final String user_id, final String state) {
        ImageView delete_post = view.findViewById(R.id.delete_btn);


        if (user.equals(user_id)) {
            delete_post.setVisibility(View.VISIBLE);
            delete_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseFirestore.getInstance().collection("Organizations").document(state)
                            .delete();
                }
            });
        } else {
            delete_post.setVisibility(View.GONE);
        }
    }

    public void getNotification(final String userid,final String post_title,String post_img) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", mAuth.getUid());
        hashMap.put("text", "bookmarked your post1111");
        hashMap.put("posttitle", post_title);
        hashMap.put("ispost", true);
       hashMap.put("Image", post_img);

        reference.push().setValue(hashMap);
    }







  /*private void shareImageandText(String title,String description,Bitmap bitmap) {
  String shareBody=title+"\n"+description;
  Uri uri=saveImageToShare(bitmap);
  Intent sIntent=new Intent(Intent.ACTION_SEND);
  sIntent.setType("text/plain");
  sIntent.putExtra(Intent.EXTRA_SUBJECT,"share");
  sIntent.putExtra(Intent.EXTRA_STREAM,uri);
  sIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
  context.startActivity(Intent.createChooser(sIntent,"share via"));

}

private Uri saveImageToShare(String title,String description,Bitmap bitmap) {
  File imagefolder=new File(context.getCacheDir(),"images");
  Uri uri=null;
  try{
      imagefolder.mkdirs();
      File file=new File(imagefolder,"shared_image.png");
      FileOutputStream stream=new FileOutputStream(file);
      bitmap.compress(Bitmap.CompressFormat.PNG,90,stream);
      stream.flush();
      stream.close();
      uri= FileProvider.getUriForFile(context,"com.example.expo.blogapp.fileprovider",file);

  }catch(Exception e){
      Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).();

  }
}

    private void shareTextOnly(String title, String description) {
        String shareBody = title + "\n" + description;
        Intent sIntent = new Intent(Intent.ACTION_SEND);
        sIntent.setType("text/plain");
        sIntent.putExtra(Intent.EXTRA_SUBJECT, "share");
        sIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sIntent, "share via"));

    }*/


    public void showPostDetails(String imgurl, String user, long time, final String title, String details, int views, String phonenumber, String upi, final Activity activity,String hospitalname,String hospitalbill) {

        TextView post_title = view.findViewById(R.id.post_title);
        TextView post_desc = view.findViewById(R.id.post_desc);

       // ImageView donate = view.findViewById(R.id.donate);
      //  ImageView delete_notify = view.findViewById(R.id.delete_not);

        TextView head1 = view.findViewById(R.id.phonenumber);
      //  TextView head2 = view.findViewById(R.id.upi1);

        TextView head3 = view.findViewById(R.id.hospital_bill);
        TextView head4 = view.findViewById(R.id.hospital_name);


        final Intent post = new Intent(activity, BlogDetails.class);
        final Intent post1 = new Intent(activity, Upiactivity.class);
        post.putExtra("ImageUrl", imgurl);
      //  post.putExtra("ImageUrl1", imgurl);
      //  post.putExtra("ImageUrl2", imgurl);
        post.putExtra("Time", time);
        post.putExtra("User", user);
        post.putExtra("Title", title);
        post.putExtra("Details", details);
        post.putExtra("Phonenumber", phonenumber);
      //  post.putExtra("Upi", upi);
        post.putExtra("Hospitalname",hospitalname);
        post.putExtra("HospitalBill", hospitalbill);
        post.putExtra("Views", views);
        post1.putExtra("User1", user);
        // post1.putExtra("UserId1", user);
        // post1.putExtra("User1",user);
        //post1.putExtra("Upi1", upi);
        post1.putExtra("Phonenumber1", phonenumber);
        post1.putExtra("Hospitalname1", hospitalname);

        post1.putExtra("HospitalBill1", hospitalbill);

        post1.putExtra("Title", title);



        post_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(post);
            }
        });
        head1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(post);
            }
        });
       /* head2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(post);
            }
        });*/

        post_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(post);
            }
        });




      /*  if (user.equals(user_id)) {
            sad.setVisibility(View.GONE);


        } else {
            sad.setVisibility(View.VISIBLE);
            sad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  final Intent post1 = new Intent(activity, PaymentActivity.class);
                    // activity.startActivity(post1);
                    animate();
                }
            });
        }*/
    }

    private void deleteNotifications(final String user_id,final String post_title) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(user_id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.child("posttitle").getValue().equals(post_title)){
                        snapshot.getRef().removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();
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




    protected int getLayoutId() {
        return 0;
    }



}
