package com.gcbuying.app.PopFragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.gcbuying.app.R;
import com.gcbuying.app.Server.NotInUse.MySingleton;
import com.gcbuying.app.Server.Server;
import com.gcbuying.app.api.ApiModelClass;
import com.gcbuying.app.api.ServerCallback;
import com.gcbuying.app.utilities.Utilities;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordFragment extends DialogFragment {


    private EditText ed_CurrentPassword, ed_NewPassword, ed_Con_Password;
    private Button btn_Save;
    KProgressHUD hud;
    String accessToken = "";
    ImageView ivBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        String user_password = Utilities.getString(getActivity(), "user_password");
        accessToken = Utilities.getString(getActivity(), "access_token");

        ed_CurrentPassword = v.findViewById(R.id.ed_current_Password);
        ed_NewPassword = v.findViewById(R.id.ed_new_Password);
        ed_Con_Password = v.findViewById(R.id.ed_confirm_Password);
        btn_Save = v.findViewById(R.id.btn_save);
//        ed_CurrentPassword.setText(user_password);
        ivBack = v.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getoldPass = ed_CurrentPassword.getText().toString();
                String getNewpass = ed_NewPassword.getText().toString();
                String getConfirmNewpass = ed_Con_Password.getText().toString();
                if (!getoldPass.equals("")) {
                    if (!getNewpass.equals("")) {
                        if (!(getNewpass.length() < 8)) {
                            if ((getNewpass.equals(getConfirmNewpass))) {
                                changePassApi(getoldPass, getNewpass);
                            } else {
                                Toast.makeText(getActivity(), "Confirm Password not match", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "new Password required ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "old password required", Toast.LENGTH_SHORT).show();

                }

            }
        });

        return v;
    }

    private void changePassApi(final String old_pass, final String new_pass) {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, "https://gcbuying.com/api/auth/change-password", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    String message = object.getString("message");
                    if (status == 200) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        hud.dismiss();
                    } else {
                        hud.dismiss();
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
                    message = "server not found please try again later!.";
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
                params.put("old_password", old_pass);
                params.put("new_password", new_pass);
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

    public void changePassApi1(final String old_pass, final String new_pass) {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", String.valueOf(Utilities.getInt(getActivity(), "user_id")));
        params.put("old_password", old_pass);
        params.put("new_password", new_pass);
        HashMap<String, String> headers = new HashMap<String, String>();
        params.put("Accept", "application/json");
        params.put("Authorization", "Bearer " + accessToken);


        ApiModelClass.GetApiResponse(Request.Method.POST, Server.change_password, getActivity(), params, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    hud.dismiss();

                    try {
                        JSONObject object = new JSONObject(String.valueOf(result));
                        int status = object.getInt("status");
                        String message = object.getString("message");
                        if (status == 200) {

                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            hud.dismiss();
                        } else {

                            hud.dismiss();
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    hud.dismiss();
                    Toast.makeText(getActivity(), ERROR, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}