package com.gcbuying.app.Activities;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

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

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static com.gcbuying.app.SessionManager.SessionManager.KEY_NAME;

public class LoginActivity extends AppCompatActivity {
    KProgressHUD hud;
    private Button btn_signin;
    private EditText Ed_Email;
    TextView tvForgotPass,createnewaccount;
    ImageView ivBack;
    ImageView biometricLoginButton;
    private EditText Ed_Password;
    String accessToken = "", user_email = "", login_status = "", user_pass = "";
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_signin = findViewById(R.id.btn_signin);
        ivBack = findViewById(R.id.ivBack);
        Ed_Email = findViewById(R.id.ed_email);
        tvForgotPass = findViewById(R.id.tvForgotPass);
        createnewaccount = findViewById(R.id.createnewaccount);
        biometricLoginButton = findViewById(R.id.ivLoginWithBiomateric);
        
        Ed_Password = findViewById(R.id.ed_password);
        accessToken = Utilities.getString(LoginActivity.this, "access_token");
        user_email = Utilities.getString(LoginActivity.this, "user_email");
        user_pass = Utilities.getString(LoginActivity.this, "user_pass");
        login_status = Utilities.getString(LoginActivity.this, "login_status");
        tvForgotPass.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        createnewaccount.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(LoginActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                loginApi(user_email, user_pass);

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();

            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for GC BUYING")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Close")
                .build();

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                generateSecretKey(new KeyGenParameterSpec.Builder(
                        KEY_NAME,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .setUserAuthenticationRequired(true)
                        // Invalidate the keys if the user has registered a new biometric
                        // credential, such as a new fingerprint. Can call this method only
                        // on Android 7.0 (API level 24) or higher. The variable
                        // "invalidatedByBiometricEnrollment" is true by default.
                        .setInvalidatedByBiometricEnrollment(true)
                        .build());
            }
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        biometricLoginButton.setOnClickListener(view -> {
            if (login_status.equals("yes")) {
                biometricPrompt.authenticate(promptInfo);

            } else {
                    Toast.makeText(this, "To Enable this Login First With Email ", Toast.LENGTH_SHORT).show();


            }
        });


        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://gcbuying.com/password/reset"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btn_signin.setEnabled(false);
        btn_signin.setBackgroundResource(R.drawable.btn_round_disabled);

        if (!user_email.equals("")) {
            Ed_Email.setText(user_email);
        } else {
            Ed_Email.setHint("");
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Ed_Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String getEmail = Ed_Email.getText().toString();
                String getPass = Ed_Password.getText().toString();

                if (!getEmail.equals("")) {
                    if (!getPass.equals("")) {
                        if (!(getPass.length() < 6)) {
                            btn_signin.setEnabled(true);
                            btn_signin.setBackgroundResource(R.drawable.btn_round);
                        } else {
                            btn_signin.setEnabled(false);
                            btn_signin.setBackgroundResource(R.drawable.btn_round_disabled);
                        }
                    } else {
                        btn_signin.setEnabled(false);
                        btn_signin.setBackgroundResource(R.drawable.btn_round_disabled);
                    }
                } else {
                    btn_signin.setEnabled(false);
                    btn_signin.setBackgroundResource(R.drawable.btn_round_disabled);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail = Ed_Email.getText().toString();
                String getPass = Ed_Password.getText().toString();
                Utilities.saveString(LoginActivity.this, "user_password", getPass);

                if (!getEmail.equals("")) {
                    if (!getPass.equals("")) {
                        loginApi(getEmail, getPass);
                    } else {
                        Toast.makeText(LoginActivity.this, "Enter Password ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Enter Email ", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

    public void SignUp(View v) {

        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    public void loginApi(final String email, final String password) {
        hud = KProgressHUD.create(LoginActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, Server.LOGIN, LoginActivity.this, params, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    hud.dismiss();

                    try {
                        JSONObject object = new JSONObject(String.valueOf(result));
                        int status = object.getInt("status");
                        if (status == 200) {
                            hud.dismiss();
                            JSONObject obj = object.getJSONObject("data");
                            String access_token = obj.getString("access_token");
                            String token_type = obj.getString("token_type");
                            int expires_in = obj.getInt("expires_in");
                            Utilities.saveString(LoginActivity.this, "access_token", access_token);
                            Utilities.saveString(LoginActivity.this, "login_status", "yes");
                            login_status = "yes";
                            Utilities.saveString(LoginActivity.this, "user_email", email);
                            Utilities.saveString(LoginActivity.this, "user_pass", password);
                            Utilities.saveString(LoginActivity.this,"login","login");
                            Utilities.saveString(LoginActivity.this,"loggedIn","loggedIn");
//                        authMeApi();
                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, Home1Activity.class));
                            finish();
                        } else {
                            String message = object.getString("message");
                            hud.dismiss();
                            Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    hud.dismiss();
                    Toast.makeText(LoginActivity.this, ERROR, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void authMeApi() {
        hud = KProgressHUD.create(LoginActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.authMe, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    String message = object.getString("message");
//                    int  cart_counter = object.getInt("cart_counter");
                    if (status == 200) {

                        JSONObject obj = object.getJSONObject("data");
                        int id = obj.getInt("id");
                        String name = obj.getString("name");
                        String email = obj.getString("email");
                        String phone = obj.getString("phone");
                        String balance = obj.getString("balance");
                        Toast.makeText(LoginActivity.this, "auth success", Toast.LENGTH_SHORT).show();
                        Utilities.saveString(LoginActivity.this, "user_name", name);
                        Utilities.saveString(LoginActivity.this, "user_email", email);

                        Utilities.saveString(LoginActivity.this, "user_phone", phone);
                        Utilities.saveString(LoginActivity.this, "user_balance", balance);
                        Utilities.saveInt(LoginActivity.this, "user_id", id);
                    } else {
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

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
                if (LoginActivity.this != null)
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
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
                params.put("Authorization", "Bearer " + accessToken);
                return params;
            }


        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(LoginActivity.this).addToRequestQueue(RegistrationRequest);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateSecretKey(KeyGenParameterSpec keyGenParameterSpec) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        keyGenerator.init(keyGenParameterSpec);
        keyGenerator.generateKey();
    }

    private SecretKey getSecretKey() throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");

        // Before the keystore can be accessed, it must be loaded.
        keyStore.load(null);
        return ((SecretKey) keyStore.getKey(KEY_NAME, null));
    }

    private Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/"
                + KeyProperties.ENCRYPTION_PADDING_PKCS7);
    }

}