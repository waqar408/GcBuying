package com.gcbuying.app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcbuying.app.R;

public class WithdrawHistoryDetailActivity extends AppCompatActivity {
    RelativeLayout rlBack;
    TextView tvAmount, tvStatus, tvTxId, bankName, accountName, accountNo;
    RelativeLayout statusBack;
    String txtId, ammount, notes, status, bankNames, accountNames, accountNos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_history_detail);
        rlBack = findViewById(R.id.rl);
        tvAmount = findViewById(R.id.tvAmount);
        tvStatus = findViewById(R.id.tvStatus);
        statusBack = findViewById(R.id.l);
        tvTxId = findViewById(R.id.tvTxId);
        bankName = findViewById(R.id.bankName);
        accountName = findViewById(R.id.accountName);
        accountNo = findViewById(R.id.accountNo);

        Intent intent = getIntent();
        txtId = intent.getStringExtra("trxId");
        ammount = intent.getStringExtra("amount");
        notes = intent.getStringExtra("notes");
        status = intent.getStringExtra("status");
        bankNames = intent.getStringExtra("bankName");
        accountNames = intent.getStringExtra("accountName");
        accountNos = intent.getStringExtra("accountNo");


        tvTxId.setText(txtId);
        tvAmount.setText(ammount);
        tvStatus.setText(status);
        bankName.setText(bankNames);
        accountName.setText(accountNames);
        accountNo.setText(accountNos);
        if (status.equals("Completed"))
        {
            statusBack.setBackgroundResource(R.drawable.complete_background);
            tvStatus.setTextColor(getResources().getColor(R.color.completedTextColors));
        }else {
            statusBack.setBackgroundResource(R.drawable.pending_background);
            tvStatus.setTextColor(getResources().getColor(R.color.pendingTextColors));
        }

        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}