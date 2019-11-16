package com.example.expo.blogapp.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.chaos.view.PinView;
import com.example.expo.blogapp.Fragments.HomeFragment1;
import com.example.expo.blogapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.shuhart.stepview.StepView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;

public class PostActivitycrop extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE =1 ;
    private static final int RC_CROP1 =100 ;
    private static final int CAMERA_REQUEST_CODE1 =2 ;
    private static final int RC_CROP = 200;
    private static final int CROP_IMAGE_ACTIVITY_REQUEST_CODE1 =100 ;
    private static final int RC_CROP2 =300 ;

   private int currentStep=0;
   private  int prevs=0;
    LinearLayout layout1,layout2,layout3,layout4,layout5;
    StepView stepView;


    private Uri mImageUri=null;
    private Uri mImageUri1=null;
    private Uri mImageUri2=null;

    String miUrlOk = "";
    private StorageTask uploadTask;
    StorageReference storageRef;
   // private Uri post_image_uri;
    private ImageView post_image,hospital_image1,hospital_image2;
    private EditText post_title, post_details,post_phonenumber,post_upi,hospital_name,hospital_bill,amount_goal;
    private ProgressDialog pdialog;
    private FirebaseAuth mAuth;
    public static final int PICK_IMAGE = 1;
    private final String SAMPLE_CROPPED_IMG_NAME = "SampleCroppping";

    ImageView close, image_added;
    TextView post,pending;
    EditText description;
    String phoneNumber;
    private Button next;
    private  Fragment fragment;
    AlertDialog dialog_verifying,profile_dialog;

    private static String uniqueIdentifier = null;
    private static final String UNIQUE_ID = "UNIQUE_ID";
    private static final long ONE_HOUR_MILLI = 60*60*1000;

    private static final String TAG = "FirebasePhoneNumAuth";

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth firebaseAuth;

   // private String phoneNumber;
    private Button sendCodeButton;
    private Button verifyCodeButton;
    private Button signOutButton;
    private Button button2,button5;

    private EditText phoneNum;
    private PinView verifyCodeET;
    private TextView phonenumberText,resend;

    private String mVerificationId;
    String cancer;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private boolean doubleBackToExitPressedOnce;


    // private FirebaseAuth mAuth;

    @SuppressLint("SetTextI18n")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add);
        mAuth = FirebaseAuth.getInstance();
        TextView header = findViewById(R.id.header);
        CircleImageView profileimage = findViewById(R.id.profileimage);

        //   Toolbar toolbar = view.findViewById(R.id.toolbar);

        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout3 = (LinearLayout) findViewById(R.id.layout3);
        //layout4 = (LinearLayout) findViewById(R.id.layout4);
        layout5 = (LinearLayout) findViewById(R.id.layout5);
        post_title =findViewById(R.id.post_title);
        post_details = findViewById(R.id.post_details);
        post_image = findViewById(R.id.post_image);
      hospital_image1= findViewById(R.id.hospitalimage1);
        hospital_name= findViewById(R.id.hospital_name);
        amount_goal= findViewById(R.id.amountgoal);
        hospital_bill= findViewById(R.id.hospital_bill2);
        hospital_image2= findViewById(R.id.hospitalimage2);
        pending=findViewById(R.id.review);

        post_phonenumber = findViewById(R.id.phonenumber);
        //post_upi=findViewById(R.id.upi1);

        // post_details = view.findViewById(R.id.post_details);

       // Button submit_post_btn =findViewById(R.id.submit4);
        close = findViewById(R.id.close);
        //image_added = findViewById(R.id.image_added);
        post = findViewById(R.id.post);
        description = findViewById(R.id.description);
        next = (Button) findViewById(R.id.submit1);
        sendCodeButton = (Button) findViewById(R.id.submit3);
       // verifyCodeButton = (Button) findViewById(R.id.submit4);
        button2 = (Button) findViewById(R.id.submit2);
        button5 = (Button) findViewById(R.id.submit5);
        firebaseAuth = FirebaseAuth.getInstance();
        phoneNum = (EditText) findViewById(R.id.phonenumber);

        verifyCodeET = (PinView) findViewById(R.id.pinView);
        phonenumberText = (TextView) findViewById(R.id.phonenumberText);
        resend = (TextView) findViewById(R.id.resend_code);

       // List<String> sources=new ArrayList<>();


       /* sources.add("Details");
        sources.add("Enter Phone number");
        sources.add("Verification code");
        sources.add("Submit");*/


        Intent intent = getIntent();

        post_title.setText(intent.getExtras().getString("cancer"));


        stepView = findViewById(R.id.step_view);
        stepView.setStepsNumber(5);
        List<String> steps = new ArrayList<>();

        steps.add("Details");
        steps.add("Documents ");
        steps.add("Phonenumber");
       // steps.add("Verify");
        steps.add("submit");

        stepView.setSteps(steps);
      //  stepView.setSteps(sources);

        stepView.go(0, true);
        layout1.setVisibility(View.VISIBLE);

        storageRef = FirebaseStorage.getInstance().getReference("posts1");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  String phoneNumber = phoneNum.getText().toString();
                String posttitle1 = post_title.getText().toString();
                String postdes = post_details.getText().toString();
               // String postupi = post_upi.getText().toString();

                String hospitalname =hospital_name.getText().toString();

                if (TextUtils.isEmpty(posttitle1)) {
                    post_title.setError("Enter a title");
                   post_title.requestFocus();
                }
                if (TextUtils.isEmpty(postdes)) {
                    post_details.setError("write description");
                    post_details.requestFocus();
                }
                if (TextUtils.isEmpty(hospitalname)) {
                    hospital_name.setError("Enter a upi");
                   hospital_bill.requestFocus();
                }
                if (mImageUri==null) {
                  //  post_image.setError("Enter a Phone Number");
                    toast("Please select an Image");
                    post_image.requestFocus();
                }
                else {
                  //  uploadPostImage(Html.fromHtml(post_title.getText().toString()).toString());
                    if (currentStep < stepView.getStepCount() - 1) {
                        currentStep++;
                        stepView.go(currentStep, true);
                    } else {
                        stepView.done(true);
                    }
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                }
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  String phoneNumber = phoneNum.getText().toString();
//                phonenumberText.setText(phoneNumber);


                String hospitalbill= hospital_bill.getText().toString();

                String amountgoal =amount_goal.getText().toString();

                // phonenumberText.setText(phoneNumber);



                if (TextUtils.isEmpty(hospitalbill)) {
                   hospital_bill.setError("Hospital bill");
                   hospital_bill.requestFocus();
                }

                if (TextUtils.isEmpty(amountgoal)) {
                    hospital_bill.setError("Enter the current Amount");
                    hospital_bill.requestFocus();
                }


                if (mImageUri1==null) {
                    //  post_image.setError("Enter a Phone Number");
                    toast("Please select an Image");
                    hospital_image1.requestFocus();

                }
                if (mImageUri2==null) {
                    //  post_image.setError("Enter a Phone Number");
                    toast("Please select an Image");
                    hospital_image1.requestFocus();

                }

                else {
                    //  uploadPostImage(Html.fromHtml(post_title.getText().toString()).toString());
                    if (currentStep < stepView.getStepCount() - 1) {
                        currentStep++;
                        stepView.go(currentStep, true);
                    } else {
                        stepView.done(true);
                    }
                    layout2.setVisibility(View.GONE);
                    layout3.setVisibility(View.VISIBLE);
                }
            }
        });

        post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* CropImage.activity()
                        .setAspectRatio(1,1)
                        .start(PostActivitycrop.this);*/
                startCropImageActivity(mImageUri,RC_CROP);
               // startCropImageActivity(mImageUri,RC_CROP);
            }
        });
        hospital_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* CropImage.activity()
                        .setAspectRatio(1,1)
                        .start(PostActivitycrop.this);*/
               startCropImageActivity(mImageUri1,RC_CROP1);

            }
        });
       hospital_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* CropImage.activity(mImageUri2)
                        .setAspectRatio(1,1)
                        .start(PostActivitycrop.this);*/
                startCropImageActivity(mImageUri2,RC_CROP2);


            }
       });

        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNumber = phoneNum.getText().toString();
//                phonenumberText.setText(phoneNumber);

                if (TextUtils.isEmpty(phoneNumber)) {
                    phoneNum.setError("Enter a Phone Number");
                    phoneNum.requestFocus();
                } else if (!isValidMobile(phoneNumber)) {
                    phoneNum.setError("Please enter a valid phone");
                    phoneNum.requestFocus();
                } else {

                    if (currentStep < stepView.getStepCount() - 1) {
                        currentStep++;
                        stepView.go(currentStep, true);
                    } else {
                        stepView.done(true);
                    }
                    layout3.setVisibility(View.GONE);
                    layout5.setVisibility(View.VISIBLE);

                  /*  PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            PostActivitycrop.this,               // Activity (for callback binding)
                            mCallbacks);  */      // OnVerificationStateChangedCallbacks
                }
            }
        });
      /*  mCallbacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout= inflater.inflate(R.layout.processing_dialog,null);
                AlertDialog.Builder show = new AlertDialog.Builder(PostActivitycrop.this);

                show.setView(alertLayout);
                show.setCancelable(false);
                dialog_verifying = show.create();
                dialog_verifying.show();
                //signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }
            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // ...
            }
        };
       /* verifyCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String verificationCode = verifyCodeET.getText().toString();
                if(verificationCode.isEmpty()){
                    Toast.makeText(PostActivitycrop.this,"Enter verification code",Toast.LENGTH_SHORT).show();
                }else {

                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout= inflater.inflate(R.layout.processing_dialog,null);
                    AlertDialog.Builder show = new AlertDialog.Builder(PostActivitycrop.this);

                    show.setView(alertLayout);
                    show.setCancelable(false);
                    dialog_verifying = show.create();
                    dialog_verifying.show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                    signInWithPhoneAuthCredential(credential);

                }
            }
        });*/

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (currentStep < stepView.getStepCount() - 1) {
                    currentStep++;
                    stepView.go(currentStep, true);
                }
                if (allClear()) {
                    uploadPostImage(Html.fromHtml(post_title.getText().toString()).toString());
                    uploadHospitalImage(Html.fromHtml(post_title.getText().toString()).toString());
                    uploadHospitalBill(Html.fromHtml(hospital_bill.getText().toString()).toString());
                    //uploadHospitalImage1(Html.fromHtml(post_title.getText().toString()).toString());
                }else {
                    stepView.done(true);
                }
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout= inflater.inflate(R.layout.profile_create_dialog,null);
                AlertDialog.Builder show = new AlertDialog.Builder(PostActivitycrop.this);
                show.setView(alertLayout);
                show.setCancelable(false);
                profile_dialog = show.create();
                profile_dialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        profile_dialog.dismiss();
                        startActivity(new Intent(PostActivitycrop.this,MainActivity.class));
                        finish();
                    }
                },3000);
            }
        });

       /* close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PostActivitycrop.this, MainActivity.class));
                finish();
            }
        });*/


      /*submit_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // uploadImage_10();
                if (allClear()) {
                    uploadPostImage(Html.fromHtml(post_title.getText().toString()).toString());
                }
            }
        });*/




    }

    private void startCropImageActivity(Uri imageUri, int requestCode) {
        Intent vCropIntent = CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .getIntent(this);

        startActivityForResult(vCropIntent, requestCode);
    }


    private boolean allClear() {
     //   pos.setText(phoneNumber);
        String phoneNumber = post_phonenumber.getText().toString();
        if (mImageUri == null&&mImageUri1==null&&mImageUri2==null) {
           toast("Please select an Image");
            return false;
        }else if (TextUtils.isEmpty(post_title.getText().toString())){
            post_title.setError("Title can't be Empty");
            return false;
        }else if (TextUtils.isEmpty(post_details.getText().toString())){
            post_details.setError("Detail can't be Empty");
            return false;
        }else if (TextUtils.isEmpty(hospital_name.getText().toString())){
           hospital_name.setError("Hospital name can't be Empty");
            return false;
        }else if (!isValidMobile(post_phonenumber.getText().toString())) {
            post_phonenumber.setError("Please enter a valid phone");
            post_phonenumber.requestFocus();
            return false;
        }else {
            return true;
        }
    }
    private void uploadPostImage(String postId) {
        pdialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        pdialog.setMessage("Please wait...");
        pdialog.setIndeterminate(true);
        pdialog.setCanceledOnTouchOutside(false);
        pdialog.setCancelable(false);
//        pdialog.show();
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
    private void uploadHospitalImage(String postId) {
        pdialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        pdialog.setMessage("Please wait...");
        pdialog.setIndeterminate(true);
        pdialog.setCanceledOnTouchOutside(false);
        pdialog.setCancelable(false);
//        pdialog.show();
        if (mImageUri1!= null) {
            final StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("Hospital_images/" + postId + ".png" + System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            UploadTask uploadTask = riversRef.putFile(mImageUri1);
         //   UploadTask uploadTask1 = riversRef.putFile(mImageUri2);
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

                        saveHospitalDetails(downloadUri.toString());

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

    private void saveHospitalDetails(String imageurl) {

        String title = post_title.getText().toString();
        String hospitalname = hospital_name.getText().toString();
      //  String hospitalname = hospital_name.getText().toString();
        String hospitalbill = hospital_bill.getText().toString();

       // String amountgoal = amount_goal.getText().toString();
        // int phonenumber=Integer.parseInt(phone);
       // String upi = post_upi.getText().toString();
        // int upi=Integer.parseInt(upi1);

        //  int upi= Integer.parseInt(up.getText().toString());
       // String desc = details.substring(0, Math.min(details.length(), 250));
        String user = mAuth.getCurrentUser().getUid();

        Map<String, Object> post = new HashMap<>();


        post.put("HospitalImage1", imageurl);
     /// post.put("HospitalImage2", imageUrl);
        post.put("Title", title);


        post.put("hospitalname", hospitalname);
        post.put("hospitalbill", hospitalbill);


        FirebaseFirestore.getInstance().collection("Posts").document(title).collection("HospitalRelated").document(hospitalname)
                .set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    //    pdialog.dismiss();
                        post_title.setText("");
                        hospital_name.setText("");
                        hospital_bill.setText("");
                        post_details.setText("");
                        post_phonenumber.setText("");
                       // post_upi.setText("");
                        post_image.setImageResource(R.color.com_facebook_device_auth_text);
                        hospital_image1.setImageResource(R.color.com_facebook_device_auth_text);
                        hospital_image2.setImageResource(R.color.com_facebook_device_auth_text);

                        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        /*FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        getFragmentManager().beginTransaction().replace(R.id.home1, new HomeFragment1()).commitNowAllowingStateLoss();;*/


// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
                        startActivity(new Intent(PostActivitycrop.this, MainActivity.class));
                        finish();
                        Toast.makeText(PostActivitycrop.this, "Post uploaded successfully :)", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
                    }
                });
    }
   private void uploadHospitalBill(String hospitalbil) {
       pdialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
       pdialog.setMessage("Please wait...");
       pdialog.setIndeterminate(true);
       pdialog.setCanceledOnTouchOutside(false);
       pdialog.setCancelable(false);
//       pdialog.show();
       if (mImageUri2 != null) {
           final StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("Hospital_bills/" +hospitalbil + ".png" + System.currentTimeMillis() + "." + getFileExtension(mImageUri));
           UploadTask uploadTask = riversRef.putFile(mImageUri2);
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
                      //saveHospitalBills(downloadUri.toString());
                     //  saveHospitalBill(downloadUri.toString());

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





    private void savePostDetails(String url) {
        String title = post_title.getText().toString();
        final String pending="Pending";
        String details = post_details.getText().toString();
        String phonenumber = post_phonenumber.getText().toString();
        String hospitalname = hospital_name.getText().toString();
        String amountgoal = amount_goal.getText().toString();
        //  String hospitalname = hospital_name.getText().toString();
        String hospitalbill = hospital_bill.getText().toString();
        // int phonenumber=Integer.parseInt(phone);
     //   String upi = post_upi.getText().toString();
        // int upi=Integer.parseInt(upi1);

        //  int upi= Integer.parseInt(up.getText().toString());
        String desc = details.substring(0, Math.min(details.length(), 250));
        String user = mAuth.getCurrentUser().getUid();

        Map<String, Object> post = new HashMap<>();
        post.put("User", user);
        post.put("Views", 0);

      //  post.put("HospitalImage1", imageurl);
        post.put("Image", url);
        post.put("hospitalname", hospitalname);
        post.put("hospitalbill", hospitalbill);
        post.put("Time", System.currentTimeMillis());
        post.put("Title", title);
        post.put("Require Amount", amountgoal);

        post.put("Desc", desc);
        post.put("Pending", pending);
        post.put("Phonenumber", phonenumber);
       // post.put("Upi", upi);
        post.put("Details", details);

        FirebaseFirestore.getInstance().collection("Posts").document(title)
                .set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pdialog.dismiss();
                        post_title.setText("");
                        hospital_name.setText("");
                        hospital_bill.setText("");
                        post_details.setText("");
                        amount_goal.setText("");
                        post_phonenumber.setText("");
                     //   post_upi.setText("");
                        post_image.setImageResource(R.color.com_facebook_device_auth_text);
                        hospital_image1.setImageResource(R.color.com_facebook_device_auth_text);
                        hospital_image2.setImageResource(R.color.com_facebook_device_auth_text);

                       //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        /*FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        getFragmentManager().beginTransaction().replace(R.id.home1, new HomeFragment1()).commitNowAllowingStateLoss();;*/


// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
                        startActivity(new Intent(PostActivitycrop.this, MainActivity.class));
                        finish();
                        Toast.makeText(PostActivitycrop.this, "Post uploaded successfully :)", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
                    }
                });
    }


    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 6 && phone.length() <= 13;

        }
        return false;
    }



    private void resendVerificationCode(String toString, PhoneAuthProvider.ForceResendingToken mResendToken) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                mResendToken);             // ForceResendingToken from cal
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
      mAuth.signInWithCredential(credential)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Log.d(TAG, "signInWithCredential:success");
                           // FirebaseUser currentuser=task.getResult().getUser();
                            dialog_verifying.dismiss();
                            if (currentStep < stepView.getStepCount() - 1) {
                                currentStep++;
                                stepView.go(currentStep, true);
                            } else {
                                stepView.done(true);
                            }
                            layout1.setVisibility(View.GONE);
                            layout2.setVisibility(View.GONE);
                            layout3.setVisibility(View.GONE);
                           // layout4.setVisibility(View.GONE);
                            layout5.setVisibility(View.VISIBLE);
                            // ..
                            FirebaseUser currentUser = task.getResult().getUser();

                        }else {

                                dialog_verifying.dismiss();
                                Toast.makeText(PostActivitycrop.this,"Something wrong",Toast.LENGTH_SHORT).show();
                               // Log.w(TAG, "signInWithCredential:failure", task.getException());
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                }
                            }
                        }
                   });



        }
    private void toast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
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

        if (resultCode == RESULT_OK)  // resultCode: -1
        {
            if (requestCode == CAMERA_REQUEST_CODE) // requestCode: 288
            {
                Uri picUri =mImageUri;
                startCropImageActivity(picUri, RC_CROP);
                Toast.makeText(PostActivitycrop.this, "Image 1 save",
                        Toast.LENGTH_SHORT).show();
            }
            if (requestCode == CAMERA_REQUEST_CODE1) {
                Uri picUri = mImageUri1;
                startCropImageActivity(picUri, RC_CROP1);
                Toast.makeText(PostActivitycrop.this, "Image 2 save",
                        Toast.LENGTH_SHORT).show();
            }

            if (requestCode == RC_CROP) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                //put image on first ImageView
                mImageUri= result.getUri();
                post_image.setImageURI(mImageUri);
            }

            if (requestCode == RC_CROP1) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                mImageUri1 = result.getUri();
                hospital_image1.setImageURI(mImageUri1);
                //put image on second ImageView
            }
            if (requestCode == RC_CROP2) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                mImageUri2 = result.getUri();
                hospital_image2.setImageURI(mImageUri2);
                //put image on second ImageView
            }
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            // if there is any error show it
           // Exception error = r.getError();
            Toast.makeText(this, "Something gone wrong!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivitycrop.this, MainActivity.class));
            finish();
        }
    }




    @Override
    public void onBackPressed()
    {
        if (currentStep == 0) {
            Intent i=new Intent(PostActivitycrop.this,MainActivity.class);
            startActivity(i);
           // currentStep--;
         //   stepView.go(currentStep, true);
        } if (currentStep==1){
            stepView.go(0,true);
            layout2.setVisibility(View.GONE);
           layout1.setVisibility(View.VISIBLE);


        }if(currentStep==2){
        stepView.go(1,true);
        layout3.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);


    }if(currentStep==3){
        stepView.go(2,true);
        layout4.setVisibility(View.GONE);
        layout3.setVisibility(View.VISIBLE);


    }if(currentStep==4){
        stepView.go(2,true);
        layout5.setVisibility(View.GONE);
        layout3.setVisibility(View.VISIBLE);



    }else {
    //    stepView.done(true);
        stepView.done(false);
    }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

      //  layout1.setVisibility(View.GONE);
       // layout2.setVisibility(View.VISIBLE);
    }
        //super.onBackPressed();
        //Intent i=new Intent(PostActivitycrop.this,MainActivity.class);
        //startActivity(i);

}