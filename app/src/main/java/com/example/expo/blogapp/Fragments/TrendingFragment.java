package com.example.expo.blogapp.Fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expo.blogapp.Activities.Helper.BlogViewHolder;
import com.example.expo.blogapp.Activities.Modal.Blog;
import com.example.expo.blogapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TrendingFragment extends Fragment {

    private RecyclerView blog_list;
    private FirestoreRecyclerAdapter<Blog,BlogViewHolder> adapter;
    private CoordinatorLayout coordinatorLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, container, false);


        blog_list = view.findViewById(R.id.blog_list);
        populateBlogList();
        blog_list.setAdapter(adapter);
        blog_list.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        blog_list.setLayoutManager(linearLayoutManager);
        coordinatorLayout = view.findViewById(R.id.main_layout);
        adapter.startListening();

        return view;
    }



    private void populateBlogList(){

        Query query = FirebaseFirestore.getInstance()
                .collection("Posts")
              .whereEqualTo("Pending","Verified");
               // .orderBy("Pending", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Blog> options = new FirestoreRecyclerOptions.Builder<Blog>()
                .setQuery(query, Blog.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Blog, BlogViewHolder>(options) {
            @Override
            public void onBindViewHolder(BlogViewHolder holder, int position, final Blog model) {

                //holder.setImage(model.getImage(),getActivity());
                holder.setImage(getActivity(),model.getImage(),getActivity(),model.getTitle());
                holder.setUser(getActivity(), model.getUser(), getActivity());
                holder.setDate(model.getTime());
                holder.setTitle(model.getTitle());
                holder.setRevieww(getActivity(),model.getTitle(),getActivity());
                holder.setPhonenumber(model.getPhonenumber());
              //  holder.setUpi(model.getUpi());
                holder.setDesc(model.getDesc());
              //  holder.setLikes(getActivity(), model.getTitle());
                holder.setBookmark(getActivity(), model.getTitle(), model.getDesc(), model.getImage(), model.getTime(), coordinatorLayout, model.getUser());

             //   holder.showPostDetails(model.getImage(), model.getUser(), model.getTime(), model.getTitle(), model.getDetails(),model.getPhonenumber(),model.getUpi(), model.getViews(), getActivity());
            //    holder.showPostDetails(model.getImage(), model.getUser(), model.getTime(), model.getTitle(), model.getDetails(), model.getViews(),model.getPhonenumber(),model.getUpi(), getActivity());

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
