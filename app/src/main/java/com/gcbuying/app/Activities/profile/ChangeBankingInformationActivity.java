package com.gcbuying.app.Activities.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.gcbuying.app.R;

public class ChangeBankingInformationActivity extends AppCompatActivity {
RelativeLayout rlback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_banking_information);
        rlback = findViewById(R.id.rlback);
        rlback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}