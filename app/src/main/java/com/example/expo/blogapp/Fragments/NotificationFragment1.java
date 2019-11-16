package com.example.expo.blogapp.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expo.blogapp.Activities.Helper.BlogViewHolder;
import com.example.expo.blogapp.Activities.Helper.NotificationViewHolder;
import com.example.expo.blogapp.Activities.Modal.Blog;
import com.example.expo.blogapp.Activities.Modal.Notifications;
import com.example.expo.blogapp.Models.Data;
import com.example.expo.blogapp.Models.Notification;
import com.example.expo.blogapp.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.widget.ShareButton;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class NotificationFragment1 extends Fragment {

    public static final String ARG_POSITION ="jdjdo" ;
    private RecyclerView blog_list;
    private FirestoreRecyclerAdapter<Notifications, NotificationViewHolder> adapter;
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



         blog_list= view.findViewById(R.id.blog_list);
        populateBlogList();
       blog_list.setAdapter(adapter);
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
                .collection("Notifications")
                .orderBy("body", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<Notifications> options = new FirestoreRecyclerOptions.Builder<Notifications>()
                .setQuery(query, Notifications.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Notifications, NotificationViewHolder>(options) {
            @Override
            public void onBindViewHolder(NotificationViewHolder holder, final int position, final Notifications model2) {
                //  final Blog blog = .get(position);


                holder.setImage(getActivity(), model2.getImage(), getActivity(),model2.getTitle());
                holder.setBody(getActivity(),model2.getTitle(),getActivity());
                holder.setTitle(getActivity(),model2.getTitle(),getActivity());
                holder.setDeleteBtn(model2.getUser(),model2.getTitle());

               // holder.showPostDetails(model.getImage(), model.getUser(), model.getTime(), model.getTitle(), model.getDetails(), model.getViews(), model.getPhonenumber(), model.getUpi(), getActivity(), model.getHospitalname(), model.getHospitalbill());
            }

            @Override
            public NotificationViewHolder onCreateViewHolder(ViewGroup group, int i) {

                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.blog_row3, group, false);
                return new NotificationViewHolder(view);
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
