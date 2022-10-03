package com.gcbuying.app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
import com.gcbuying.app.Models.WithdrawResponseModel;
import com.gcbuying.app.R;
import com.gcbuying.app.Server.NotInUse.MySingleton;
import com.gcbuying.app.Server.Server;
import com.gcbuying.app.api.ApiModelClass;
import com.gcbuying.app.api.ServerCallback;
import com.gcbuying.app.networks.GetDataService;
import com.gcbuying.app.networks.RetrofitClientInstance;
import com.gcbuying.app.utilities.Utilities;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class WithDrawNowActivity extends AppCompatActivity {

    private ImageView back_ic;
    private EditText ed_TotalAmountN, ed_OrderNote,ed_password;
    private Button btn_withDrawNow;
    KProgressHUD hud;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw_now);

        back_ic = findViewById(R.id.back_ic);
        ed_TotalAmountN = findViewById(R.id.ed_totalamountN);
        ed_OrderNote = findViewById(R.id.ed_orderNote);
        ed_password = findViewById(R.id.ed_password);
        btn_withDrawNow = findViewById(R.id.btn_withDrawNow);
        imgBack = findViewById(R.id.imgBack);
        String getAmount = ed_TotalAmountN.getText().toString();
        String getNotes = ed_OrderNote.getText().toString();


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        btn_withDrawNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getAmount = ed_TotalAmountN.getText().toString();
                String getNotes = ed_OrderNote.getText().toString();
                String password = ed_password.getText().toString();
                String pass = Utilities.getString(WithDrawNowActivity.this,"user_pass");
                String getToken = Utilities.getString(WithDrawNowActivity.this, "access_token");
                if (!getAmount.equals("")) {
                    if (password.equals(pass))
                    {
                        withdrawNowApi(getAmount, getNotes);
                    }else {
                        Toast.makeText(WithDrawNowActivity.this, "You have entered the wrong password", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(WithDrawNowActivity.this, "enter ammount first", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    private void withdrawRetrofit(String amount1,String notes,String token) {
        hud = KProgressHUD.create(WithDrawNowActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<WithdrawResponseModel> call = service.withdrawNow(amount1, notes,"Bearer" +token);
        call.enqueue(new Callback<WithdrawResponseModel>() {
            @Override
            public void onResponse(Call<WithdrawResponseModel> call, retrofit2.Response<WithdrawResponseModel> response) {

                assert response.body() != null;
                int status = response.body().getStatus();
                if (status == 200) {
                    hud.dismiss();
                    Toast.makeText(WithDrawNowActivity.this, "Successfully", Toast.LENGTH_SHORT).show();

                } else {
                    hud.dismiss();
                    Toast.makeText(WithDrawNowActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<WithdrawResponseModel> call, Throwable t) {
                hud.dismiss();
//                t.printStackTrace();
                Toast.makeText(WithDrawNowActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void withdrawNowApi(final String amount, final String notes) {
        hud = KProgressHUD.create(WithDrawNowActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.withdraws_createPass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    String data = object.getString("data");
//                    String message = object.getString("message");
//                    int  cart_counter = object.getInt("cart_counter");
                    if (status == 200) {
                       // JSONObject data = object.getJSONObject("data");
                        Toast.makeText(WithDrawNowActivity.this, ""+data, Toast.LENGTH_SHORT).show();
                        /*String amount = data.getString("amount");
                        hud.dismiss();
                        Toast.makeText(WithDrawNowActivity.this, "Amount: "+amount+" "+"Withdraw Successfully", Toast.LENGTH_SHORT).show();*/
                    } else {
                        hud.dismiss();
                        String message = object.getString("data");
                        Toast.makeText(WithDrawNowActivity.this, message, Toast.LENGTH_SHORT).show();

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
                if (WithDrawNowActivity.this != null)
                    Toast.makeText(WithDrawNowActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("amount", amount);
                params.put("notes", notes);
//

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + Utilities.getString(WithDrawNowActivity.this, "access_token"));
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(WithDrawNowActivity.this).addToRequestQueue(RegistrationRequest);


    }

    public void withdrawNowApi1(final String amount, final String notes) {
        hud = KProgressHUD.create(WithDrawNowActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("amount", amount);
        params.put("notes", notes);
        HashMap<String, String> headers = new HashMap<String, String>();
        params.put("Accept", "application/json");
        params.put("Authorization", "Bearer " + Utilities.getString(WithDrawNowActivity.this, "access_token"));


        ApiModelClass.GetApiResponse(Request.Method.POST, Server.withdraws_create, WithDrawNowActivity.this, params, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    hud.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");
                        if (status == 200) {
                            hud.dismiss();
                            Toast.makeText(WithDrawNowActivity.this, "successfully withdraw", Toast.LENGTH_LONG).show();
                        } else {
                            hud.dismiss();
//                            String message = jsonObject.getString("data");
                            Toast.makeText(WithDrawNowActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(WithDrawNowActivity.this, "jsonException", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    hud.dismiss();
                    Toast.makeText(WithDrawNowActivity.this, ERROR, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}