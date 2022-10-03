package com.gcbuying.app.PopFragments;

import android.app.FragmentManager;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gcbuying.app.Activities.BankDetailsActivity;
import com.gcbuying.app.R;
import com.gcbuying.app.Server.NotInUse.MySingleton;
import com.gcbuying.app.Server.Server;
import com.gcbuying.app.utilities.Utilities;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ChangeBankDeatilsFragment extends DialogFragment {
    KProgressHUD hud;
    ImageView ivBack;
    private EditText ed_BankName, ed_AccountName, ed_AccountNumber;
    private Button btn_updatebankDetails;
    String accessToken = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_bank_deatils, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        String account_name = Utilities.getString(getContext(), "account_name");
        String account_no = Utilities.getString(getContext(), "account_no");
        String bank_name = Utilities.getString(getContext(), "bank_name");
        accessToken = Utilities.getString(getActivity(), "access_token");
        ed_BankName = v.findViewById(R.id.ed_bankName);
        ed_AccountName = v.findViewById(R.id.ed_accountName);
        ed_AccountNumber = v.findViewById(R.id.ed_accountNumber);
        btn_updatebankDetails = v.findViewById(R.id.btn_updatebankDetails);
        ed_AccountName.setText(account_name);
        ed_AccountNumber.setText(account_no);
        ed_BankName.setText(bank_name);
        ivBack = v.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        btn_updatebankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getABandName = ed_BankName.getText().toString();
                String getAccName = ed_AccountName.getText().toString();
                String getAccNo = ed_AccountNumber.getText().toString();

                if (!getABandName.isEmpty()) {
                    if (!getAccName.isEmpty()) {
                        if (!getAccNo.isEmpty()) {

                            updateBankDetailApi(getAccName, getAccNo, getABandName);

                        } else {
                            Toast.makeText(getActivity(), "Account Number Required", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Account Name Required", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), " Bank Name Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    public interface ItemClickListener {
        void onItemClick(String item);
    }

    public static interface OnCompleteListener {
        public abstract void onComplete(String time);
    }

    public void show(FragmentManager manager) {
    }

    private void updateBankDetailApi(final String account_name, final String account_no, final String bank_name) {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.bank_update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
//                    String message = object.getString("message");
//                    int  cart_counter = object.getInt("cart_counter");
                    if (status == 200) {
                        hud.dismiss();
                        Toast.makeText(getActivity(), "Bank Detail Updated Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), BankDetailsActivity.class));
                        getActivity().finish();
                        dismiss();
                    } else {
                        hud.dismiss();
                        Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hud.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hud.dismiss();
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "Server error please try again later";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                if (getActivity() != null)
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(Utilities.getInt(getActivity(), "user_id")));
                params.put("account_name", account_name);
                params.put("bank_name", bank_name);
                params.put("account_no", account_no);
//

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + accessToken);
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(getActivity()).addToRequestQueue(RegistrationRequest);


    }

}