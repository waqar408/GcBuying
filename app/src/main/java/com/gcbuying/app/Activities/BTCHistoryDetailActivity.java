package com.gcbuying.app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcbuying.app.R;

public class BTCHistoryDetailActivity extends AppCompatActivity {
    RelativeLayout rl;
    TextView tvAmount,tvStatus,txId,destAddress,totalAmount;
    LinearLayout lna;
    View viewa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btc_history_detail);
        rl = findViewById(R.id.rl);
        tvAmount = findViewById(R.id.tvAmount);
        tvStatus = findViewById(R.id.tvStatus);
        txId = findViewById(R.id.txId);
        destAddress = findViewById(R.id.destAddress);
        totalAmount = findViewById(R.id.totalAmount);
        lna = findViewById(R.id.lna);
        viewa = findViewById(R.id.viewa);

        Intent intent = getIntent();
        String status = intent.getStringExtra("status");
        String amount = intent.getStringExtra("amount");
        String txid = intent.getStringExtra("txid");
        String totalAmounts = intent.getStringExtra("totalAmount");
        String destAddresss = intent.getStringExtra("destAddress");
        if (status.equals("RECIEVED"))
        {
            lna.setVisibility(View.GONE);
            viewa.setVisibility(View.GONE);
        }
        tvAmount.setText(amount);
        txId.setText(txid);
        totalAmount.setText(totalAmounts);
        destAddress.setText(destAddresss);
        tvStatus.setText(status);



        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}