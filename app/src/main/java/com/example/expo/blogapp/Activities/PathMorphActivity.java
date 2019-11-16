package com.example.expo.blogapp.Activities;

import android.view.View;

import com.example.expo.blogapp.Activities.Helper.BlogViewHolder;
import com.example.expo.blogapp.R;

public class PathMorphActivity extends BlogViewHolder{
    public PathMorphActivity(View itemView) {
        super(itemView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.blog_row;
    }
}