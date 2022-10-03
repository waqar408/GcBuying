package com.gcbuying.app.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.gcbuying.app.Adapters.HistoryFragmentDummyAdapter;
import com.gcbuying.app.Adapters.TradesAdapter;
import com.gcbuying.app.Models.HistoryDummyModel;
import com.gcbuying.app.Models.tradehistory.TradeHistoryApi;
import com.gcbuying.app.Models.TradesModel;
import com.gcbuying.app.Models.tradehistory.TradeHistoryResponseModel;
import com.gcbuying.app.R;
import com.gcbuying.app.Server.NotInUse.MySingleton;
import com.gcbuying.app.Server.Server;
import com.gcbuying.app.networks.ApiClient;
import com.gcbuying.app.networks.GetDataService;
import com.gcbuying.app.networks.UrlController;
import com.gcbuying.app.utilities.Utilities;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class TradeFragment extends Fragment {

    private RecyclerView TradesRecyclerview;
    ArrayList<TradesModel> tradeslist;
    TradesAdapter tradesAdapter;
    LinearLayoutManager manager;
    KProgressHUD hud;
    ArrayList<TradeHistoryApi> list = new ArrayList<>();
    HistoryFragmentDummyAdapter historyFragmentDummyAdapter;
    ApiClient apiClient = new ApiClient();

//   private  RecyclerView SellsRecyclerview;
//   ArrayList<SellsModel> sellslist;
//   SellsAdapter sellsAdapter;
//   LinearLayoutManager linearLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_trade, container, false);

        TradesRecyclerview = v.findViewById(R.id.trade_recyclerview);
        /*list.add(new HistoryDummyModel("ITUNES USA (100s)","$ 100","NGN56,000","12-03-2020"));
        list.add(new HistoryDummyModel("ITUNES USA (100s)","$ 100","NGN56,000","12-03-2020"));
        list.add(new HistoryDummyModel("ITUNES USA (100s)","$ 100","NGN56,000","12-03-2020"));
        list.add(new HistoryDummyModel("ITUNES USA (100s)","$ 100","NGN56,000","12-03-2020"));
        list.add(new HistoryDummyModel("ITUNES USA (100s)","$ 100","NGN56,000","12-03-2020"));
        historyFragmentDummyAdapter = new HistoryFragmentDummyAdapter(getActivity(), list);*/
        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        TradesRecyclerview.setLayoutManager(linearLayoutManager);
        TradesRecyclerview.setAdapter(historyFragmentDummyAdapter);*/
        //   SellsRecyclerview = v.findViewById(R.id.sell_recyclerview);

        getRechargeOrderApi();
        return v;


    }

    private void getTradeHistoryList()
    {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        int user_id = Utilities.getInt(getContext(), "user_id");
        String url = "https://gcbuying.com/api/trade-history/"+user_id;
        GetDataService restService = UrlController.createService(GetDataService.class);
        Call<TradeHistoryResponseModel> myCall = restService.tradeHistory(url);
        myCall.enqueue(new Callback<TradeHistoryResponseModel>() {
            @Override
            public void onResponse(Call<TradeHistoryResponseModel> call, retrofit2.Response<TradeHistoryResponseModel> response) {
                if (response.body()!=null)
                {
                    int status = response.body().getStatus();

                    if (status == 200) {
                        Utilities.hideProgressDialog();

                        list = response.body().getData();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                        TradesRecyclerview.setLayoutManager(linearLayoutManager);
                        TradesRecyclerview.setAdapter(historyFragmentDummyAdapter);

                    }
                    else {
                        Toast.makeText(getContext(), response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TradeHistoryResponseModel> call, Throwable t) {

            }



        });


    }

    private void getRechargeOrderApi() {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        int user_id = Utilities.getInt(getContext(), "user_id");
        String url = "https://gcbuying.com/api/trade-history/"+user_id;
        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    tradeslist = new ArrayList<>();
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 200) {
                        final JSONArray objUser = object.getJSONArray("data");
                        //Toast.makeText(getActivity(), "list found", Toast.LENGTH_SHORT).show();
                        hud.dismiss();
                        for (int i = 0; i < objUser.length(); i++) {
                            JSONObject jsonObject = objUser.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String user_id = jsonObject.getString("user_id");
                            String card_id = jsonObject.getString("card_id");
                            String rate = jsonObject.getString("rate");
                            String total_amount = jsonObject.getString("total_amount");
                            String get_amount = jsonObject.getString("get_amount");
                            String method = jsonObject.getString("method");
                            String note = jsonObject.getString("note");
                            String status1 = jsonObject.getString("status");
                            String txn_id = jsonObject.getString("txn_id");
                            String comment = jsonObject.getString("comment");
                            String updated_at = jsonObject.getString("updated_at");

                            list.add(new TradeHistoryApi(id,card_id,user_id,total_amount,rate,get_amount,method,status1,txn_id,comment));
                        }
                        historyFragmentDummyAdapter = new HistoryFragmentDummyAdapter(getActivity(), list);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                        TradesRecyclerview.setLayoutManager(linearLayoutManager);
                        TradesRecyclerview.setAdapter(historyFragmentDummyAdapter);
                    } else {
                        hud.dismiss();
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

}