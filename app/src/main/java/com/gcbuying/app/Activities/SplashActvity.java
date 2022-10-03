package com.gcbuying.app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcbuying.app.Adapters.SplashAdapter;
import com.gcbuying.app.R;
import com.gcbuying.app.utilities.Utilities;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SplashActvity extends AppCompatActivity {

    ViewPager viewPager;
    ArrayList<Integer> images;
    SplashAdapter adpter;
    TextView tv_skip;
    ImageView imgskip;
    TextView tvwelcome;
    String login = "";
    Utilities utilities = new Utilities(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_actvity);


        login = Utilities.getString(this,"login");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.splashbackcolor));
        }
        viewPager = findViewById(R.id.viewPager);
        tv_skip = findViewById(R.id.tv_skip);
        tvwelcome = findViewById(R.id.tvwelcome);
        images = new ArrayList<>();
        images.add(R.drawable.three);
        images.add(R.drawable.four);
        imgskip = findViewById(R.id.imgskip);
        adpter = new SplashAdapter(this, images);
        viewPager.setPadding(0, 0, 0, 0);
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(adpter);

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (login.equals("login"))
                {
                    startActivity(new Intent(SplashActvity.this, Home1Activity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActvity.this, LoginActivity.class));
                    finish();
                }



            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                if (position == 0) {

                    // viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    tv_skip.setText("Skip");
                    tv_skip.setTextColor(getResources().getColor(R.color.textcolor));
                    imgskip.setImageResource(R.drawable.halfarrow);
                    tvwelcome.setText("Welcome");
                    tvwelcome.setTextColor(getResources().getColor(R.color.textcolor));

                    imgskip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            swipeRight(position);
                        }
                    });

                }

                if (position == 1) {

                    tv_skip.setText("");
                    imgskip.setImageResource(R.drawable.fullarrow);
                    tvwelcome.setText("Gift Card and Bitcoin Exchange");
                    tvwelcome.setTextColor(getResources().getColor(R.color.textcolor));
                    imgskip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String userStatus = Utilities.getString(SplashActvity.this,"loggedIn");
                            if (userStatus.equals("loggedIn"))
                            {
                                startActivity(new Intent(SplashActvity.this,Home1Activity.class));
                                finish();
                            }else {
                                startActivity(new Intent(SplashActvity.this, LoginActivity.class));
                                finish();
                            }
                        }
                    });


                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    public void swipeRight(int x){
        if(x < 2){
            viewPager.setCurrentItem(x + 1);
        }
    }
}