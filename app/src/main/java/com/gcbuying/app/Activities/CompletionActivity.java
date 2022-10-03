package com.gcbuying.app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gcbuying.app.R;

public class CompletionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion);
    }

    public void tohome(View view) {

        startActivity(new Intent(CompletionActivity.this,Home1Activity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}