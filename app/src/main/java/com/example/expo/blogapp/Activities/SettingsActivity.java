package com.example.expo.blogapp.Activities;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.expo.blogapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
	private Toolbar toolbar;

	private TextView title;
	private ImageView back_btn,photoadd,editname,editemail,editphoneno;
	private CircleImageView profile_image;
	private CardView name, email, password, notification,phoneno;
	private TextView profile_name, profile_email, profile_password, notification_text, version,profile_phoneno;
	// private Button logout_btn;
	private Switch nbtn;
	private FirebaseAuth mAuth;
	private FirebaseUser user;
	private Uri photo_url;
	private String username;
	private String useremail;
	private String userphoneno;
	TextView save, tv_change;
	FirebaseUser firebaseUser;
	StorageReference storageRef;
	private StorageTask uploadTask;
	private Uri mImageUri;

	private UserInfo provider;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);


		//Finding view By ID
		toolbar = findViewById(R.id.toolbar);
		title = findViewById(R.id.title);
		back_btn = findViewById(R.id.back_btn);

		profile_image = findViewById(R.id.profile_image);
		name = findViewById(R.id.name);
		email = findViewById(R.id.email);

		//   password = findViewById(R.id.password);

		notification = findViewById(R.id.notification);
		profile_name = findViewById(R.id.profile_name);
		profile_email = findViewById(R.id.profile_email);
		// editphoneno = findViewById(R.id.editphoneno);
		//editname = findViewById(R.id.editname);
		// editemail = findViewById(R.id.editemail);
		//  profile_password = findViewById(R.id.profile_password);
		notification_text = findViewById(R.id.notification_text);
		// version = findViewById(R.id.version);
		//   logout_btn = findViewById(R.id.logout_btn);
		nbtn = findViewById(R.id.nbtn);
		firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
		storageRef = FirebaseStorage.getInstance().getReference("uploads");
		// email.setFocusable(false);


		//email.setFocusableInTouchMode(true);
/*        editemail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //  email.setFocusableInTouchMode(true);
              //  profile_email.setFocusable(true);
              //  profile_email.setEnabled(true);
              //  profile_email.setClickable(true);
              //  profile_email.setFocusableInTouchMode(true);
            }
        });*/




		//Changing the Typefaces
		Typeface extravaganzza = Typeface.createFromAsset(getAssets(), "fonts/extravaganzza.ttf");
		title.setTypeface(extravaganzza);
		profile_name.setTypeface(extravaganzza);
		profile_email.setTypeface(extravaganzza);
//        profile_password.setTypeface(extravaganzza);
		notification_text.setTypeface(extravaganzza);
		//  version.setTypeface(extravaganzza);
		//    logout_btn.setTypeface(extravaganzza);


      /*  save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(fullname.getText().toString(),
                        username.getText().toString(),
                        bio.getText().toString());
            }
        });*/

		profile_image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				CropImage.activity()
						.setAspectRatio(1,1)
						.setCropShape(CropImageView.CropShape.OVAL)
						.start( SettingsActivity.this);
			}
		});
		mAuth = FirebaseAuth.getInstance();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		}
		user = mAuth.getCurrentUser();
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("");
		back_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SettingsActivity.super.onBackPressed();
			}
		});

		//Setting profile information
		getProfileDetails();


	}

	private void getProfileDetails(){
		if (user != null){

			SharedPreferences sharedPreferences = getSharedPreferences("notification", MODE_PRIVATE);
			final SharedPreferences.Editor editor = sharedPreferences.edit();
			if (!sharedPreferences.contains("on")){
				editor.putBoolean("on", true);
				editor.apply();
			}

			if (sharedPreferences.getBoolean("on", true)){
				nbtn.setChecked(true);
				notification_text.setText("Notifications ON");
			}else {
				nbtn.setChecked(false);
				notification_text.setText("Notifications OFF");
			}

			nbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked){
						editor.putBoolean("on", true);
						notification_text.setText("Notifications ON");
						editor.apply();
					}else {
						editor.putBoolean("on", false);
						notification_text.setText("Notifications OFF");
						editor.apply();
					}
				}
			});


			photo_url = user.getPhotoUrl();
			username = user.getDisplayName();
			useremail = user.getEmail();
			//  userphoneno=user.getPhoneNumber();
			// name=user.getDisplayName();
			provider = user.getProviderData().get(0);


			profile_name.setText(username);

			profile_email.setText(useremail);
			//pro
			// profile_phoneno.setText(userphoneno);
			setProfileImage(photo_url);

           /* if (emailLogin()){
                password.setVisibility(View.VISIBLE);
            }else {
                password.setVisibility(View.GONE);
            }*/

		}
	}
	private void uploadImage(){
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("Uploading");
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
						String miUrlOk = downloadUri.toString();

						DatabaseReference reference = FirebaseDatabase.getInstance().getReference("").child(firebaseUser.getUid());
						HashMap<String, Object> map1 = new HashMap<>();
						map1.put("imageurl", ""+miUrlOk);
						reference.updateChildren(map1);

						// setImage(mImageUri);


						pd.dismiss();

					} else {
						Toast.makeText(SettingsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
					}
				}
			}).addOnFailureListener(new OnFailureListener() {
				@Override
				public void onFailure(@NonNull Exception e) {
					Toast.makeText(SettingsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			});

		} else {
			Toast.makeText(SettingsActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
		}
	}

	/* private void setImage(Uri mImageUri) {
         Glide.with(SettingsActivity.this)
                 .applyDefaultRequestOptions(new RequestOptions()
                         .placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                         .error(R.drawable.com_facebook_profile_picture_blank_square)
                         .diskCacheStrategy(DiskCacheStrategy.ALL))
                 .load(mImageUri)
                 .into(profile_image);

     }
 */
	private String getFileExtension(Uri uri){
		ContentResolver cR = getContentResolver();
		MimeTypeMap mime = MimeTypeMap.getSingleton();
		return mime.getExtensionFromMimeType(cR.getType(uri));
	}
	private void setProfileImage(Uri url){
		Glide.with(SettingsActivity.this)
				.applyDefaultRequestOptions(new RequestOptions()
						.placeholder(R.drawable.com_facebook_profile_picture_blank_square)
						.error(R.drawable.com_facebook_profile_picture_blank_square)
						.diskCacheStrategy(DiskCacheStrategy.ALL))
				.load(url)
				.into(profile_image);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

			CropImage.ActivityResult result = CropImage.getActivityResult(data);
			mImageUri = result.getUri();

			uploadImage();

		} else {
			Toast.makeText(this, "Something gone wrong!", Toast.LENGTH_SHORT).show();
		}
	}

	private boolean emailLogin(){
		return provider.equals("firebase");
	}



}


