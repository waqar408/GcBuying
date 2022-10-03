package com.gcbuying.app.Fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.gcbuying.app.Activities.BTCWalletHistoryActivity;
import com.gcbuying.app.Activities.SellBitCoinActivity;
import com.gcbuying.app.Activities.SendBitCoinActivity;
import com.gcbuying.app.Adapters.ReceivedHistoryAdapter;
import com.gcbuying.app.Adapters.SendHistoryAdapter;
import com.gcbuying.app.Models.ReceivedHistoryModel;
import com.gcbuying.app.Models.SendHistoryModel;
import com.gcbuying.app.PopFragments.ReceivingAddressFragment;
import com.gcbuying.app.R;
import com.gcbuying.app.Server.NotInUse.MySingleton;
import com.gcbuying.app.Server.Server;
import com.gcbuying.app.utilities.Utilities;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.CLIPBOARD_SERVICE;


public class BtcWalletFragment extends Fragment {



    TextView tvBalance;
    LinearLayout btn_sendBitcoin,btn_ReceiveBitcoin,btn_sellBitCoin;
    private RecyclerView received_history_Recyclerview;
    ArrayList<ReceivedHistoryModel> received_history_list;
    ReceivedHistoryAdapter receivedHistoryAdapter;
    LinearLayoutManager manager;
    EditText etBitCoinAddress;
    KProgressHUD hud;
    ImageView btn_Copy,imgHistory;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private RecyclerView send_history_Recyclerview;
    ArrayList<SendHistoryModel> send_history_list;
    SendHistoryAdapter sendHistoryAdapter;
    LinearLayoutManager sendmanager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_btc_wallet, container, false);

        btn_sendBitcoin = v.findViewById(R.id.btn_sendBitcoin);
        btn_sellBitCoin = v.findViewById(R.id.btn_sellBitCoin);
        btn_ReceiveBitcoin = v.findViewById(R.id.btn_receiveBitcoin);
        received_history_Recyclerview = v.findViewById(R.id.Received_History);
        send_history_Recyclerview = v.findViewById(R.id.Send_History);
        imgHistory = v.findViewById(R.id.imgHistory);
        btn_Copy = v.findViewById(R.id.btn_Copy);
        etBitCoinAddress = v.findViewById(R.id.etBitCoinAddress);
        tvBalance = v.findViewById(R.id.tvBalance);
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
        btn_sendBitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SendBitCoinActivity.class));
            }
        });
        imgHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BTCWalletHistoryActivity.class));
            }
        });

        btn_sellBitCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getActivity(), SellBitCoinActivity.class));

            }
        });

        btn_ReceiveBitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getFragmentManager();
                ReceivingAddressFragment newFragment = new ReceivingAddressFragment();
                newFragment.show(fm, "abc");

            }
        });
        bitCoinAddress();
        bitCoinBalance();
        getHistoryApi();
        getSentHistoryApi();
        return v;
    }

    private void bitCoinAddress() {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.wallet_btc_address, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
//                    int status = object.getInt("status");
//                    if (status == 200) {
//                        JSONObject obj = object.getJSONObject("data");
//                        int id = obj.getInt("id");
//                        String rate = obj.getString("rate");
                    String address = object.getString("address");
                    Utilities.saveString(getActivity(), "wallet_address", address);
                    Toast.makeText(getActivity(), "wallet Address found", Toast.LENGTH_SHORT).show();
//
                    etBitCoinAddress.setText(address);
//
//                    } else {
//                        Toast.makeText(getActivity(), "error with address ", Toast.LENGTH_SHORT).show();
//
//                    }
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
                    message = "Invalid Credential";
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
//                params.put("email", email);
//                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + Utilities.getString(getActivity(), "access_token"));
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(getActivity()).addToRequestQueue(RegistrationRequest);


    }

    private void bitCoinBalance() {


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.check_balance, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
//                    String status = object.getString("status");
//                    if (status.equals("success")) {
//                        JSONObject obj = object.getJSONObject("data");
//                        Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                    int user_id = object.getInt("user_id");
                    String address = object.getString("address");
                    String available_balance = object.getString("available_balance");
                    Toast.makeText(getContext(), "Wallet Balance Found", Toast.LENGTH_SHORT).show();
                    Utilities.saveString(getContext(), "wallet_balance", available_balance);

                    tvBalance.setText(available_balance + " BTC");

//                    } else {
//                        Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
//
//                    }
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
                    message = "Server Error please try again later:";
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
//                params.put("email", email);
//                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + Utilities.getString(getActivity(), "access_token"));
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(getActivity()).addToRequestQueue(RegistrationRequest);


    }

    private void getHistoryApi() {

        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.wallet_transaction_recieved, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    received_history_list = new ArrayList<>();
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("success")) {
                        final JSONObject objUser = object.getJSONObject("data");
                        String network = objUser.getString("network");

//                        Toast.makeText(getActivity(), "list found", Toast.LENGTH_SHORT).show();
                        JSONArray txs = objUser.getJSONArray("txs");
//                        hud.dismiss();
                        for (int i = 0; i < txs.length(); i++) {
//                            hud.dismiss();
                            JSONObject jsonObject = txs.getJSONObject(i);
                            String time = jsonObject.getString("time");
                            String id = jsonObject.getString("txid");
                            String confirmations = jsonObject.getString("confirmations");
                            JSONArray amounts_received = jsonObject.getJSONArray("amounts_received");
                            for (int j = 0; j < amounts_received.length(); j++) {
                                JSONObject amounts_received_object = amounts_received.getJSONObject(j);
                                String recipient = amounts_received_object.getString("recipient");
                                String amount = amounts_received_object.getString("amount");
                                received_history_list.add(new ReceivedHistoryModel(id, amount, recipient, confirmations, time, false));

                            }
                        }
                        receivedHistoryAdapter = new ReceivedHistoryAdapter(getActivity(), received_history_list);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                        received_history_Recyclerview.setLayoutManager(linearLayoutManager);
                        received_history_Recyclerview.setAdapter(receivedHistoryAdapter);


                    } else {
//                            hud.dismiss();
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
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
                if (getActivity() != null)
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
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
//                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + Utilities.getString(getActivity(), "access_token"));
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(getActivity()).addToRequestQueue(RegistrationRequest);


    }

    private void getSentHistoryApi() {

        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.wallet_transactions_sent, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    send_history_list = new ArrayList<>();
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("success")) {
                        final JSONObject objUser = object.getJSONObject("data");
                        String network = objUser.getString("network");

//                        Toast.makeText(getActivity(), "Sending History found", Toast.LENGTH_SHORT).show();
                        JSONArray txs = objUser.getJSONArray("txs");
//                        hud.dismiss();
                        for (int i = 0; i < txs.length(); i++) {
//                            hud.dismiss();
                            JSONObject jsonObject = txs.getJSONObject(i);
                            String time = jsonObject.getString("time");
                            String id = jsonObject.getString("txid");
                            int confirmations = jsonObject.getInt("confirmations");
                            String total_amount_sent = jsonObject.getString("total_amount_sent");
                            JSONArray amounts_sent = jsonObject.getJSONArray("amounts_sent");
                            JSONArray senders = jsonObject.getJSONArray("senders");
                            for (int j = 0; j < amounts_sent.length(); j++) {
                                JSONObject amount_Sent_obj = amounts_sent.getJSONObject(j);
                                String amount = amount_Sent_obj.getString("amount");
                                send_history_list.add(new SendHistoryModel(id, amount, String.valueOf(senders), confirmations, time, false));

                            }
                        }
                        sendHistoryAdapter = new SendHistoryAdapter(getActivity(), send_history_list);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                        send_history_Recyclerview.setLayoutManager(linearLayoutManager);
                        send_history_Recyclerview.setAdapter(sendHistoryAdapter);


                    } else {
//                            hud.dismiss();
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
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
                if (getActivity() != null)
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
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
//                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + Utilities.getString(getActivity(), "access_token"));
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