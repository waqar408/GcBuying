package com.gcbuying.app.PopFragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.gcbuying.app.R;
import com.gcbuying.app.utilities.Utilities;
import com.kaopiz.kprogresshud.KProgressHUD;

import static android.content.Context.CLIPBOARD_SERVICE;

public class ReceivingAddressFragment extends DialogFragment {
    View view;
    KProgressHUD hud;
    EditText edAddress;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    ImageView btn_Copy;
    ImageView ivBack;
    Button btn_sendBitcoin;

    public ReceivingAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_receiving_address, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        String wallet_address = Utilities.getString(getActivity(), "wallet_address");
        edAddress = view.findViewById(R.id.edAddress);
        btn_Copy = view.findViewById(R.id.btn_Copy);
        ivBack = view.findViewById(R.id.ivBack);
        btn_sendBitcoin = view.findViewById(R.id.btn_sendBitcoin);
        edAddress.setText(wallet_address);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        btn_sendBitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        btn_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                String text;
                text = Utilities.getString(getActivity(), "wallet_address");
                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(getActivity(), "Text Copied", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}