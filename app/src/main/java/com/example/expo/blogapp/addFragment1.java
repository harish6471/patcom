package com.example.expo.blogapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expo.blogapp.Activities.MainActivity;
import com.example.expo.blogapp.Activities.PostActivitycrop;
import com.example.expo.blogapp.R;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImageView;


public class addFragment1 extends Fragment {
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
    private Context mContext;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public addFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_add_fragment1, container, false);

        mAuth = FirebaseAuth.getInstance();
        TextView header = view.findViewById(R.id.header);
        CircleImageView profileimage = view.findViewById(R.id.profileimage);

        //   Toolbar toolbar = view.findViewById(R.id.toolbar);
        post_title =view.findViewById(R.id.post_title);
        post_details = view.findViewById(R.id.post_details);
        post_image = view.findViewById(R.id.post_image);

        post_phonenumber = view.findViewById(R.id.phonenumber);
        post_upi=view.findViewById(R.id.upi1);

        // post_details = view.findViewById(R.id.post_details);

        Button submit_post_btn =view.findViewById(R.id.submit_post_btn);
        close = view.findViewById(R.id.close);
        //image_added = findViewById(R.id.image_added);
        post = view.findViewById(R.id.post);
        description = view.findViewById(R.id.description);

        storageRef = FirebaseStorage.getInstance().getReference("posts1");

        post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .start(getContext(),addFragment1.this);
                //  startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
               // startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

       /* close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PostActivitycrop.this, MainActivity.class));
                finish();
            }
        });*/

        submit_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // uploadImage_10();
                if (allClear()) {
                    uploadPostImage(Html.fromHtml(post_title.getText().toString()).toString());
                }
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
    private void uploadPostImage(String postId) {
        pdialog = new ProgressDialog(getApplicationContext(), R.style.MyAlertDialogStyle);
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

                        //  startActivity(new Intent(this, MainActivity.class));
                        // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        // getFragmentManager().beginTransaction().replace(R.id.home1, new HomeFragment1()).commitNowAllowingStateLoss();;
                        Toast.makeText(getApplicationContext(), "Post uploaded successfully :)", Toast.LENGTH_LONG).show();
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
    private String getFileExtension(Uri uri){
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

  /*  private void uploadImage_10(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Posting");
        pd.show();
        if (mImageUri != null){
            final StorageReference fileReference = storageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            uploadTask = fileReference.putFile(mImageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        miUrlOk = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                        String postid = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("postid", postid);
                        hashMap.put("postimage", miUrlOk);
                        hashMap.put("description", description.getText().toString());
                        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());

                        reference.child(postid).setValue(hashMap);

                        pd.dismiss();

                        startActivity(new Intent(PostActivitycrop.this, MainActivity.class));
                        finish();

                    } else {
                        Toast.makeText(PostActivitycrop.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostActivitycrop.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(PostActivitycrop.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
          //  startActivity(new Intent(PostActivitycrop.this, MainActivity.class));
           // finish();

    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}


