package com.gcbuying.app.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.gcbuying.app.Activities.BankDetailsActivity;
import com.gcbuying.app.Activities.RechargePhoneActivity;
import com.gcbuying.app.Activities.SellGiftActivity;
import com.gcbuying.app.Activities.TradeBitCoinActivity;
import com.gcbuying.app.Adapters.AnnouncementAdapter;
import com.gcbuying.app.Adapters.CardRatesAdapter;
import com.gcbuying.app.Models.CardRatesModel;
import com.gcbuying.app.Models.GetAnouncementModel;
import com.gcbuying.app.R;
import com.gcbuying.app.Server.NotInUse.MySingleton;
import com.gcbuying.app.Server.Server;
import com.gcbuying.app.api.ApiModelClass;
import com.gcbuying.app.api.ServerCallback;
import com.gcbuying.app.utilities.Utilities;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {


    private Button btn_SendGiftCard, btn_SellBitCoin;
    TextView tvBalance;
    private RecyclerView cardRatesRecyclerview;
    private CardRatesAdapter adapter;
    ArrayList<CardRatesModel> cardRatesModels;
    LinearLayoutManager manager;
    RelativeLayout btn_PhoneRecharge,btn_BankDetails;
    RelativeLayout llSellGift;
    RelativeLayout sel_bit;
    private ArrayList<GetAnouncementModel> getAnouncementModels;
    ArrayList<String> text;
    AnnouncementAdapter adpter;
    KProgressHUD hud;
    String accessToken = "";
    SwipeRefreshLayout mSwipeRefreshLayout;
    ImageView imgHide;
    ImageView imgShow;
    ViewPager viewPager;
    String balance = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        accessToken = Utilities.getString(getActivity(), "access_token");
        btn_SendGiftCard = v.findViewById(R.id.btn_sendGiftCard);
        btn_SellBitCoin = v.findViewById(R.id.btn_sellBitCoin);
        llSellGift = v.findViewById(R.id.llSellGift);
        sel_bit = v.findViewById(R.id.sel_bit);
        tvBalance = v.findViewById(R.id.tvBalance);
        viewPager = v.findViewById(R.id.viewPager);
        btn_BankDetails = v.findViewById(R.id.btn_bankDetails);
        btn_PhoneRecharge = v.findViewById(R.id.btn_phoneRecharge);
        imgHide = v.findViewById(R.id.imgHide);
        imgShow = v.findViewById(R.id.imgShow);
//        mSwipeRefreshLayout = v.findViewById(R.id.mSwipeRefreshLayout);
        cardRatesRecyclerview = v.findViewById(R.id.recyclerview);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
        loginApi();
        btn_SendGiftCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SellGiftActivity.class));
            }
        });

        llSellGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SellGiftActivity.class));

            }
        });
        btn_SellBitCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TradeBitCoinActivity.class));

            }
        });
        sel_bit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TradeBitCoinActivity.class));
            }
        });

        btn_BankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), BankDetailsActivity.class));
            }
        });

        btn_PhoneRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RechargePhoneActivity.class));

            }
        });
        imgHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgShow.setVisibility(View.VISIBLE);
                imgHide.setVisibility(View.GONE);
                tvBalance.setText("NGN "+balance);
            }
        });

        imgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgShow.setVisibility(View.GONE);
                imgHide.setVisibility(View.VISIBLE);
                tvBalance.setText("********");
            }
        });
        blockChainApi();
        getCartRatesApi();
        authMeApi();
        bitCoinAddress();
//        refreshToeknApi();
        viewPager = v.findViewById(R.id.ViewPager);
        text = new ArrayList<>();
        text.add(getString(R.string.if_the_application_is));
        text.add("Minimum accepted trade is $20");
        text.add("You now have a custom GCBUYING bitcoin wallet for sending and receiving of bitcoins. You can now buy gift cards with bitcoins and also recharge your airtime balance.");


        adpter = new AnnouncementAdapter(getContext(), text);
        viewPager.setPadding(0, 0, 0, 0);
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(adpter);

        return v;
    }

    private void getCartRatesApi() {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.sell_cards, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    cardRatesModels = new ArrayList<>();
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 200) {
                        hud.dismiss();
                        final JSONArray objUser = object.getJSONArray("data");
//                        Toast.makeText(getActivity(), "list found", Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < objUser.length(); i++) {

                            JSONObject jsonObject = objUser.getJSONObject(i);
                            String image = jsonObject.getString("image");
                            int id = jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            String rate = jsonObject.getString("rate");
                            cardRatesModels.add(new CardRatesModel(id, name, rate, image));

                        }
                        adapter = new CardRatesAdapter(getActivity(), cardRatesModels);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                        cardRatesRecyclerview.setLayoutManager(linearLayoutManager);
                        cardRatesRecyclerview.setAdapter(adapter);


                    } else {
                        hud.dismiss();
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
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
                if (getActivity() != null)
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//no params
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


    private void authMeApi() {
//        hud = KProgressHUD.create(getActivity())
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("Please wait")
//                .setCancellable(true)
//                .setAnimationSpeed(2)
//                .setDimAmount(0.5f)
//                .show();


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, "https://gcbuying.com/api/auth/me", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
//                    int  cart_counter = object.getInt("cart_counter");
                    if (status == 200) {

                        JSONObject obj = object.getJSONObject("data");
                        int id = obj.getInt("id");
                        String name = obj.getString("name");
                        String email = obj.getString("email");
                        String phone = obj.getString("phone");
                        balance = obj.getString("balance");
//                        Toast.makeText(getActivity(), "auth success", Toast.LENGTH_SHORT).show();
                        Utilities.saveString(getActivity(), "user_name", name);
                        Utilities.saveString(getActivity(), "user_email", email);
                        Utilities.saveString(getActivity(), "user_phone", phone);
                        Utilities.saveString(getActivity(), "user_phone", phone);
                        Utilities.saveString(getActivity(), "naira_balance", balance);
                        Utilities.saveInt(getActivity(), "user_id", id);

                        tvBalance.setText("NGN "+balance);
                    } else {
                        Toast.makeText(getActivity(), "balance not fount", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hud.dismiss();
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "Server can not ne found please try again later";
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
//                params.put("name","abdul");
//                params.put("email","abdulrehman.as007@gmail.com");
//                params.put("password","12345678");
//                params.put("phone","23435467");
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

//    @Override
//    public void onRefresh() {
//        new Handler().postDelayed(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void run() {
//                onRefreshfrag();
//                mSwipeRefreshLayout.setRefreshing(true);
//            }
//        }, 1000);
//
////        Toast.makeText(context, "jdfjdf", Toast.LENGTH_SHORT).show();
//    }

//    public void onRefreshfrag() {
//        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
//
//    }

    private void refreshToeknApi() {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.refresh, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
//                    String message = object.getString("message");
//                    int  cart_counter = object.getInt("cart_counter");
                    if (status == 200) {
                        JSONObject obj = object.getJSONObject("data");
                        String access_token = obj.getString("access_token");
                        String token_type = obj.getString("token_type");
                        int expires_in = obj.getInt("expires_in");
                        Utilities.saveString(getActivity(), "access_token", access_token);
//                        Toast.makeText(getActivity(), "Token Refresh Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "token not refreshed", Toast.LENGTH_SHORT).show();

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
                params.put("Authorization", "Bearer" + accessToken);
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(getActivity()).addToRequestQueue(RegistrationRequest);


    }

    private void blockChainApi() {

        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, "https://blockchain.info/ticker", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject obj = object.getJSONObject("USD");
                    double last = obj.getInt("last");
                    double btcInOneDollar = (double) 1 / (double) last;
                    String nnn = new DecimalFormat("#").format(btcInOneDollar);
                    BigDecimal d = new BigDecimal(btcInOneDollar);
                    BigDecimal d1 = d.setScale(2, BigDecimal.ROUND_HALF_UP); // yields 34.00
                    Utilities.saveString(getActivity(), "btcInOneDollar", String.valueOf(btcInOneDollar));
                    Utilities.saveString(getActivity(), "lastValue", String.valueOf(last));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hud.dismiss();
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "Server Error";
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

                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(getActivity()).addToRequestQueue(RegistrationRequest);


    }

    private void bitCoinAddress() {

        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.bitcoin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 200) {
                        JSONObject obj = object.getJSONObject("data");
//                        int id = obj.getInt("id");
                        String rate = obj.getString("rate");
                        String address = obj.getString("address");
                        Utilities.saveString(getActivity(), "bitcoin_address", address);
                        Utilities.saveString(getActivity(), "bitcoin_rate", rate);
//
                    } else {

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
//                params.put("Accept", "application/json");
//                params.put("Authorization", "Bearer " + Utilities.getString(getActivity(), "access_token"));
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(getActivity()).addToRequestQueue(RegistrationRequest);


    }

    public void loginApi() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("email", Utilities.getString(getActivity(), "user_email"));
        params.put("password", Utilities.getString(getActivity(), "user_pass"));
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, Server.LOGIN, getActivity(), params, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    try {
                        JSONObject object = new JSONObject(String.valueOf(result));
                        int status = object.getInt("status");
                        if (status == 200) {
                            JSONObject obj = object.getJSONObject("data");
                            String access_token = obj.getString("access_token");
                            String token_type = obj.getString("token_type");
                            int expires_in = obj.getInt("expires_in");
                            Utilities.saveString(getActivity(), "access_token", access_token);
                          //  Toast.makeText(getActivity(), "Token Refresh", Toast.LENGTH_SHORT).show();
                        } else {
                            String message = object.getString("message");
//                            Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(getActivity(), ERROR, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}