package com.example.expo.blogapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expo.blogapp.Activities.MainActivity;
import com.example.expo.blogapp.Activities.PostActivity;
import com.example.expo.blogapp.Activities.PostActivitycrop;
import com.example.expo.blogapp.Activities.SearchActivity;
import com.example.expo.blogapp.Activities.Upiactivity;
import com.example.expo.blogapp.Adapter.SliderAdapter;
import com.example.expo.blogapp.Models.Data;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Cateogeories extends AppCompatActivity {

    GridLayout mainGrid;
    Activity mActivity;
    Toolbar toolbar;
    RecyclerView mRecyclerView;
    //Unbinder mUnbinder;
    ViewPager viewPager;
    TabLayout indicator;
    TextView header1;

    List<Integer> icon;
    List<String> iconName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cateogeories);

        final Button button=(Button)findViewById(R.id.b1);
        final Button button1=(Button)findViewById(R.id.b2);
        final Button a=(Button)findViewById(R.id.a1);
        final Button a1=(Button)findViewById(R.id.a2);
        final Button button2=(Button)findViewById(R.id.b3);
        final Button button3=(Button)findViewById(R.id.b4);
        final Button button4=(Button)findViewById(R.id.b5);
        final Button button5=(Button)findViewById(R.id.b6);
        final Button button6=(Button)findViewById(R.id.b7);
        final Button button7=(Button)findViewById(R.id.b8);

        viewPager = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.indicator);

        icon = new ArrayList<>();
        icon.add(R.drawable.image2);
        icon.add(R.drawable.image4);
        icon.add(R.drawable.image);

        iconName = new ArrayList<>();
        iconName.add("Slide 1");
        iconName.add("Slide 2");
        iconName.add("Slide 3");

        viewPager.setAdapter(new SliderAdapter(this, icon, iconName));
        indicator.setupWithViewPager(viewPager, true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);


        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cateogeories.this,PostActivitycrop.class);

                startActivity(intent);
            }
        });
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cateogeories.this,MainActivity.class);

                startActivity(intent);
            }
        });



        button.setVisibility(View.GONE);
        button1.setVisibility(View.GONE);

        button2.setVisibility(View.GONE);
        button3.setVisibility(View.GONE);

        button4.setVisibility(View.GONE);
        button5.setVisibility(View.GONE);

        button6.setVisibility(View.GONE);
        button7.setVisibility(View.GONE);


        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        toolbar=findViewById(R.id.toolbar);


        //Set Event
       // setSingleEvent(mainGrid);
       setToggleEvent(mainGrid);
        setTouchEvent(mainGrid);


    }
    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
         Cateogeories.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() <icon  .size() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    private void setTouchEvent(GridLayout mainGrid) {
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final CardView cardView = (CardView) mainGrid.getChildAt(i);

            cardView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {



                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {

                        v.setScaleX(0.95f);
                        v.setScaleY(0.95f);

                    } else if (action == MotionEvent.ACTION_UP) {

                        v.animate().cancel();
                        v.animate().scaleX(1f).setDuration(1000).start();
                        v.animate().scaleY(1f).setDuration(1000).start();


                        // Here for example
                      //  performClick();

                    }

                    return false;
                }
            });
        }
    }

    private void setToggleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
      //  for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView

            final CardView cardView = (CardView) mainGrid.getChildAt(0);
        final CardView cardView1 = (CardView) mainGrid.getChildAt(1);
        final CardView cardView2 = (CardView) mainGrid.getChildAt(2);
        final CardView cardView3 = (CardView) mainGrid.getChildAt(3);

        final Button button=(Button)findViewById(R.id.b1);
        final Button button1=(Button)findViewById(R.id.b2);
        final Button button2=(Button)findViewById(R.id.b3);
        final Button button3=(Button)findViewById(R.id.b4);
        final Button button4=(Button)findViewById(R.id.b5);
        final Button button5=(Button)findViewById(R.id.b6);
        final Button button6=(Button)findViewById(R.id.b7);
        final Button button7=(Button)findViewById(R.id.b8);
        final TextView t1=findViewById(R.id.emergency);
        final TextView t2=findViewById(R.id.cancer);

        final TextView t3=findViewById(R.id.organizations);





        cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        button.setVisibility(View.VISIBLE);
                        button1.setVisibility(View.VISIBLE);
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);

                        button4.setVisibility(View.GONE);
                        button5.setVisibility(View.GONE);
                        button6.setVisibility(View.GONE);
                        button7.setVisibility(View.GONE);
                        //   Toast.makeText(Cateogeories.this, "State : True", Toast.LENGTH_SHORT).show();
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Cateogeories.this, PostActivitycrop.class);
                                intent.putExtra("Emergency", t1.getText().toString());
                                startActivity(intent);
                            }
                        });
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Cateogeories.this, SearchActivity.class);
                                intent.putExtra("Emergency", t1.getText().toString());
                                startActivity(intent);
                            }
                        });


                    } else {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                        //Toast.makeText(Cateogeories.this, "State : False", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                    //Change background color
                    cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    button2.setVisibility(View.VISIBLE);
                    button3.setVisibility(View.VISIBLE);
                    button.setVisibility(View.GONE);
                    button1.setVisibility(View.GONE);
                    button4.setVisibility(View.GONE);
                    button5.setVisibility(View.GONE);
                    button6.setVisibility(View.GONE);
                    button7.setVisibility(View.GONE);

                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Cateogeories.this, PostActivitycrop.class);
                            intent.putExtra("cancer", t2.getText().toString());
                            startActivity(intent);
                        }
                    });
                    button3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Cateogeories.this, SearchActivity.class);
                            intent.putExtra("cancer", t2.getText().toString());
                            startActivity(intent);
                        }
                    });



                      /*  button2.setVisibility(View.VISIBLE);
                        button3.setVisibility(View.VISIBLE);
                        button4.setVisibility(View.VISIBLE);
                        button5.setVisibility(View.VISIBLE);
                        button6.setVisibility(View.VISIBLE);
                        button7.setVisibility(View.VISIBLE);*/
                    //   Toast.makeText(Cateogeories.this, "State : True", Toast.LENGTH_SHORT).show();

                } else {
                    //Change background color
                    cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                    //Toast.makeText(Cateogeories.this, "State : False", Toast.LENGTH_SHORT).show();
                }


            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                    //Change background color
                    cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    button4.setVisibility(View.VISIBLE);
                    button5.setVisibility(View.VISIBLE);

                    button2.setVisibility(View.GONE);
                    button3.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    button1.setVisibility(View.GONE);

                    button6.setVisibility(View.GONE);
                    button7.setVisibility(View.GONE);

                    button4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Cateogeories.this, PostActivity.class);
                            intent.putExtra("organizations", t3.getText().toString());
                            startActivity(intent);
                        }
                    });
                    button5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Cateogeories.this, SearchActivity.class);
                            intent.putExtra("organizations", t3.getText().toString());
                            startActivity(intent);
                        }
                    });

                      /*  button2.setVisibility(View.VISIBLE);
                        button3.setVisibility(View.VISIBLE);
                        button4.setVisibility(View.VISIBLE);
                        button5.setVisibility(View.VISIBLE);
                        button6.setVisibility(View.VISIBLE);
                        button7.setVisibility(View.VISIBLE);*/
                    //   Toast.makeText(Cateogeories.this, "State : True", Toast.LENGTH_SHORT).show();

                } else {
                    //Change background color
                    cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                    //Toast.makeText(Cateogeories.this, "State : False", Toast.LENGTH_SHORT).show();
                }




            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                    //Change background color
                    cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    button6.setVisibility(View.VISIBLE);
                    button7.setVisibility(View.VISIBLE);
                    button2.setVisibility(View.GONE);
                    button3.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    button1.setVisibility(View.GONE);
                    button4.setVisibility(View.GONE);
                    button5.setVisibility(View.GONE);

                      /*  button2.setVisibility(View.VISIBLE);
                        button3.setVisibility(View.VISIBLE);
                        button4.setVisibility(View.VISIBLE);
                        button5.setVisibility(View.VISIBLE);
                        button6.setVisibility(View.VISIBLE);
                        button7.setVisibility(View.VISIBLE);*/
                    //   Toast.makeText(Cateogeories.this, "State : True", Toast.LENGTH_SHORT).show();

                } else {
                    //Change background color
                    cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                    //Toast.makeText(Cateogeories.this, "State : False", Toast.LENGTH_SHORT).show();
                }


            }


        });



}


    @Override
    protected void onDestroy() {
        super.onDestroy();
       // if (mUnbinder != null) mUnbinder.unbind();
    }
    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Cateogeories.this, MainActivity.class);
                    intent.putExtra("info","This is activity from card item index  "+finalI);
                    startActivity(intent);

                }
            });
        }
    }



}
