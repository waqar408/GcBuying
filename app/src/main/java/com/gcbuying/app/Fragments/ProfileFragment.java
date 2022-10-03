package com.gcbuying.app.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.gcbuying.app.Activities.Home1Activity;
import com.gcbuying.app.Activities.LoginActivity;
import com.gcbuying.app.Activities.profile.AppSettingActivity;
import com.gcbuying.app.Activities.profile.ChangeBankingInformationActivity;
import com.gcbuying.app.R;
import com.gcbuying.app.Server.NotInUse.MySingleton;
import com.gcbuying.app.Server.Server;
import com.gcbuying.app.api.ApiModelClass;
import com.gcbuying.app.api.ServerCallback;
import com.gcbuying.app.utilities.Utilities;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {
    LinearLayout lnPersonalInformation,lnChangeEmail,lnChangePassword,lnCustomerSupport,lnBankDetail;
    RelativeLayout rlSettings;
    LinearLayout logout;
    KProgressHUD hud;
    String accountName,bankName,accountNo,password;
    String accessToken = "";
    String uName = "";
    String pNumber = "";
    String uEmail = "";
    TextView tvUserName,tvEmail;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        lnPersonalInformation = view.findViewById(R.id.lnPersonalInformation);
        lnChangeEmail = view.findViewById(R.id.lnChangeEmail);
        lnChangePassword = view.findViewById(R.id.lnChangePassword);
        lnBankDetail = view.findViewById(R.id.lnBankDetail);
        lnCustomerSupport = view.findViewById(R.id.lnCustomerSupport);
        accessToken = Utilities.getString(getActivity(), "access_token");
        uName  = Utilities.getString(getActivity(),"user_name");
        pNumber  = Utilities.getString(getActivity(),"user_phone");
        uEmail = Utilities.getString(getActivity(),"user_email");
        tvUserName = view.findViewById(R.id.tvUserName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvUserName.setText(uName);
        tvEmail.setText(uEmail);


        rlSettings = view.findViewById(R.id.rlSettings);
        logout = view.findViewById(R.id.logout);
        password = Utilities.getString(getContext(),"user_pass");
        lnPersonalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfileInformation();
            }
        });
        lnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEmail();

            }
        });
        lnCustomerSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://gcbuying.com/"));
                startActivity(viewIntent);
            }
        });
        lnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();

            }
        });
        lnBankDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBankingInformation();

            }
        });
        rlSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AppSettingActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                Utilities.clearSharedPref(requireContext());
                getActivity().finish();
            }
        });
        getBandDetailApi();

        return view;
    }
    private void getBandDetailApi() {
        hud = KProgressHUD.create(getContext())
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
                        bankName = data.getString("bank_name");
                        int id = data.getInt("id");
                        accountName = data.getString("account_name");
                        accountNo = data.getString("account_no");

                        Utilities.saveString(getContext(), "account_name", accountName);
                        Utilities.saveString(getContext(), "account_no", accountNo);
                        Utilities.saveString(getContext(), "bank_name", bankName);

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
                if (getContext() != null)
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
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
                params.put("Authorization", "Bearer " + Utilities.getString(getContext(),"access_token"));
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(getContext()).addToRequestQueue(RegistrationRequest);


    }

    private void changeProfileInformation() {
        BottomSheetDialog bottomSheetDialog =new  BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottomsheet_change_profile_information);
        RelativeLayout rlback = bottomSheetDialog.findViewById(R.id.rlback);
        EditText ed_userName = bottomSheetDialog.findViewById(R.id.ed_userName);
        EditText ed_phone = bottomSheetDialog.findViewById(R.id.ed_phone);
        Button btn_updateProfile = bottomSheetDialog.findViewById(R.id.btn_updateProfile);
        ed_userName.setText(uName);
        ed_phone.setText(pNumber);

        btn_updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = ed_userName.getText().toString();
                String edPhone = ed_phone.getText().toString();
                if (userName.equals(""))
                {
                    Toast.makeText(getContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
                }else if (edPhone.equals(""))
                {
                    Toast.makeText(getContext(), "Please Enter Phone Number", Toast.LENGTH_SHORT).show();

                }else {
                    updateProfileApi(userName,edPhone);
                }
            }
        });
        rlback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }
    private void changeEmail() {
        BottomSheetDialog bottomSheetDialog =new  BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottomsheet_change_email);
        RelativeLayout rlback = bottomSheetDialog.findViewById(R.id.rlback);
        rlback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }
    private void changePassword() {
        BottomSheetDialog bottomSheetDialog =new  BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottomsheet_change_password);
        RelativeLayout rlback = bottomSheetDialog.findViewById(R.id.rlback);
        EditText ed_newPass = bottomSheetDialog.findViewById(R.id.ed_newPass);
        EditText ed_confirmNewPass = bottomSheetDialog.findViewById(R.id.ed_confirmNewPass);
        EditText ed_OldPass = bottomSheetDialog.findViewById(R.id.ed_OldPass);
        Button btn_changPassword = bottomSheetDialog.findViewById(R.id.btn_changPassword);
        btn_changPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = ed_newPass.getText().toString();
                String cpass = ed_confirmNewPass.getText().toString();
                String oldPass = ed_OldPass.getText().toString();
                if (pass.equals(""))
                {
                    Toast.makeText(getContext(), "Please Enter New Password", Toast.LENGTH_SHORT).show();
                }else if (cpass.equals(""))
                {
                    Toast.makeText(getContext(), "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                }else if (!cpass.equals(pass))
                {
                    Toast.makeText(getContext(), "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                }else if (oldPass.equals(""))
                {
                    Toast.makeText(getContext(), "Please Enter Old Password", Toast.LENGTH_SHORT).show();
                }else if (!oldPass.equals(password))
                {
                    Toast.makeText(getContext(), "Wrong Current Password", Toast.LENGTH_SHORT).show();
                }else {
                   updatePassword(pass,oldPass);
                }
            }
        });
        rlback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }
    private void changeBankingInformation() {
        BottomSheetDialog bottomSheetDialog =new  BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottomsheet_change_bankinginformation);
        RelativeLayout rlback = bottomSheetDialog.findViewById(R.id.rlback);
        EditText ed_usdAmount = bottomSheetDialog.findViewById(R.id.ed_usdAmount);
        EditText ed_btcAmount = bottomSheetDialog.findViewById(R.id.ed_btcAmount);
        EditText ed_getN = bottomSheetDialog.findViewById(R.id.ed_getN);
        EditText ed_password = bottomSheetDialog.findViewById(R.id.ed_password);
        Button btn_tradeBitCoin = bottomSheetDialog.findViewById(R.id.btn_tradeBitCoin);
        ed_usdAmount.setText(bankName);
        ed_btcAmount.setText(accountName);
        ed_getN.setText(accountNo);


        btn_tradeBitCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getABandName = ed_usdAmount.getText().toString();
                String getAccName = ed_btcAmount.getText().toString();
                String getAccNo = ed_getN.getText().toString();
                String pass = ed_password.getText().toString();
                if (!getABandName.isEmpty()) {
                    if (!getAccName.isEmpty()) {
                        if (!getAccNo.isEmpty()) {
                            if (pass.equals(password))
                            {
                                updateBankDetailApi(getAccName, getAccNo, getABandName);

                            }else {
                                Toast.makeText(getContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getActivity(), "Account Number Required", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Account Name Required", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), " Bank Name Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rlback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    private void updateBankDetailApi(final String account_name, final String account_no, final String bank_name) {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.bank_update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
//                    String message = object.getString("message");
//                    int  cart_counter = object.getInt("cart_counter");
                    if (status == 200) {
                        hud.dismiss();
                        Toast.makeText(getActivity(), "Bank Detail Updated Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), Home1Activity.class));
                        getActivity().finish();
                    } else {
                        hud.dismiss();
                        Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();

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
                if (getActivity() != null)
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(Utilities.getInt(getActivity(), "user_id")));
                params.put("account_name", account_name);
                params.put("bank_name", bank_name);
                params.put("account_no", account_no);
//

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

    private void updatePassword(final String password, final String currentPassword) {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.change_password, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
//                    String message = object.getString("message");
//                    int  cart_counter = object.getInt("cart_counter");
                    if (status == 200) {
                        hud.dismiss();
                        Toast.makeText(getActivity(), "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    } else {
                        hud.dismiss();
                        Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();

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
                if (getActivity() != null)
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("new_password", password);
                params.put("old_password", currentPassword);
//

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

    private void updateProfileApi(final String name, final String number) {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.updateProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
//                    String message = object.getString("message");
//                    int  cart_counter = object.getInt("cart_counter");
                    if (status == 200) {
                        hud.dismiss();
                        Toast.makeText(getActivity(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), Home1Activity.class));
                        getActivity().finish();
                    } else {
                        hud.dismiss();
                        Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();

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
                if (getActivity() != null)
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", name);
                params.put("last_name", "");
                params.put("phone", number);
//

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



}