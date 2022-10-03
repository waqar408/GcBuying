package com.gcbuying.app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.gcbuying.app.Adapters.RechargeAdapter;
import com.gcbuying.app.Models.RechargeModel;
import com.gcbuying.app.Models.SelectOperatorModel;
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
import java.util.List;
import java.util.Map;

public class RechargePhoneActivity extends AppCompatActivity {

    private String CardTypeArray[] = {"Visa Card", "Master Card", "Gold Card", "Silver Card"};
    private ArrayList<SelectOperatorModel> selectOperatorModels;
    private ImageView back_ic;
    private EditText ed_Phonenumber, ed_UsdAmount, ed_UsdAmountWithVAT, ed_NTotalAmount, ed_BtcAmount, ed_PayToid, ed_Note;
    private Button btn_RechargePhone;
    private Spinner CardType;
    private ImageView btn_Copyid;
    KProgressHUD hud;
    ImageView imgBack;
    String usdAmount;
    int operators_id;
    private RecyclerView rechargeRecyclerview;
    ArrayList<RechargeModel> rechargelist;
    RechargeAdapter adapter;
    LinearLayoutManager manager;
    List<String> operators;
    String bitcoin_address, btcInOneDollar, bitcoin_rate;
    String operaterRate;
    double getNairaAmmout, getBTCAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_phone);
        bitcoin_address = Utilities.getString(RechargePhoneActivity.this, "bitcoin_address");
        btcInOneDollar = Utilities.getString(RechargePhoneActivity.this, "btcInOneDollar");
        bitcoin_rate = Utilities.getString(RechargePhoneActivity.this, "bitcoin_rate");
        back_ic = findViewById(R.id.back_ic);
        ed_Phonenumber = findViewById(R.id.ed_phonenumber);
        ed_UsdAmount = findViewById(R.id.ed_usdAmount);
        ed_UsdAmountWithVAT = findViewById(R.id.ed_usdAmountVAT);
        ed_NTotalAmount = findViewById(R.id.ed_nTotalAmount);
        ed_BtcAmount = findViewById(R.id.ed_btcAmount);
        ed_PayToid = findViewById(R.id.ed_id);
        ed_Note = findViewById(R.id.ed_note);
        imgBack = findViewById(R.id.imgBack);
        btn_RechargePhone = findViewById(R.id.btn_btnrecharge);
        CardType = findViewById(R.id.card_Spinner);
        btn_Copyid = findViewById(R.id.btn_Copy);
        rechargeRecyclerview = findViewById(R.id.Recharge_Phone);
        ed_PayToid.setText(bitcoin_address);
        ed_UsdAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                usdAmount = ed_UsdAmount.getText().toString();

                if (TextUtils.isEmpty(usdAmount)) {
                    ed_NTotalAmount.setText("0");
                    ed_BtcAmount.setText("0");
                    return;
                } else {

                    getNairaAmmout = Double.parseDouble(bitcoin_rate) * Double.parseDouble(usdAmount);

                    ed_NTotalAmount.setText(String.valueOf(getNairaAmmout));
                    double vatCalcaulation = Double.parseDouble(usdAmount) * 0.15;
                    double vatAmount = Double.parseDouble(ed_UsdAmount.getText().toString()) - (vatCalcaulation);
                    getBTCAmount = Double.parseDouble(btcInOneDollar) * vatAmount;
                    ed_UsdAmountWithVAT.setText(String.valueOf(vatAmount));

                    String scientificNotation = String.valueOf(getBTCAmount);
                    Double scientificDouble = Double.parseDouble(scientificNotation);
                    NumberFormat nf = new DecimalFormat("################################################.###########################################");
                    String decimalString_getBTCAmount = nf.format(scientificDouble);

                    ed_BtcAmount.setText(String.valueOf(decimalString_getBTCAmount));

                }

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_RechargePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getPhone = ed_Phonenumber.getText().toString();
                String getUSD_ammount = ed_UsdAmount.getText().toString();
                String getTotalAmmount = ed_NTotalAmount.getText().toString();
                String get_BTC_amount = ed_BtcAmount.getText().toString();
                String note = ed_Note.getText().toString();
                createRechargeOrderApi(getUSD_ammount, getPhone);
            }
        });
        getOperatorApi();
        getRecentRechargeApi();
    }

    private void getOperatorApi() {


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.recharges_operators, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    selectOperatorModels = new ArrayList<SelectOperatorModel>();
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 200) {
                        operators = new ArrayList<>();
                        Toast.makeText(RechargePhoneActivity.this, "operaters list found", Toast.LENGTH_SHORT).show();
                        JSONObject jsonObject;
                        JSONArray objUser = object.getJSONArray("data");
                        for (int i = 0; i < objUser.length(); i++) {
                            jsonObject = objUser.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            String rate = jsonObject.getString("rate");
                            selectOperatorModels.add(new SelectOperatorModel(id, name, rate));
                            operators.add(name);

                        }
                        // Creating adapter for spinner
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RechargePhoneActivity.this, android.R.layout.simple_spinner_item, operators);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        CardType.setAdapter(dataAdapter);
                        operators.add(0,"Select Your Operator");
                        CardType.setSelection(0);
                        CardType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String get_area_type = parent.getItemAtPosition(position).toString();
                                operators_id = selectOperatorModels.get(position).getId();
                                operaterRate = selectOperatorModels.get(position).getRate();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

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
                if (RechargePhoneActivity.this != null)
                    Toast.makeText(RechargePhoneActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("token", token);
//                params.put("do", "area_units");
//                params.put("apikey", "travces.com");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
//                params.put("Authorization", "Bearer " + accessToken);
                return params;
            }
        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(RechargePhoneActivity.this).addToRequestQueue(RegistrationRequest);


    }

    private void getRecentRechargeApi() {

        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.recharges_orders, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    rechargelist = new ArrayList<>();
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 200) {
                        final JSONArray objUser = object.getJSONArray("data");
                        Toast.makeText(RechargePhoneActivity.this, "recharge order found", Toast.LENGTH_SHORT).show();
//                        hud.dismiss();
                        for (int i = 0; i < objUser.length(); i++) {
//                            hud.dismiss();
                            JSONObject jsonObject = objUser.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            int user_id = jsonObject.getInt("user_id");
                            String status1 = jsonObject.getString("status");
                            String amount_naira = jsonObject.getString("amount_naira");
                            String amount_usd = jsonObject.getString("amount_usd");
                            String phone = jsonObject.getString("phone");
                            String operator_id = jsonObject.getString("operator_id");
                            String amount_btc = jsonObject.getString("amount_btc");
                            String txid = jsonObject.getString("txid");
                            String note = jsonObject.getString("note");
                            String created_at = jsonObject.getString("created_at");
                            String updated_at = jsonObject.getString("updated_at");
                            rechargelist.add(new RechargeModel(id, "bank", phone, amount_naira, amount_btc, amount_usd, status1, created_at, updated_at, "action_show"));

                        }
                        adapter = new RechargeAdapter(RechargePhoneActivity.this, rechargelist);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RechargePhoneActivity.this, RecyclerView.VERTICAL, false);
                        rechargeRecyclerview.setLayoutManager(linearLayoutManager);
                        rechargeRecyclerview.setAdapter(adapter);


                    } else {
                        Toast.makeText(RechargePhoneActivity.this, "recharge list not found", Toast.LENGTH_SHORT).show();

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
                if (RechargePhoneActivity.this != null)
                    Toast.makeText(RechargePhoneActivity.this, message, Toast.LENGTH_LONG).show();
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
                params.put("Authorization", "Bearer " + Utilities.getString(RechargePhoneActivity.this, "access_token"));
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(RechargePhoneActivity.this).addToRequestQueue(RegistrationRequest);


    }


    private void createRechargeOrderApi(final String amount_usd, final String phone) {
        hud = KProgressHUD.create(RechargePhoneActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.create_recharge, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 200) {
                        hud.dismiss();
//                        final JSONObject objUser = object.getJSONObject("data");
                        getRecentRechargeApi();
                        Toast.makeText(RechargePhoneActivity.this, "Recharge Successfully ", Toast.LENGTH_SHORT).show();

                    } else {
                        hud.dismiss();
                        Toast.makeText(RechargePhoneActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
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
                if (RechargePhoneActivity.this != null)
                    Toast.makeText(RechargePhoneActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("operator_id", String.valueOf(operators_id));
                params.put("amount_naira", String.valueOf(getNairaAmmout));
                params.put("amount_usd", amount_usd);
                params.put("amount_btc", String.valueOf(getBTCAmount));
                params.put("phone", phone);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + Utilities.getString(RechargePhoneActivity.this, "access_token"));
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(RechargePhoneActivity.this).addToRequestQueue(RegistrationRequest);


    }
}