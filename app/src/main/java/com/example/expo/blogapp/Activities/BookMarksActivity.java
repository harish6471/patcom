package com.example.expo.blogapp.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expo.blogapp.Activities.Helper.BookmarksViewHolder;
import com.example.expo.blogapp.Activities.Modal.Bookmarks;
import com.example.expo.blogapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookMarksActivity extends AppCompatActivity {

    private RecyclerView blog_list;
    private FirestoreRecyclerAdapter<Bookmarks, BookmarksViewHolder> adapter;
    private CoordinatorLayout coordinatorLayout;
    private FirebaseAuth mAuth;
    private String user_id;
    private RelativeLayout error;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bookmarks);


        mAuth = FirebaseAuth.getInstance();
     //   TextView header = findViewById(R.id.header);
     //   View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        error = findViewById(R.id.error);
        error.setVisibility(View.GONE);

        //Initializing the Recycler View
        blog_list =findViewById(R.id.blog_list);
        populateBlogList();
        blog_list.setAdapter(adapter);
        blog_list.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        blog_list.setLayoutManager(linearLayoutManager);
        coordinatorLayout = findViewById(R.id.main_layout);
        adapter.startListening();

        //Checking if no bookmarks
        if (adapter.getItemCount() == 0){
            error.setVisibility(View.VISIBLE);
        }else {
            error.setVisibility(View.GONE);
        }
    }

    private void populateBlogList() {
        Query query = FirebaseFirestore.getInstance()
                .collection("Users")
                .document(user_id)
                .collection("Bookmarks")
                .orderBy("BookmarkTime", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Bookmarks> options = new FirestoreRecyclerOptions.Builder<Bookmarks>()
                .setQuery(query, Bookmarks.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Bookmarks, BookmarksViewHolder>(options) {
            @Override
            public void onBindViewHolder(BookmarksViewHolder holder, int position, final Bookmarks model) {

                holder.setImage(model.getImage(),getApplicationContext());
                holder.setUser(BookMarksActivity.this, model.getUser());
                holder.setDate(model.getTime());
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.setDeleteBtn(model.getUser(),model.getTitle());
             //   holder.setView(model.getTitle());
                holder.showPostDetails(model.getImage(), model.getUser(), model.getTime(), model.getTitle(), BookMarksActivity.this );
            }

            @Override
            public BookmarksViewHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.blog_row, group, false);
                return new BookmarksViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                if (adapter.getItemCount() == 0){
                    error.setVisibility(View.VISIBLE);
                }else {
                    error.setVisibility(View.GONE);
                }
            }
        };
    }

    @Override
    public void onBackPressed() {

       finish();
    }


}
