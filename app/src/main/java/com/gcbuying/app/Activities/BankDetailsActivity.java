package com.gcbuying.app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.gcbuying.app.PopFragments.ChangeBankDeatilsFragment;
import com.gcbuying.app.R;
import com.gcbuying.app.Server.NotInUse.MySingleton;
import com.gcbuying.app.Server.Server;
import com.gcbuying.app.utilities.Utilities;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BankDetailsActivity extends AppCompatActivity implements   ChangeBankDeatilsFragment.ItemClickListener, ChangeBankDeatilsFragment.OnCompleteListener {

    private ImageView back_ic;
    private TextView tv_bankname, tv_accountName, tv_account_Number;
    private Button btn_UpdatebankDetails;
    KProgressHUD hud;
    int user_id;
    ImageView imgBack;
    String accessToken = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);
        user_id = Utilities.getInt(BankDetailsActivity.this, "user_id");
        accessToken = Utilities.getString(BankDetailsActivity.this, "access_token");
        back_ic = findViewById(R.id.back_ic);
        tv_bankname = findViewById(R.id.text_bankname);
        tv_accountName = findViewById(R.id.text_accountName);
        imgBack = findViewById(R.id.imgBack);
        tv_account_Number = findViewById(R.id.text_account_Number);
        btn_UpdatebankDetails = findViewById(R.id.btn_updatebankDetails);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_UpdatebankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChangeBankDeatilsFragment newRespond = new ChangeBankDeatilsFragment();
                newRespond.show(getSupportFragmentManager(), "fragment");


            }
        });
        getBandDetailApi();
    }

    private void getBandDetailApi() {
        hud = KProgressHUD.create(BankDetailsActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.bank, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
//                    String message = object.getString("message");
//                    int  cart_counter = object.getInt("cart_counter");
                    if (status==200) {
//                        String msg = object.getString("message");
//                        Toast.makeText(BankDetailsActivity.this,status, Toast.LENGTH_SHORT).show();
                        JSONObject data = object.getJSONObject("data");
                            String bank_name = data.getString("bank_name");
                            int id = data.getInt("id");
                            String account_name = data.getString("account_name");
                            String account_no = data.getString("account_no");
                            tv_account_Number.setText(account_no);
                            tv_accountName.setText(account_name);
                            tv_bankname.setText(bank_name);
                            Utilities.saveString(BankDetailsActivity.this, "account_name", account_name);
                            Utilities.saveString(BankDetailsActivity.this, "account_no", account_no);
                            Utilities.saveString(BankDetailsActivity.this, "bank_name", bank_name);

//
//                        }
                        hud.dismiss();;
                    } else {
//                        Toast.makeText(BankDetailsActivity.this, status, Toast.LENGTH_SHORT).show();
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
                if (BankDetailsActivity.this != null)
                    Toast.makeText(BankDetailsActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("user_id", String.valueOf(user_id));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + Utilities.getString(BankDetailsActivity.this,"access_token"));
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(BankDetailsActivity.this).addToRequestQueue(RegistrationRequest);


    }

    @Override
    public void onItemClick(String item) {

    }

    @Override
    public void onComplete(String time) {

    }
//    public void getBandDetailApi() {
//        hud = KProgressHUD.create(BankDetailsActivity.this)
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("Please wait")
//                .setCancellable(true)
//                .setAnimationSpeed(2)
//                .setDimAmount(0.5f)
//                .show();
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("user_id", String.valueOf(user_id));
//        HashMap<String, String> headers = new HashMap<String, String>();
//        headers.put("Accept", "application/json");
//
//
//        ApiModelClass.GetApiResponse(Request.Method.POST, Server.my_bank_account_details, BankDetailsActivity.this, params, headers, new ServerCallback() {
//            @Override
//            public void onSuccess(JSONObject result, String ERROR) {
//
//                if (ERROR.isEmpty()) {
//
//                    hud.dismiss();
//
//                    try {
//                        JSONObject object = new JSONObject(String.valueOf(result));
//
//                        int status = object.getInt("status");
//                        String message = object.getString("message");
//                        if (status == 200) {
//                            String msg = object.getString("message");
//                            JSONArray data = object.getJSONArray("data");
//                            for (int i = 0; i < data.length(); i++) {
//                                JSONObject jsonObject = data.getJSONObject(i);
//                                String bank_name = jsonObject.getString("bank_name");
//                                int id = jsonObject.getInt("id");
//                                String account_name = jsonObject.getString("account_name");
//                                String account_no = jsonObject.getString("account_no");
//                                tv_account_Number.setText(account_no);
//                                tv_accountName.setText(account_name);
//                                tv_bankname.setText(bank_name);
//
//                            }
//                            hud.dismiss();
//                            Toast.makeText(BankDetailsActivity.this, msg, Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(BankDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                } else {
//                    hud.dismiss();
//                    Toast.makeText(BankDetailsActivity.this, ERROR, Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });
//
//    }

}