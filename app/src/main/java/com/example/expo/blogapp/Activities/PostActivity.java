package com.example.expo.blogapp.Activities;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;

import android.os.Bundle;
import android.os.Handler;
import android.sax.StartElementListener;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.expo.blogapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostActivity extends AppCompatActivity {

    private Uri mImageUri = null;
    // private Uri post_image_uri;
    private ImageView post_photo;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RC_CROP1 = 100;
    private static final int CAMERA_REQUEST_CODE1 = 2;
    private static final int RC_CROP = 200;
    private static final int CROP_IMAGE_ACTIVITY_REQUEST_CODE1 = 100;
    private static final int RC_CROP2 = 300;
    private EditText post_title, post_details, post_phone, post_city, post_state, post_head, post_name, post_website, post_Amount;
    private ProgressDialog pdialog;
    private FirebaseAuth mAuth;
    public static final int PICK_IMAGE = 1;
    private final String SAMPLE_CROPPED_IMG_NAME = "SampleCroppping";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        mAuth = FirebaseAuth.getInstance();
     //   TextView header = findViewById(R.id.header);
        CircleImageView profileimage = findViewById(R.id.profileimage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        post_website = findViewById(R.id.link);
        post_phone = findViewById(R.id.phonenumber);
        //    post_image = findViewById(R.id.post_image);
        post_city = findViewById(R.id.city);
        post_photo = findViewById(R.id.organizationphoto);
        post_state = findViewById(R.id.state);
        post_head = findViewById(R.id.name);
        post_Amount = findViewById(R.id.required);
        post_name = findViewById(R.id.organization_name);

        //  post_name= findViewById(R.id.required);
        Button submit_post_btn = findViewById(R.id.submit_post_btn);

        submit_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allClear()) {
                    //  savePostDetails();
                    uploadPostImage(Html.fromHtml(post_name.getText().toString()).toString());
                }
            }
        });

        post_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* CropImage.activity()
                        .setAspectRatio(1,1)
                        .start(PostActivitycrop.this);*/
                startCropImageActivity(mImageUri, RC_CROP);
                // startCropImageActivity(mImageUri,RC_CROP);
            }
        });

       /* post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(PostActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PostActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        pickimage();
                    }
                } else {
                    pickimage();
                }
            }
        });*/
    }

    @Override
    public void onBackPressed() {

       finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
        }

        return true;
    }


    private void pickimage() {
        //  CropImage.startPickImageActivity(this);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    private boolean allClear() {
        if (mImageUri == null) {
            toast("Please select an Image");
            return false;
        } else if (TextUtils.isEmpty(post_Amount.getText().toString())) {
            post_website.setError("Amount cant be empty");
            return false;
        } else if (TextUtils.isEmpty(post_website.getText().toString())) {
            post_website.setError("Url cant be empty");
            return false;
        } else if (TextUtils.isEmpty(post_name.getText().toString())) {
            post_name.setError("Enter name");
            return false;
        } else if (TextUtils.isEmpty(post_head.getText().toString())) {
            post_head.setError("Head of the organization");
            return false;
        } else if (TextUtils.isEmpty(post_city.getText().toString())) {
            post_city.setError("city name");
            return false;
        } else if (TextUtils.isEmpty(post_state.getText().toString())) {
            post_state.setError("State name");
            return false;

        } else if (!isValidMobile(post_phone.getText().toString())) {
            post_phone.setError("Please enter a valid phone");
            post_phone.requestFocus();
        } else {
            return true;
        }
        return true;
    }

    private boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 6 && phone.length() <= 13;

        }
        return false;
    }


    private void savePostDetails(String url) {
        pdialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        pdialog.setMessage("Please wait...");
        pdialog.setIndeterminate(true);
        pdialog.setCanceledOnTouchOutside(false);
        pdialog.setCancelable(false);


        String website = post_website.getText().toString();
        String name = post_name.getText().toString();
        String Pending = "pending";

        //    String head = details.substring(0, Math.min(details.length(), 250));
        String user = mAuth.getCurrentUser().getUid();
        String city = post_city.getText().toString();
        String head = post_head.getText().toString();
        String state = post_state.getText().toString();
        String Amount = post_Amount.getText().toString();
        String amount = "0";
        String phoneno = post_phone.getText().toString();

        Map<String, Object> post = new HashMap<>();
        post.put("User", user);
        //  post.put("Views", 0);

        //  post.put("HospitalImage1", imageurl);
        post.put("website", website);
        post.put("Image", url);
        post.put("Name", name);
        post.put("Head", head);
        post.put("City", city);
        post.put("Required Amount", Amount);
        post.put("State", state);
        post.put("Pending", Pending);
        post.put("Phonenno", phoneno);
        post.put("Amount", amount);
        // post.put("Details", details);

        FirebaseFirestore.getInstance().collection("Organizations").document(state)
                .set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        pdialog.dismiss();
                        post_state.setText("");
                        post_city.setText("");
                        post_website.setText("");
                        post_name.setText("");
                        //post_image.setImageResource(R.color.com_facebook_device_auth_text);
                        startActivity(new Intent(PostActivity.this, MainActivity.class));
                        finish();
                        Toast.makeText(PostActivity.this, "Event  uploaded successfully :)", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void uploadPostImage(String postId) {
        pdialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        pdialog.setMessage("Please wait...");
        pdialog.setIndeterminate(true);
        pdialog.setCanceledOnTouchOutside(false);
        pdialog.setCancelable(false);
//        pdialog.show();
        if (mImageUri != null) {
            final StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("organization_images/" + postId + ".png" + System.currentTimeMillis() + "." + getFileExtension(mImageUri));
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

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void startCropImageActivity(Uri imageUri, int requestCode) {
        Intent vCropIntent = CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .getIntent(this);

        startActivityForResult(vCropIntent, requestCode);
    }

    private void toast(String message) {
        Toast.makeText(PostActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)  // resultCode: -1
        {
            if (requestCode == CAMERA_REQUEST_CODE) // requestCode: 288
            {
                Uri picUri = mImageUri;
                startCropImageActivity(picUri, RC_CROP);
                Toast.makeText(PostActivity.this, "Image 1 save",
                        Toast.LENGTH_SHORT).show();
            }

            if (requestCode == RC_CROP) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                //put image on first ImageView
                mImageUri = result.getUri();
                post_photo.setImageURI(mImageUri);
            }

        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            // if there is any error show it
            // Exception error = r.getError();
            Toast.makeText(this, "Something gone wrong!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivity.this, MainActivity.class));
            finish();
        }
    }
}
