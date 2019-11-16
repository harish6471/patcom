package com.example.expo.blogapp.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.os.PersistableBundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import com.github.clans.fab.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


import com.example.expo.blogapp.Activities.Helper.BottomNavigationViewHelper;
import com.example.expo.blogapp.Activities.Helper.CustomTypefaceSpan;
import com.example.expo.blogapp.Cateogeories;
import com.example.expo.blogapp.FeedBack;
import com.example.expo.blogapp.Fragments.ActivityFragment;

import com.example.expo.blogapp.Fragments.BookmarksFragment;
import com.example.expo.blogapp.Fragments.HomeFragment1;
import com.example.expo.blogapp.Fragments.NotificationFragment;
import com.example.expo.blogapp.Fragments.NotificationFragment1;
import com.example.expo.blogapp.Fragments.OrganizationsFragment;
import com.example.expo.blogapp.Fragments.TrendingFragment;
import com.example.expo.blogapp.Fragments.addFragment;
import com.example.expo.blogapp.Models.Notification;
import com.example.expo.blogapp.R;

import com.example.expo.blogapp.addFragment1;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.webianks.easy_feedback.EasyFeedback;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    //SliderView sliderView;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    String tag="MainActivity";
    TextView header;
    FirebaseAuth mAuth;
    private StorageReference storageReference;
    BottomNavigationView bottom_nav;
    private CircleImageView profileimage,settings,search;
    private Fragment homeFragment,trendingFragment, notificationFragment,bookmarksFragment,activityFragment,addFragemnt1,organizationsFragment;
    private Object PostActivitycrop;
    private View notificationBadge;



    Fragment selectedfragment = null;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() ==null) {
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);

            finish();
        }



        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        mAuth = FirebaseAuth.getInstance();

    /*    mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                   // MainActivity.this.finish();
                    Intent main =  new Intent(MainActivity.this, LoginActivity1.class);
                   // main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main);
                }

            }
        });*/
          isOnline();



        dl = (DrawerLayout)findViewById(R.id.drawer_layout);
    //  t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
    //   dl.addDrawerListener(t);
    //    t.syncState();

   // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            t = new ActionBarDrawerToggle(this, dl,toolbar, R.string.open_drawer, R.string.close_drawer)
            {

                public void onDrawerClosed(View view)
                {
                    supportInvalidateOptionsMenu();
                    //drawerOpened = false;
                }

                public void onDrawerOpened(View drawerView)
                {
                    supportInvalidateOptionsMenu();
                    //drawerOpened = true;
                }
            };
            t.setDrawerIndicatorEnabled(true);
           dl.setDrawerListener(t);
          t.syncState();
        }


        nv = (NavigationView)findViewById(R.id.nvView);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id) {
                    case R.id.account:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        dl.closeDrawer(GravityCompat.START);
                        break;
                    //  Toast.makeText(MainActivity.this, "My Account",Toast.LENGTH_SHORT).show();break;
                    case R.id.settings:
                        startActivity(new Intent(MainActivity.this, SettingsActivity2.class));
                        dl.closeDrawer(GravityCompat.START);
                        // profile.putExtra("UserId", mAuth.getCurrentUser().getUid());
                        break;

                    case R.id.bookmarks:

                      /* Fragment fragment = new BookmarksFragment();
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                                .addToBackStack(null)
                                .commit();
                        break;*/
                     startActivity(new Intent(MainActivity.this, BookMarksActivity.class));
                        dl.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.categories:
                       startActivity(new Intent(MainActivity.this, Cateogeories.class));
                        dl.closeDrawer(GravityCompat.START);
                        break;



                    case R.id.mail1:

                        Toast.makeText(getApplicationContext(),"Sending Mail",Toast.LENGTH_LONG).show();

                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto","patcom.h@gmail.com", null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));
                    //    startActivity(new Intent(MainActivity.this, FeedBack.class));
                        break;
                    case R.id.phonev:
                        Toast.makeText(MainActivity.this, "Calling ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:0123456789"));
                        startActivity(intent);
                        //startActivity(new Intent(MainActivity.this, FeedBack.class));
                        break;
                    case R.id.exit:
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory(Intent.CATEGORY_HOME);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                        break;


                }
               //

                return true;



            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.fb);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.fab1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.fab2);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostActivitycrop1.class);
                startActivity(intent);
            //    floatingActionButton1.hide(false);
            //    floatingActionButton2.hide(false);
                materialDesignFAM.close(true);
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivity(intent);
                materialDesignFAM.close(true);
            }
        });

     //   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
     /*   fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(MainActivity.this, PostActivitycrop1.class);
                startActivity(intent);
            }
        });*/
        Typeface pacifico = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        header = findViewById(R.id.header);
        header.setTypeface(pacifico);
        storageReference = FirebaseStorage.getInstance().getReference();
        profileimage = findViewById(R.id.profileimage);
    // settings = findViewById(R.id.settings);
        search = findViewById(R.id.search);

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(MainActivity.this, SettingsActivity.class);
                profile.putExtra("UserId", mAuth.getCurrentUser().getUid());
                startActivity(profile);
            }
        });
/*       settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(MainActivity.this, SettingsActivity2.class);
                profile.putExtra("UserId", mAuth.getCurrentUser().getUid());
                startActivity(profile);
            }
        });*/
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(MainActivity.this, SearchActivity.class);
                profile.putExtra("UserId", mAuth.getCurrentUser().getUid());
                startActivity(profile);
            }
        });
     // loadprofileImage((mAuth.getCurrentUser().getPhotoUrl() != null ?mAuth.getCurrentUser().getPhotoUrl() : null));
        loadprofileImage(mAuth.getCurrentUser().getPhotoUrl());



       /* BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navigationView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(3);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge, itemView, true);*/




        bottom_nav = findViewById(R.id.bottom_nav);
//        BottomNavigationViewHelper.disableShiftMode(bottom_nav);
        Menu m = bottom_nav.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);
            applyFontToMenuItem(mi);
          //  addBadgeView();

        }
        bottom_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        homeFragment = new HomeFragment1();
        trendingFragment = new TrendingFragment();
      notificationFragment=new NotificationFragment1();
      organizationsFragment=new OrganizationsFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .add(R.id.fragment_container,trendingFragment)
               // .add(R.id.fragment_container,bookmarksFragment)
                .add(R.id.fragment_container, notificationFragment)
                .add(R.id.fragment_container,organizationsFragment)
                .hide(trendingFragment)
                //.hide(bookmarksFragment)
                .hide(notificationFragment)
                .hide(organizationsFragment)

                .commit();

    }

    private void addBadgeView() {
        try {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottom_nav.getChildAt(0);

            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(3);

            notificationBadge = LayoutInflater.from(this).inflate(R.layout.viewnotification, menuView, false);
            //    notificationBadge1 = LayoutInflater.from(this).inflate(R.layout.viewnotification ,menuView1, false);
            //   notificationBadge2 = LayoutInflater.from(this).inflate(R.layout.viewnotification ,menuView2, false);
            itemView.addView(notificationBadge);
          //  notificationBadge.setVisibility(GONE);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
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
                    replaceFragment(homeFragment, trendingFragment, notificationFragment,organizationsFragment);
                    return true;
                case R.id.trending:
                    replaceFragment(trendingFragment, homeFragment,  notificationFragment,organizationsFragment);

                    return true;
               // case R.id.bookmarks:
                  //  replaceFragment( homeFragment,trendingFragment,addFragemnt,activityFragment);
                  //  return true;
                case R.id.activity:
                    replaceFragment(notificationFragment,homeFragment, trendingFragment,organizationsFragment);

                    notificationBadge.setVisibility(GONE);
                    refreshBadgeView();


                    return true;
                case R.id.organizations:
                    replaceFragment(organizationsFragment,notificationFragment,homeFragment, trendingFragment);

                 //   notificationBadge.setVisibility(GONE);
                   // refreshBadgeView();


                    return true;

            } if (selectedfragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedfragment).commit();
            }

            return true;

        }
    };

    private void refreshBadgeView() {
        try {
            boolean badgeIsVisible = notificationBadge.getVisibility() != GONE;
            notificationBadge.setVisibility(badgeIsVisible ? GONE : VISIBLE);//makes badge visible and invisible
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            try {
                AlertDialog.Builder builder =new AlertDialog.Builder(this);
                builder.setTitle("No internet Connection");
                builder.setMessage("Please turn on internet connection to continue");
                builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }catch (Exception e){
                Log.d(tag,"Show Dialog: " + e.getMessage());
            }
           // Toast.makeText(MainActivity.this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
        addBadgeView();
        notificationBadge.setVisibility(GONE);
      //  refreshBadge();
    }



   private void loadprofileImage(Uri uri){
       //StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile_images");

        Glide.with(MainActivity.this)
                .applyDefaultRequestOptions(new RequestOptions()

                        .placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                        .error(R.drawable.com_facebook_profile_picture_blank_square)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .load(uri)
                .into(profileimage);
    }

    private void replaceFragment(Fragment one, Fragment two, Fragment three, Fragment four){
        if (!one.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .show(one)
                    .hide(two)
//                    .hide(three)
                    .hide(three)
                    .hide(four)
                    //.hide(five)
                    .commit();
        }

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        super.onPostCreate(savedInstanceState);
        t.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
      t.onConfigurationChanged(newConfig);
    }
}