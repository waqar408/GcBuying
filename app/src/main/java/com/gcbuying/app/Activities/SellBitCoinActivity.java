package com.gcbuying.app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import com.gcbuying.app.Adapters.SellHistoryAdapter;
import com.gcbuying.app.Models.SellHistoryModel;
import com.gcbuying.app.R;
import com.gcbuying.app.Server.NotInUse.MySingleton;
import com.gcbuying.app.Server.Server;
import com.gcbuying.app.utilities.Utilities;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import libs.mjn.prettydialog.PrettyDialog;

public class SellBitCoinActivity extends AppCompatActivity {


    private ImageView back_ic;
    private EditText ed_BtcAmount, ed_EstimateFee, ed_TotalAmount, ed_GetN;
    private Button btn_SellBitCoin;
    String getAmount;
    KProgressHUD hud;
    private RecyclerView sell_history_Recyclerview;
    ArrayList<SellHistoryModel> sell_history_list;
    SellHistoryAdapter sellHistoryAdapter;
    LinearLayoutManager manager;
    double getNairaAmmout;
    String bitcoin_rate;
    String lastValue;
    float amountToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_bit_coin);
        bitcoin_rate = Utilities.getString(SellBitCoinActivity.this, "bitcoin_rate");
        lastValue = Utilities.getString(SellBitCoinActivity.this, "lastValue");

        back_ic = findViewById(R.id.back_ic);
        ed_BtcAmount = findViewById(R.id.ed_btcAmount);
        ed_EstimateFee = findViewById(R.id.ed_estimateFee);
        ed_TotalAmount = findViewById(R.id.ed_totalAmount);
        ed_GetN = findViewById(R.id.ed_getN);
        btn_SellBitCoin = findViewById(R.id.btn_sellBitCoin);
        sell_history_Recyclerview = findViewById(R.id.Sell_History);

        back_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ed_BtcAmount.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getAmount = ed_BtcAmount.getText().toString();

                    double getINDoubleAmount = Double.parseDouble(getAmount);
                    if (getINDoubleAmount > 0.00001) {
                        if (!getAmount.equals("")) {
                            getNetworkFeeApi(getAmount);
                            getNairaAmmout = Double.parseDouble(bitcoin_rate) * getINDoubleAmount;
                        ed_GetN.setText(String.valueOf(getNairaAmmout));
                        } else {
                            Toast.makeText(SellBitCoinActivity.this, "Amount is Require", Toast.LENGTH_SHORT).show();
                        }
                    } else {
//                        Toast.makeText(SellBitCoinActivity.this, "Amount can't be less then 0.00001", Toast.LENGTH_SHORT).show();

                    }
                    return true;
                }
                return false;
            }
        });
        ed_BtcAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getAmount = ed_BtcAmount.getText().toString();
                if (!getAmount.equals(""))
                {
                    double getINDoubleAmount = Double.parseDouble(getAmount);
                    if (getINDoubleAmount > 0.00001) {
                        if (!getAmount.equals("")) {
                            getNairaAmmout = Double.parseDouble(bitcoin_rate) * getINDoubleAmount;
                            ed_GetN.setText(String.valueOf(getNairaAmmout));
                        } else {
                            Toast.makeText(SellBitCoinActivity.this, "Amount is Require", Toast.LENGTH_SHORT).show();
                        }
                    } else {
//                        Toast.makeText(SellBitCoinActivity.this, "Amount can't be less then 0.00001", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    ed_GetN.setText("");
                }


            }
        });

        btn_SellBitCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount_btc = ed_BtcAmount.getText().toString().trim();

                if (!amount_btc.equals("")) {
                    amountToSend = Float.parseFloat(amount_btc);
                    if (amountToSend < 0.00001) {
                        Toast.makeText(SellBitCoinActivity.this, "Amount can't be less then 0.00001", Toast.LENGTH_SHORT).show();
                    } else {
                        sellBitcoinApi(amount_btc);

                    }

                } else {
                    Toast.makeText(SellBitCoinActivity.this, "amount is required", Toast.LENGTH_SHORT).show();
                }
            }


        });
        getSellHistoryApi();
    }

    public void getSellHistoryApi() {

        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.wallet_transactions_sells, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    sell_history_list = new ArrayList<>();
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(SellBitCoinActivity.this, "Sell History found", Toast.LENGTH_SHORT).show();
                        final JSONArray objUser = object.getJSONArray("data");
                        for (int i = 0; i < objUser.length(); i++) {
//                            hud.dismiss();
                            JSONObject jsonObject = objUser.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String txid = jsonObject.getString("txid");
                            String amount_btc = jsonObject.getString("amount_btc");
                            String amount_naira = jsonObject.getString("amount_naira");
                            String status1 = jsonObject.getString("status");
                            String created_at = jsonObject.getString("created_at");
                            String updated_at = jsonObject.getString("updated_at");
                            sell_history_list.add(new SellHistoryModel(txid, amount_btc, amount_naira, status1, created_at, false));

                        }
                        sellHistoryAdapter = new SellHistoryAdapter(SellBitCoinActivity.this, sell_history_list);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SellBitCoinActivity.this, RecyclerView.VERTICAL, false);
                        sell_history_Recyclerview.setLayoutManager(linearLayoutManager);
                        sell_history_Recyclerview.setAdapter(sellHistoryAdapter);


                    } else {
//                            hud.dismiss();
                        Toast.makeText(SellBitCoinActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                hud.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                hud.dismiss();
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                if (SellBitCoinActivity.this != null)
                    Toast.makeText(SellBitCoinActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + Utilities.getString(SellBitCoinActivity.this, "access_token"));
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(SellBitCoinActivity.this).addToRequestQueue(RegistrationRequest);


    }


    public void sellBitcoinApi(final String amount) {
        hud = KProgressHUD.create(SellBitCoinActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.wallet_sell, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("success")) {
                        hud.dismiss();
//                        JSONObject data = object.getJSONObject("data");
//                        String user_id = data.getString("user_id");
//                        String txid = data.getString("txid");
//                        String amount_btc = data.getString("amount_btc");
//                        String amount_naira = data.getString("amount_naira");
//                        String status1 = data.getString("status");
//                        int id = data.getInt("id");
                        showCustomDialogforExit();
/*
                        finish();
                        Toast.makeText(SellBitCoinActivity.this, "Sold Bitcoin successfully", Toast.LENGTH_SHORT).show();
*/
                    } else {
                        if (status.equals("error")) {
                            String data = object.getString("data");
                            Toast.makeText(SellBitCoinActivity.this, "" + data, Toast.LENGTH_SHORT).show();

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
                if (SellBitCoinActivity.this != null)
                    Toast.makeText(SellBitCoinActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("amount", amount);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + Utilities.getString(SellBitCoinActivity.this, "access_token"));
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(SellBitCoinActivity.this).addToRequestQueue(RegistrationRequest);

    }

    private void getNetworkFeeApi(final String amount) {
        hud = KProgressHUD.create(SellBitCoinActivity.this)
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


                        ed_EstimateFee.setText(String.valueOf(estimate_network_fee));
                        double estimate_network_feess = estimated_network_fee + blockio_fee;
                        double amontSend1 = Double.parseDouble(getAmount);
                        double totalAmmount = estimate_network_feess + amontSend1;

                        ed_TotalAmount.setText(String.valueOf(totalAmmount));
//                        getNairaAmmout = Double.parseDouble(bitcoin_rate) * totalAmmount*Double.parseDouble(lastValue);
                        getNairaAmmout = Double.parseDouble(bitcoin_rate) * amontSend1 * Double.parseDouble(lastValue);
                        ed_GetN.setText(String.valueOf(getNairaAmmout));
                        Toast.makeText(SellBitCoinActivity.this, "Estimated fee found", Toast.LENGTH_SHORT).show();
                    } else {
                        hud.dismiss();
                        String statusfail = object.getString("status");
                        if (statusfail.equals("fail")) {
                            JSONObject data = object.getJSONObject("data");
                            Double estimated_network_fee = data.getDouble("estimated_network_fee");
                            Double blockio_fee = data.getDouble("blockio_fee");
                            double estimate_network_fee = estimated_network_fee + blockio_fee;

                            ed_EstimateFee.setText(String.valueOf(estimate_network_fee));
                            double estimate_network_feess = estimated_network_fee + blockio_fee;
                            double amontSend1 = Double.parseDouble(getAmount);
                            double totalAmmount = estimate_network_feess + amontSend1;

                            ed_TotalAmount.setText(String.valueOf(totalAmmount));
//                            getNairaAmmout = Double.parseDouble(bitcoin_rate) * totalAmmount;
                            getNairaAmmout = Double.parseDouble(bitcoin_rate) * amontSend1 * Double.parseDouble(lastValue);
//                            getNairaAmmout = Double.parseDouble(bitcoin_rate) * totalAmmount*Double.parseDouble(lastValue);

//                            getNairaAmmout = Double.parseDouble(bitcoin_rate) * amontSend1*Double.valueOf(lastValue);
                            ed_GetN.setText(String.valueOf(getNairaAmmout));
                            String error_message = data.getString("error_message");
                            Toast.makeText(SellBitCoinActivity.this, error_message, Toast.LENGTH_SHORT).show();
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
                    message = "Server error please try again laterr!";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                if (SellBitCoinActivity.this != null)
                    Toast.makeText(SellBitCoinActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("from_address", Utilities.getString(SellBitCoinActivity.this, "wallet_address"));
                params.put("amount", amount);
                params.put("address", Utilities.getString(SellBitCoinActivity.this, "bitcoin_address"));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("Accept", "application/json");
//                params.put("Authorization", "Bearer " + Utilities.getString(SellBitCoinActivity.this, "access_token"));
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(SellBitCoinActivity.this).addToRequestQueue(RegistrationRequest);


    }


    private void showCustomDialogforExit() {

        final PrettyDialog pDialog = new PrettyDialog(SellBitCoinActivity.this);
        pDialog
                .setTitle("Done!")
                .setMessage("Sold Bitcoin successfully")
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
}