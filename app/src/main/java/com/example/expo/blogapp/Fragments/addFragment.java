package com.example.expo.blogapp.Fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expo.blogapp.Activities.EditProfile;
import com.example.expo.blogapp.Activities.MainActivity;
import com.example.expo.blogapp.Activities.PostActivity;
import com.example.expo.blogapp.Activities.PostActivitycrop;
import com.example.expo.blogapp.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.grpc.stub.StreamObserver;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.FacebookSdk.getCacheDir;
import static com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;


public class addFragment extends Fragment {
    private Uri mImageUri;
    String miUrlOk = "";
    private StorageTask uploadTask;
    StorageReference storageRef;
    // private Uri post_image_uri;
    private ImageView post_image;
    private EditText post_title, post_details,post_phonenumber,post_upi;
    private ProgressDialog pdialog;
    private FirebaseAuth mAuth;
    public static final int PICK_IMAGE = 1;
    private final String SAMPLE_CROPPED_IMG_NAME = "SampleCroppping";

    ImageView close, image_added;
    TextView post;
    EditText description;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container, false);
        mAuth = FirebaseAuth.getInstance();
        TextView header = view.findViewById(R.id.header);
        CircleImageView profileimage = view.findViewById(R.id.profileimage);

     //   Toolbar toolbar = view.findViewById(R.id.toolbar);
        post_title = view.findViewById(R.id.post_title);
        post_details = view.findViewById(R.id.post_details);
        post_image = view.findViewById(R.id.post_image);

      post_phonenumber = view.findViewById(R.id.phonenumber);
      post_upi=view.findViewById(R.id.upi1);

       // post_details = view.findViewById(R.id.post_details);

        Button submit_post_btn = view.findViewById(R.id.submit_post_btn);

        submit_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allClear()) {
                    uploadPostImage(Html.fromHtml(post_title.getText().toString()).toString());
                }
            }
        });



        Typeface pacifico = Typeface.createFromAsset(((AppCompatActivity) getActivity()).getAssets(), "fonts/Pacifico.ttf");
//       header.setText("Post Blog");
      //  header.setTypeface(pacifico);
      //  profileimage.setVisibility(View.GONE);
     //  search.setVisibility(View.GONE);
       // ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
      //  ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
       // ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setAspectRatio(1,1)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(getActivity());
            }
        });

        return view;
    }

    private boolean allClear() {
        if (mImageUri == null) {
            toast("Please select an Image");
            return false;
        }else if (TextUtils.isEmpty(post_title.getText().toString())){
            post_title.setError("Title can't be Empty");
            return false;
        }else if (TextUtils.isEmpty(post_details.getText().toString())){
            post_details.setError("Detail can't be Empty");
            return false;
        }else if (TextUtils.isEmpty(post_details.getText().toString())){
            post_details.setError("Upi id can't be Empty");
            return false;
        }else if (TextUtils.isEmpty(post_details.getText().toString())){
            post_details.setError("Phone Number can't be Empty");
            return false;
        }else {
            return true;
        }
    }

   /* private void pickimage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,CROP_IMAGE_ACTIVITY_REQUEST_CODE );
    }*/
    private String getFileExtension(Uri uri){
        ContentResolver cR =getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



    private void uploadPostImage(String postId) {
        pdialog = new ProgressDialog(getActivity(), R.style.MyAlertDialogStyle);
        pdialog.setMessage("Please wait...");
        pdialog.setIndeterminate(true);
        pdialog.setCanceledOnTouchOutside(false);
        pdialog.setCancelable(false);
        pdialog.show();
        if (mImageUri!= null) {
            final StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("post_images/" + postId + ".png" + System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            UploadTask uploadTask = riversRef.putFile(mImageUri);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }


                    return riversRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        savePostDetails(downloadUri.toString());

                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
                 /*   @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            savePostDetails(task.getResult().getDownloadUrl().toString());
                        } else {
                            Toast.makeText(PostActivity.this, "Unable to upload Image ! " + task.getException(), Toast.LENGTH_SHORT).show();
                            pdialog.dismiss();
                        }
                    }
                });*/
        }
    }
    private void savePostDetails(String imageUrl) {
        String title = post_title.getText().toString();
        String details = post_details.getText().toString();
        String phonenumber = post_phonenumber.getText().toString();
       // int phonenumber=Integer.parseInt(phone);
        String upi = post_upi.getText().toString();
       // int upi=Integer.parseInt(upi1);

        //  int upi= Integer.parseInt(up.getText().toString());
        String desc = details.substring(0, Math.min(details.length(), 250));
        String user = mAuth.getCurrentUser().getUid();

        Map<String, Object> post = new HashMap<>();
        post.put("User", user);
        post.put("Views", 0);

        post.put("Image", imageUrl);
        post.put("Time", System.currentTimeMillis());
        post.put("Title", title);
        post.put("Desc", desc);
        post.put("Phonenumber", phonenumber);
        post.put("Upi", upi);
        post.put("Details", details);

        FirebaseFirestore.getInstance().collection("Posts").document(title)
                .set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pdialog.dismiss();
                        post_title.setText("");
                        post_details.setText("");
                       post_phonenumber.setText("");
                       post_upi.setText("");
                        post_image.setImageResource(R.color.com_facebook_device_auth_text);

                        startActivity(new Intent(getActivity(), MainActivity.class));
                       // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                      // getFragmentManager().beginTransaction().replace(R.id.home1, new HomeFragment1()).commitNowAllowingStateLoss();;
                        Toast.makeText(getActivity(), "Post uploaded successfully :)", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
                    }
                });
    }



    private void toast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
          mImageUri= result.getUri();

           post_image.setImageURI(mImageUri);
        } else {
            Toast.makeText(getActivity(), "Something gone wrong!", Toast.LENGTH_SHORT).show();
           // startActivity(new Intent(getActivity(), MainActivity.class));
          //  finish();
        }

            // uploadImage();
          //  uploadPostImage(Html.fromHtml(post_title.getText().toString()).toString());

           // uploadPostImage(postId);
          /*  if (data != null) {
                post_image_uri = data.getData();
                final Uri resultUri = UCrop.getOutput(data);
              //  startCrop(post_image_uri);

                post_image.setImageURI(post_image_uri);

            }
            else if(requestCode==UCrop.REQUEST_CROP&&resultCode==RESULT_OK){
                Uri imageUriResultCrop=UCrop.getOutput(data);

                if(imageUriResultCrop!=null){
                    post_image.setImageURI(imageUriResultCrop);
                }
            }else if (resultCode == UCrop.RESULT_ERROR) {
                final Throwable cropError = UCrop.getError(data);

                /* */

        }


    }
  /*  private void startCrop(@NonNull Uri uri){
        String destinationFileName=SAMPLE_CROPPED_IMG_NAME;
        destinationFileName+=".jpg";
        UCrop ucrop=UCrop.of(uri,Uri.fromFile(new File(getCacheDir(),destinationFileName)));
        ucrop.withAspectRatio(1,1);
        //ucrop.withAspectRatio(2,3);
        //ucrop.useSourceImageAspectRatio();
        //ucrop.withAspectRatio(16,9);
        ucrop.withMaxResultSize(450,450);
        ucrop.withOptions(getCropoptions());
        ucrop.start(getActivity());


    }
    private  UCrop.Options getCropoptions(){
        UCrop.Options options=new UCrop.Options();
        options.setCompressionQuality(70);
        // options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        // options.setCompressionFormat(Bitmap.CompressFormat.JPEG );

        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));

        options.setToolbarTitle("Edit");
        return  options;


    }
    public interface onBackPressed{
        void onBackPressed();
    }
*/
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) { 
            void onBackpressed();
        }



*/

