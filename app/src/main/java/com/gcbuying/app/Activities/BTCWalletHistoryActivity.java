package com.gcbuying.app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gcbuying.app.Fragments.history.RecievedHistoryFragment;
import com.gcbuying.app.Fragments.history.SentHistoryFragment;
import com.gcbuying.app.Fragments.history.SoldViaGCBuyingFragment;
import com.gcbuying.app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BTCWalletHistoryActivity extends AppCompatActivity {
    Button btn_historySent;
    Button btn_historySent2;
    Button btn_historySent3;
    FrameLayout main_frames;
    RelativeLayout rlFilter;
    SentHistoryFragment sentHistoryFragment;
    RecievedHistoryFragment recievedHistoryFragment;
    SoldViaGCBuyingFragment soldViaGCBuyingFragment;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btcwallet_history);

        initViews();
        onClicks();

    }
    public void initViews()
    {
        btn_historySent = findViewById(R.id.btn_historySent);
        btn_historySent2 = findViewById(R.id.btn_historySent2);
        btn_historySent3 = findViewById(R.id.btn_historySent3);
        main_frames = findViewById(R.id.main_frames);
        rlFilter = findViewById(R.id.rlFilter);
        imgBack = findViewById(R.id.imgBack);
        sentHistoryFragment = new SentHistoryFragment();
        recievedHistoryFragment = new RecievedHistoryFragment();
        soldViaGCBuyingFragment = new SoldViaGCBuyingFragment();
        setFragment(sentHistoryFragment);

    }
    private void deliveryBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog =new  BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.btc_wallet_history_bottomdialog);
        Button btn_all = bottomSheetDialog.findViewById(R.id.btn_all);
        Button btn_completed = bottomSheetDialog.findViewById(R.id.btn_completed);
        Button btn_pending = bottomSheetDialog.findViewById(R.id.btn_pending);
        Button btn_failed = bottomSheetDialog.findViewById(R.id.btn_failed);
        RelativeLayout rlBack = bottomSheetDialog.findViewById(R.id.rlback);
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_all.setBackgroundResource(R.drawable.btn_round);
                btn_completed.setBackgroundResource(R.drawable.btn_background_edit);
                btn_pending.setBackgroundResource(R.drawable.btn_background_edit);
                btn_failed.setBackgroundResource(R.drawable.btn_background_edit);
                btn_all.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_completed.setTextColor(getResources().getColor(R.color.textcolor));
                btn_pending.setTextColor(getResources().getColor(R.color.textcolor));
                btn_failed.setTextColor(getResources().getColor(R.color.textcolor));

            }
        });
        btn_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_all.setBackgroundResource(R.drawable.btn_background_edit);
                btn_completed.setBackgroundResource(R.drawable.btn_round);
                btn_pending.setBackgroundResource(R.drawable.btn_background_edit);
                btn_failed.setBackgroundResource(R.drawable.btn_background_edit);
                btn_all.setTextColor(getResources().getColor(R.color.textcolor));
                btn_completed.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_pending.setTextColor(getResources().getColor(R.color.textcolor));
                btn_failed.setTextColor(getResources().getColor(R.color.textcolor));

            }
        });
        btn_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_all.setBackgroundResource(R.drawable.btn_background_edit);
                btn_completed.setBackgroundResource(R.drawable.btn_background_edit);
                btn_pending.setBackgroundResource(R.drawable.btn_round);
                btn_failed.setBackgroundResource(R.drawable.btn_background_edit);
                btn_all.setTextColor(getResources().getColor(R.color.textcolor));
                btn_completed.setTextColor(getResources().getColor(R.color.textcolor));
                btn_pending.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_failed.setTextColor(getResources().getColor(R.color.textcolor));

            }
        });
        btn_failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_all.setBackgroundResource(R.drawable.btn_background_edit);
                btn_completed.setBackgroundResource(R.drawable.btn_background_edit);
                btn_pending.setBackgroundResource(R.drawable.btn_background_edit);
                btn_failed.setBackgroundResource(R.drawable.btn_round);
                btn_all.setTextColor(getResources().getColor(R.color.textcolor));
                btn_completed.setTextColor(getResources().getColor(R.color.textcolor));
                btn_pending.setTextColor(getResources().getColor(R.color.textcolor));
                btn_failed.setTextColor(getResources().getColor(R.color.colorWhite));

            }
        });
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }
    public void onClicks()
    {
        btn_historySent.setBackgroundResource(R.drawable.btn_round);
        btn_historySent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(sentHistoryFragment);
                btn_historySent.setBackgroundResource(R.drawable.btn_round);
                btn_historySent2.setBackgroundResource(R.drawable.btn_background_edit);
                btn_historySent3.setBackgroundResource(R.drawable.btn_background_edit);
                btn_historySent.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_historySent2.setTextColor(getResources().getColor(R.color.textcolor));
                btn_historySent3.setTextColor(getResources().getColor(R.color.textcolor));
            }
        });
        btn_historySent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(recievedHistoryFragment);
                btn_historySent.setBackgroundResource(R.drawable.btn_background_edit);
                btn_historySent2.setBackgroundResource(R.drawable.btn_round);
                btn_historySent3.setBackgroundResource(R.drawable.btn_background_edit);
                btn_historySent.setTextColor(getResources().getColor(R.color.textcolor));
                btn_historySent2.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_historySent3.setTextColor(getResources().getColor(R.color.textcolor));

            }
        });
        btn_historySent3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(soldViaGCBuyingFragment);
                btn_historySent.setBackgroundResource(R.drawable.btn_background_edit);
                btn_historySent2.setBackgroundResource(R.drawable.btn_background_edit);
                btn_historySent3.setBackgroundResource(R.drawable.btn_round);
                btn_historySent.setTextColor(getResources().getColor(R.color.textcolor));
                btn_historySent2.setTextColor(getResources().getColor(R.color.textcolor));
                btn_historySent3.setTextColor(getResources().getColor(R.color.colorWhite));

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rlFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deliveryBottomSheetDialog();
            }
        });

    }


    private void setFragment( Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frames, fragment);
        fragmentTransaction.commit();
    }
}