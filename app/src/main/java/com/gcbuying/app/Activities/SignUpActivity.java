package com.gcbuying.app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.gcbuying.app.Models.UserResponseModel;
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

public class SignUpActivity extends AppCompatActivity {

    private ImageView back_ic;
    private EditText ed_fullname, ed_email, ed_phonenumber;
    private EditText ed_password, ed_confirm_password;
    private Button btn_signup;
    KProgressHUD hud;
    TextView tvLogin;
    RelativeLayout rlback;
    GetDataService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        back_ic = findViewById(R.id.back_ic);
        ed_fullname = findViewById(R.id.ed_fullname);
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        ed_confirm_password = findViewById(R.id.ed_confirm_Password);
        ed_phonenumber = findViewById(R.id.ed_phonenumber);
        btn_signup = findViewById(R.id.btn_sigUp);
        tvLogin = findViewById(R.id.tvLogin);
        rlback = findViewById(R.id.rlback);
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);


        tvLogin.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        rlback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName = ed_fullname.getText().toString();
                String getPhone = ed_phonenumber.getText().toString();
                String getEmail = ed_email.getText().toString();
                String getPass = ed_password.getText().toString();
                String getConfirmpass = ed_confirm_password.getText().toString();
                Utilities.saveString(SignUpActivity.this, "user_password", getPass);
                if (!getName.isEmpty()) {
                    if (!getEmail.isEmpty()) {
                        if (!getPass.isEmpty()) {
                            if (!getConfirmpass.isEmpty()) {
                                if (!getPhone.isEmpty()) {
                                    if (!(getPass.length() < 8)) {
                                        SignupApi(getName, getEmail, getPass, getPhone);

                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Phone Required", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(SignUpActivity.this, "Confirm Pass Required", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "Password Required", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Email Required", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, " Name Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void SignIn(View v) {

        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

    }

    private void SignupApi(final String name, final String email, final String password, final String phone) {
        hud = KProgressHUD.create(SignUpActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, "https://gcbuying.com/api/auth/register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 200) {
                        hud.dismiss();

                        Toast.makeText(SignUpActivity.this, "Register Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Email OR Phone already exist ", Toast.LENGTH_SHORT).show();
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
//                    message = "Server error please try again later!";
                    message = "Phone or Email has  already been taken ";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                if (SignUpActivity.this != null)
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("phone", phone);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(SignUpActivity.this).addToRequestQueue(RegistrationRequest);


    }
    private void createNewUser(String name, String email, String phone, String password) {
        hud = KProgressHUD.create(SignUpActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserResponseModel> call = service.registerUser(name,email,phone,password);
        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call, retrofit2.Response<UserResponseModel> response) {

                assert response.body() != null;
                int status = response.body().getStatus();
                if (status == 200) {
                    hud.dismiss();
                    Toast.makeText(SignUpActivity.this,"successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(SignUpActivity.this, "eroe", Toast.LENGTH_SHORT).show();

                } else {
                    hud.dismiss();
                    Toast.makeText(SignUpActivity.this,"error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponseModel> call, Throwable t) {

                hud.dismiss();
                t.printStackTrace();
            }
        });
    }

    public void SignupApi1(final String name, final String email, final String password, final String phone) {
        hud = KProgressHUD.create(SignUpActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
        params.put("phone", phone);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, Server.SIGNUP, SignUpActivity.this, params, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    hud.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");
//                        String Message = jsonObject.getString("Message");
                        if (status == 200) {
                            hud.dismiss();
                            Toast.makeText(SignUpActivity.this, "successfully register", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            hud.dismiss();
                            Toast.makeText(SignUpActivity.this, "detail already use ", Toast.LENGTH_SHORT).show();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    hud.dismiss();
                    Toast.makeText(SignUpActivity.this, ERROR, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}