package com.gcbuying.app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
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
import com.gcbuying.app.R;
import com.gcbuying.app.Server.NotInUse.MySingleton;
import com.gcbuying.app.Server.Server;
import com.gcbuying.app.utilities.Utilities;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class SendBitCoinActivity extends AppCompatActivity {

    private ImageView back_ic;
    private EditText ed_Destination_Address, ed_SendBtcamount, ed_EstimateFee, ed_TotalAmount;
    private Button btn_SendBitcoin;
    KProgressHUD hud;
    String getAddresss = "";
    String amountSend = "";
    String btcAmountStr;
    float amountToSend;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_bit_coin);

        back_ic = findViewById(R.id.back_ic);
        imgBack = findViewById(R.id.imgBack);
        ed_Destination_Address = findViewById(R.id.ed_destination_Address);
        ed_SendBtcamount = findViewById(R.id.ed_sendBtcamount);
        ed_EstimateFee = findViewById(R.id.ed_estimateFee);
        ed_TotalAmount = findViewById(R.id.ed_totalAmount);
        btn_SendBitcoin = findViewById(R.id.btn_sendBitcoin);

        imgBack.setOnClickListener(view -> finish());
        ed_Destination_Address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getAddresss = ed_Destination_Address.getText().toString();

            }
        });

        btn_SendBitcoin.setOnClickListener(view -> {

//            showCustomDialogforExit();

            String getAmount = ed_TotalAmount.getText().toString();
            String address = ed_Destination_Address.getText().toString();
            String estimatedFee = "1";
            btcAmountStr = ed_SendBtcamount.getText().toString();

            if (!address.equals("")) {
                if (!btcAmountStr.equals("")) {
                    if (!estimatedFee.equals("")) {
                        if (!getAmount.equals("")) {
                            amountToSend = Float.parseFloat(btcAmountStr);
                            if (amountToSend < 0.00001) {
                                Toast.makeText(SendBitCoinActivity.this, "Amount can't be less then 0.00001", Toast.LENGTH_SHORT).show();
                            } else {
                                // 3QSWeij46P7GmKuWLG47YQDpEDBUepRpyJ
                                sentBtcApi(btcAmountStr, address);
                            }
                        } else {
                            Toast.makeText(SendBitCoinActivity.this, "Total Amount not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(SendBitCoinActivity.this, " Estimate Fee not found ", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(SendBitCoinActivity.this, " Enter BTC value", Toast.LENGTH_SHORT).show();
                }
            } else {

                Toast.makeText(SendBitCoinActivity.this, "Enter Destination Address ", Toast.LENGTH_SHORT).show();
            }

        });

        ed_SendBtcamount.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    amountSend = ed_SendBtcamount.getText().toString();

                    if (!amountSend.equals("")) {
                        if (!getAddresss.equals("")) {
                            amountToSend = Float.parseFloat(amountSend);
                            if (amountToSend < 0.00001) {
                                Toast.makeText(SendBitCoinActivity.this, "Amount can't be less then 0.00001", Toast.LENGTH_SHORT).show();
                            } else {
                                getNetworkFeeApi(amountSend, getAddresss);
                            }
                        } else {
                            Toast.makeText(SendBitCoinActivity.this, "Destination Address is Require", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SendBitCoinActivity.this, "Amount is Require", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void showCustomDialogforExit() {

        final PrettyDialog pDialog = new PrettyDialog(SendBitCoinActivity.this);
        pDialog
                .setTitle("Done!")
                .setMessage("Sent Bitcoin successfully")
                .setIcon(R.drawable.checked)
//                .setIconTint(R.color.greenSucess)

//                .setIconTint(R.color.colorPrimary)
                /* .addButton(
                         "Ok",
                         R.color.colorPrimary,
                         R.color.pdlg_color_white,
                         new PrettyDialogCallback() {
                             @Override
                             public void onClick() {
                                 finishAffinity();
                                 pDialog.dismiss();
                             }
                         }
                 )
                 .addButton("No",
                         R.color.pdlg_color_red,
                         R.color.pdlg_color_white,
                         new PrettyDialogCallback() {
                             @Override
                             public void onClick() {
                                 pDialog.dismiss();
                             }
                         })*/
                .show();

        Thread myThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
/*

                    Intent intent = new Intent(getApplicationContext(), ContinueAs.class);
                    startActivity(intent);
*/
                    pDialog.dismiss();
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }


    private void getNetworkFeeApi(final String amount, final String to_address) {
        hud = KProgressHUD.create(SendBitCoinActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.get_network_fee, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
//                    String message = object.getString("message");
//                    int  cart_counter = object.getInt("cart_counter");
                    if (status.equals("success")) {
                        hud.dismiss();
                        JSONObject data = object.getJSONObject("data");
                        Double estimated_network_fee = data.getDouble("estimated_network_fee");
                        Double blockio_fee = data.getDouble("blockio_fee");
                        double estimate_network_fee = estimated_network_fee + blockio_fee;
                        double estimate_network_feess = estimated_network_fee + blockio_fee;
                        double amontSend1 = Double.parseDouble(amountSend);
                        double totalAmmount = estimate_network_feess + amontSend1;
                        ed_TotalAmount.setText(String.valueOf(totalAmmount));
                        ed_EstimateFee.setText(String.valueOf(estimate_network_fee));
                        Toast.makeText(SendBitCoinActivity.this, "Estimated fee found", Toast.LENGTH_SHORT).show();
                    } else {
                        hud.dismiss();
                        String statusfail = object.getString("status");
                        if (statusfail.equals("fail")) {
                            JSONObject data = object.getJSONObject("data");
//
                            Double estimated_network_fee = data.getDouble("estimated_network_fee");
                            Double blockio_fee = data.getDouble("blockio_fee");
                            double estimate_network_feess = estimated_network_fee + blockio_fee;
                            ed_EstimateFee.setText(String.valueOf(estimate_network_feess));
                            double amontSend1 = Double.parseDouble(amountSend);
                            double totalAmmount = estimate_network_feess + amontSend1;
                            ed_TotalAmount.setText(String.valueOf(totalAmmount));
                            String error_message = data.getString("error_message");
                            Toast.makeText(SendBitCoinActivity.this, error_message, Toast.LENGTH_SHORT).show();
                        }
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
                if (SendBitCoinActivity.this != null)
                    Toast.makeText(SendBitCoinActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("from_address", Utilities.getString(SendBitCoinActivity.this, "wallet_address"));
                params.put("amount", amount);
                params.put("address", to_address);
//

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("Accept", "application/json");
//                params.put("Authorization", "Bearer " + Utilities.getString(SendBitCoinActivity.this, "access_token"));
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(SendBitCoinActivity.this).addToRequestQueue(RegistrationRequest);


    }

    private void sentBtcApi(final String amount, final String to_address) {
        hud = KProgressHUD.create(SendBitCoinActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.wallet_sent, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
//                    String message = object.getString("message");
//                    int  cart_counter = object.getInt("cart_counter");
                    if (status.equals("success")) {
                        hud.dismiss();
                        JSONObject data = object.getJSONObject("data");
                        String network = data.getString("network");
                        String txid = data.getString("txid");
                        String amount_withdrawn = data.getString("amount_withdrawn");
                        String amount_sent = data.getString("amount_sent");
                        String network_fee = data.getString("network_fee");
                        String blockio_fee = data.getString("blockio_fee");
                        showCustomDialogforExit();
//                        Toast.makeText(SendBitCoinActivity.this, "Send Bitcoin successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        String status1 = object.getString("status");
                        if (status1.equals("fail")) {
                            JSONObject data = object.getJSONObject("data");
                            String error_message = data.getString("error_message");
                            Toast.makeText(SendBitCoinActivity.this, error_message, Toast.LENGTH_SHORT).show();

                        }
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
                if (SendBitCoinActivity.this != null)
                    Toast.makeText(SendBitCoinActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("amount", amount);
                params.put("address", to_address);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + Utilities.getString(SendBitCoinActivity.this, "access_token"));
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(SendBitCoinActivity.this).addToRequestQueue(RegistrationRequest);


    }
}