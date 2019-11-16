package com.example.expo.blogapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.expo.blogapp.Activities.Helper.CustomTypefaceSpan;
import com.example.expo.blogapp.Activities.ProfileActivity;
import com.example.expo.blogapp.Fragments.ActivityFragment;
import com.example.expo.blogapp.Fragments.HomeFragment1;
import com.example.expo.blogapp.Fragments.TrendingFragment;
import com.example.expo.blogapp.Fragments.addFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView header1;
    FirebaseAuth mAuth;
    BottomNavigationView bottom_nav;
    private CircleImageView profileimage1;
    private Fragment homeFragment,trendingFragment,bookmarksFragment,activityFragment,addFragemnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        mAuth = FirebaseAuth.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
     getSupportActionBar().setTitle("");
       Typeface pacifico = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        header1 = findViewById(R.id.header1);
       header1.setTypeface(pacifico);
        profileimage1 = findViewById(R.id.profileimage1);
        profileimage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(NavigationActivity.this, ProfileActivity.class);
                profile.putExtra("UserId", mAuth.getCurrentUser().getUid());
                startActivity(profile);
            }
        });
       loadprofileImage(mAuth.getCurrentUser().getPhotoUrl());

      /*  FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        bottom_nav = findViewById(R.id.bottom_nav);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               drawer.openDrawer(GravityCompat.START);
            }
        });
        toggle.setHomeAsUpIndicator(R.drawable.ic_hamburg);
        navigationView.setNavigationItemSelectedListener(this);

        Menu m = bottom_nav.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);
            applyFontToMenuItem(mi);
        }
        bottom_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Initializing Home Fragment
        homeFragment = new HomeFragment1();
        addFragemnt = new addFragment();
        trendingFragment = new TrendingFragment();
        //   bookmarksFragment = new BookmarksFragment();
        activityFragment = new ActivityFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .add(R.id.fragment_container,trendingFragment)
                .add(R.id.fragment_container,addFragemnt)
                // .add(R.id.fragment_container,bookmarksFragment)
                .add(R.id.fragment_container,activityFragment)
                .hide(trendingFragment)
                .hide(addFragemnt)
                //.hide(bookmarksFragment)
                .hide(activityFragment)

                .commit();
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tap again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(homeFragment, trendingFragment,addFragemnt,activityFragment);
                    return true;
                case R.id.trending:
                    replaceFragment(trendingFragment, homeFragment,addFragemnt,  activityFragment);
                    return true;
                // case R.id.bookmarks:
                //  replaceFragment( homeFragment,trendingFragment,addFragemnt,activityFragment);
                //  return true;
                case R.id.activity:
                    replaceFragment(activityFragment,homeFragment,trendingFragment,addFragemnt);
                    return true;

            }
            return false;
        }
    };

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private void loadprofileImage(Uri uri){
        Glide.with(NavigationActivity.this)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                        .error(R.drawable.com_facebook_profile_picture_blank_square)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .load(uri)
                .into(profileimage1);
    }

    private void replaceFragment( Fragment one, Fragment two, Fragment three, Fragment four){
        if (!one.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .show(one)
                    .hide(two)
                    .hide(three)
                    .hide(four)
                    //.hide(five)
                    .commit();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
