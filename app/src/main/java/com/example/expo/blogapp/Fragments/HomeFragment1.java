package com.example.expo.blogapp.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expo.blogapp.Activities.Helper.BlogViewHolder;
import com.example.expo.blogapp.Activities.Modal.Blog;
import com.example.expo.blogapp.Models.Data;
import com.example.expo.blogapp.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.widget.ShareButton;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class HomeFragment1 extends Fragment {

    public static final String ARG_POSITION ="jdjdo" ;
    private RecyclerView blog_list;
    private FirestoreRecyclerAdapter<Blog, BlogViewHolder> adapter;
   // private MultiViewTypeAdapter<> adapter1;
    private CoordinatorLayout coordinatorLayout;

    // private ShareButton shareButton;
    //image
    private Bitmap image;
    //counter
    private int counter = 0;

    CallbackManager callbackManager;
    
    private ShareButton shareButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
       // AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        //callbackManager = CallbackManager.Factory.create();

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //  setLikeButton(view);
    }

   /* private void setLikeButton(View view) {
        LikeView likeView =  view.findViewById(R.id.likeView);
       // shareButton.setShareContent(content);
        likeView.setLikeViewStyle(LikeView.Style.STANDARD);
        likeView.setFragment(this);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);

        String pageUrlToLike = "https://www.facebook.com/FacebookDevelopers";
        likeView.setObjectIdAndType(pageUrlToLike, LikeView.ObjectType.PAGE);

    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_fragment1, container, false);

        shareButton = view.findViewById(R.id.fb_share_button);
        ArrayList<Data> list = new ArrayList<>();
        list.add(new Data(Data.VIEW_PAGER, "Hello. This is the View Pager view type with images", 0));



        blog_list = view.findViewById(
                R.id.blog_list);
        populateBlogList();
        blog_list.setAdapter(adapter);
       // blog_list.setAdapter(adapter1);
       blog_list.setItemAnimator(new DefaultItemAnimator());
        blog_list.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        blog_list.setLayoutManager(linearLayoutManager);
        coordinatorLayout = view.findViewById(R.id.main_layout);
        adapter.startListening();



        return view;


    }


    private void populateBlogList() {


        Query query = FirebaseFirestore.getInstance()
                .collection("Posts")
                .orderBy("Time", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<Blog> options = new FirestoreRecyclerOptions.Builder<Blog>()
                .setQuery(query, Blog.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Blog, BlogViewHolder>(options) {
            @Override
            public void onBindViewHolder(BlogViewHolder holder, final int position, final Blog model) {
                //  final Blog blog = .get(position);


                holder.setImage(getActivity(), model.getImage(), getActivity(), model.getTitle());
                // holder.setHospital(getActivity(),model.getImage(),getActivity(),model.getTitle(),model.getHospitalname());
                //  holder.setHospitall(getActivity(),model.getImage(),getActivity(),model.getTitle());
                holder.setUser(getActivity(), model.getUser(), getActivity());
                holder.setDate(model.getTime());
                holder.setFullImage(model.getImage(), getActivity());
                // holder.setAmountgoal(model.getAmountgoal());
                holder.setTitle(model.getTitle());
                // holder.setReview(model.getReview());
                holder.setPhonenumber(model.getPhonenumber());
                //  holder.setReview(model.getReview());
                holder.setRevieww(getActivity(), model.getTitle(), getActivity());
                holder.setReviews(getActivity());
                holder.setReviewss(getActivity());
                holder.setHospitalname(model.getHospitalname());
                holder.setHospitalbill(model.getHospitalbill());
                //     holder.setAmountgoal(getActivity(),model.getTitle(),getActivity());
                holder.setAmount(getActivity(), model.getTitle(), model.getHospitalname(), getActivity());
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
                holder.setBookmark(getActivity(), model.getTitle(), model.getDesc(), model.getImage(), model.getTime(), coordinatorLayout, model.getUser());
                // holder.setDonateBtn(model.getUser(),getActivity(),model.getTitle(),model.getDesc(),model.getPhonenumber(),model.getUpi(),model.getImage(),model.getTime(),coordinatorLayout);

                holder.showPostDetails(model.getImage(), model.getUser(), model.getTime(), model.getTitle(), model.getDetails(), model.getViews(), model.getPhonenumber(), model.getUpi(), getActivity(), model.getHospitalname(), model.getHospitalbill());
            }

            @Override
            public BlogViewHolder onCreateViewHolder(ViewGroup group, int i) {

                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.blog_row, group, false);
                return new BlogViewHolder(view);
            }

        };




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Call callbackManager.onActivityResult to pass login result to the LoginManager via callbackManager.

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
