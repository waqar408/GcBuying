package com.gcbuying.app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gcbuying.app.R;

public class TradeHistoryDetailActivity extends AppCompatActivity {
    RelativeLayout rlBack,lnstatus;
    String ngnAmount,status,txnId,method,rate,sellamount;
    TextView ngnamount,status1,txnid,method1,amount,rate1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_history_detail);
        rlBack =  findViewById(R.id.rl);

        Intent intent = new Intent();
        ngnAmount = getIntent().getExtras().getString("ngnamount");
        status = getIntent().getExtras().getString("status");
        txnId = getIntent().getExtras().getString("txnid");
        method = getIntent().getExtras().getString("method");
        rate = getIntent().getExtras().getString("rate");
        sellamount = getIntent().getExtras().getString("sellamount");

        ngnamount = findViewById(R.id.ngnamount);
        status1 = findViewById(R.id.status);
        txnid = findViewById(R.id.txnid);
        method1 = findViewById(R.id.method);
        amount = findViewById(R.id.amount);
        rate1 = findViewById(R.id.rate);
        lnstatus = findViewById(R.id.lnstatus);


        if (status.equals("Cancelled"))
        {
            lnstatus.setBackgroundResource(R.drawable.cancelled_background);
            status1.setTextColor(getResources().getColor(R.color.pdlg_color_red));
        }
        if (status.equals("Completed"))
        {
            lnstatus.setBackgroundResource(R.drawable.complete_background);
            status1.setTextColor(getResources().getColor(R.color.pdlg_color_green));

        }
        if (status.equals("Pending"))
        {
            lnstatus.setBackgroundResource(R.drawable.pending_background);
            status1.setTextColor(getResources().getColor(R.color.pdlg_color_red));

        }
        ngnamount.setText(ngnAmount);
        status1.setText(status);
        txnid.setText(txnId);
        method1.setText(method);
        rate1.setText(rate);
        Toast.makeText(this, ""+ngnAmount, Toast.LENGTH_SHORT).show();

        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}