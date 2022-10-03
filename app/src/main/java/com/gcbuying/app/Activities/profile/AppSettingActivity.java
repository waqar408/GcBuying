package com.gcbuying.app.Activities.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gcbuying.app.R;

public class AppSettingActivity extends AppCompatActivity {
    ImageView imgBack;
    LinearLayout lnFAQ,lnPrivacy,lnTermsAndCondition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);
        imgBack = findViewById(R.id.imgBack);
        lnFAQ = findViewById(R.id.lnFAQ);
        lnPrivacy = findViewById(R.id.lnPrivacy);
        lnTermsAndCondition = findViewById(R.id.lnTermsAndCondition);
        lnFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://gcbuying.com/"));
                startActivity(viewIntent);
            }
        });
        lnPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://gcbuying.com/privacy"));
                startActivity(viewIntent);
            }
        });
        lnTermsAndCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://gcbuying.com/privacy"));
                startActivity(viewIntent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}