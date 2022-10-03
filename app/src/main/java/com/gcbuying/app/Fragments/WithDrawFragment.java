package com.gcbuying.app.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.gcbuying.app.Activities.WithDrawNowActivity;
import com.gcbuying.app.Adapters.WithDrawAdapter;
import com.gcbuying.app.Fragments.withdrawhistory.WithdrawCompletedHistoryFragment;
import com.gcbuying.app.Fragments.withdrawhistory.WithdrawPendingHistoryFragment;
import com.gcbuying.app.Models.WithDrawModel;
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


public class WithDrawFragment extends Fragment {

    private RecyclerView WithgDrawRecyclerview;
    ArrayList<WithDrawModel> withDrawModels;
    WithDrawAdapter adapter;
    LinearLayoutManager manager;
    KProgressHUD hud;
    TextView tvBalance;
    private Button btn_WithDrawNow;
    Button btnPending,btnAccepted;
    ImageView imgHide;
    ImageView imgShow;
    private Boolean passwordVisibile = false;
    String naira_balance = "";
    WithdrawCompletedHistoryFragment withdrawCompletedHistoryFragment = new WithdrawCompletedHistoryFragment();
    WithdrawPendingHistoryFragment withdrawPendingHistoryFragment = new WithdrawPendingHistoryFragment();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_with_draw, container, false);
        naira_balance = Utilities.getString(getActivity(), "naira_balance");
        initViews(v);
        onClicks();
        getWithDrawasApi();
        tvBalance.setText("NGN "+naira_balance);
        return v;
    }

    private void onClicks() {


        btn_WithDrawNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WithDrawNowActivity.class));
            }
        });

    }
    private void initViews(View v) {

        WithgDrawRecyclerview = v.findViewById(R.id.With_Draw);
        btnPending = v.findViewById(R.id.btnPending);
        btn_WithDrawNow = v.findViewById(R.id.btn_withDrawNow);
        tvBalance = v.findViewById(R.id.tvBalance);
        btnAccepted = v.findViewById(R.id.btnAccepted);
        imgHide = v.findViewById(R.id.imgHide);
        imgShow = v.findViewById(R.id.imgShow);
        onClickss();
        setFragment(withdrawPendingHistoryFragment);
    }
    private void onClickss()
    {
        btnPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(withdrawPendingHistoryFragment);
                btnPending.setBackgroundResource(R.drawable.btn_white);
                btnAccepted.setBackgroundResource(R.drawable.btn_transparent);
                btnAccepted.setTextColor(getResources().getColor(R.color.unselectedtext));
                btnPending.setTextColor(getResources().getColor(R.color.textcolor));
            }
        });
        btnAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(withdrawCompletedHistoryFragment);
                btnPending.setBackgroundResource(R.drawable.btn_transparent);
                btnAccepted.setBackgroundResource(R.drawable.btn_white);
                btnAccepted.setTextColor(getResources().getColor(R.color.textcolor));
                btnPending.setTextColor(getResources().getColor(R.color.unselectedtext));
            }
        });
        imgHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgShow.setVisibility(View.VISIBLE);
                imgHide.setVisibility(View.GONE);
                tvBalance.setText("NGN "+naira_balance);
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
    }

    private void getWithDrawasApi() {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.withdraws, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    withDrawModels = new ArrayList<>();
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 200) {
                        final JSONArray objUser = object.getJSONArray("data");
//                        Toast.makeText(getActivity(), "list found", Toast.LENGTH_SHORT).show();
                        hud.dismiss();
                        for (int i = 0; i < objUser.length(); i++) {
                            JSONObject jsonObject = objUser.getJSONObject(i);
                            String amount = jsonObject.getString("amount");
                            int id = jsonObject.getInt("id");
                            String trx_id = jsonObject.getString("trx_id");
                            String bank_name = jsonObject.getString("bank_name");

                            String account_name = jsonObject.getString("account_name");
                            String account_no = jsonObject.getString("account_no");
                            String status1 = jsonObject.getString("status");
                            String created_at = jsonObject.getString("created_at");
                            String updated_at = jsonObject.getString("updated_at");

                            withDrawModels.add(new WithDrawModel(trx_id, bank_name, account_name, account_no, amount, status1, created_at, updated_at, status1, false));

                        }
                        adapter = new WithDrawAdapter(getActivity(), withDrawModels);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                        WithgDrawRecyclerview.setLayoutManager(linearLayoutManager);
                        WithgDrawRecyclerview.setAdapter(adapter);


                    } else {

                        Toast.makeText(getActivity(), "data not found", Toast.LENGTH_SHORT).show();
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
    private void setFragment( Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frames, fragment);
        fragmentTransaction.commit();
    }
}