package com.example.expo.blogapp.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expo.blogapp.Activities.Helper.BlogViewHolder;
import com.example.expo.blogapp.Activities.Modal.Blog;
import com.example.expo.blogapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SearchActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    ImageView back;
    EditText search_input;
    RecyclerView blog_list;
    private FirestoreRecyclerAdapter<Blog, BlogViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Finding view by id
        coordinatorLayout = findViewById(R.id.constraint_layout);
        toolbar = findViewById(R.id.toolbar);
        back = findViewById(R.id.back);
        search_input = findViewById(R.id.search_input);
        blog_list = findViewById(R.id.blog_list);

        Intent intent = getIntent();

      search_input.setText(intent.getExtras().getString("cancer"));
        search_input.setText(intent.getExtras().getString("organizations"));
        search_input.setText(intent.getExtras().getString("Emergency"));



        //Initializing the recycler view



        //Customizing the action bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        search_input.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (search_input.getRight() - search_input.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        search_input.setText("");
                    }
                }
                return false;
            }
        });

        search_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {


                    String text = search_input.getText().toString();
                    if (!text.equals("")){
                        populateBlogList(text);
                        blog_list.setAdapter(adapter);
                        blog_list.setHasFixedSize(true);
                        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                        blog_list.setLayoutManager(linearLayoutManager);
                        adapter.startListening();
                    }
                }
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.super.onBackPressed();
            }
        });
    }


    private void populateBlogList(final String search_text){


        Query query = FirebaseFirestore.getInstance()
                .collection("Posts")
                .orderBy("Title")
                .orderBy("Time", Query.Direction.DESCENDING)
                .whereGreaterThanOrEqualTo("Title", search_text);
//                .startAt(search_text)
//                .endAt(search_text + "\uf8ff");


        FirestoreRecyclerOptions<Blog> options = new FirestoreRecyclerOptions.Builder<Blog>()
                .setQuery(query, Blog.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<Blog, BlogViewHolder>(options) {
            @Override
            public void onBindViewHolder(BlogViewHolder holder, int position, final Blog model) {

              //  holder.setImage(model.getImage(),SearchActivity.this);
                holder.setImage(SearchActivity.this, model.getImage(), SearchActivity.this, model.getTitle());
                // holder.setHospital(getActivity(),model.getImage(),getActivity(),model.getTitle(),model.getHospitalname());
                //  holder.setHospitall(getActivity(),model.getImage(),getActivity(),model.getTitle());
                holder.setUser(SearchActivity.this, model.getUser(), SearchActivity.this);
                holder.setDate(model.getTime());
                holder.setFullImage(model.getImage(), SearchActivity.this);
                // holder.setAmountgoal(model.getAmountgoal());
                holder.setTitle(model.getTitle());
                // holder.setReview(model.getReview());
                holder.setPhonenumber(model.getPhonenumber());
                //  holder.setReview(model.getReview());
                holder.setRevieww(SearchActivity.this, model.getTitle(), SearchActivity.this);
                holder.setReviews(SearchActivity.this);
                holder.setReviewss(SearchActivity.this);
                holder.setHospitalname(model.getHospitalname());
                holder.setHospitalbill(model.getHospitalbill());
                //     holder.setAmountgoal(getActivity(),model.getTitle(),getActivity());
                holder.setAmount(SearchActivity.this, model.getTitle(), model.getHospitalname(), SearchActivity.this);
                //  holder.setUpi(model.getUpi());
                // holder.
                // holder.setShareButton(model.getImage(),getActivity());
                // ShareButton shareButton = itemView.findViewById(R.id.fb_share_button);

                //  holder.setLinkShare(getView());
                holder.setDesc(model.getDesc());
                //  holder.setDonateBtn(model.getUser(),model.getDesc(),model.getTitle(),model.getImage(),model.getPhonenumber(),model.getUpi(),coordinatorLayout,model.getTime(),getActivity());
                //  holder.setLikes(getActivity(),model.getTitle());
                holder.setDeleteBtn(model.getUser(), model.getTitle());
                // holder.setMore(model.getUser(),getActivity());
                holder.getNotification(model.getUser(), model.getPostid(), model.getImage());
                holder.setBookmark(SearchActivity.this, model.getTitle(), model.getDesc(), model.getImage(), model.getTime(), coordinatorLayout, model.getUser());
                // holder.setDonateBtn(model.getUser(),getActivity(),model.getTitle(),model.getDesc(),model.getPhonenumber(),model.getUpi(),model.getImage(),model.getTime(),coordinatorLayout);

                holder.showPostDetails(model.getImage(), model.getUser(), model.getTime(), model.getTitle(), model.getDetails(), model.getViews(), model.getPhonenumber(), model.getUpi(), SearchActivity.this, model.getHospitalname(), model.getHospitalbill());
            }



            @Override
            public BlogViewHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.blog_row, group, false);
                return new BlogViewHolder(view);
            }
        };
    }
}