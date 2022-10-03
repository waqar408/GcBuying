package com.gcbuying.app.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.gcbuying.app.Models.MultipleImageModel;
import com.gcbuying.app.R;
import com.gcbuying.app.Server.NotInUse.MySingleton;
import com.gcbuying.app.networks.GetDataService;
import com.gcbuying.app.networks.RetrofitClientInstance;
import com.gcbuying.app.networks.UrlController;
import com.gcbuying.app.utilities.Utilities;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libs.mjn.prettydialog.PrettyDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class TradeBitCoinActivity extends AppCompatActivity {

    KProgressHUD hud;
    private ImageView back_ic, iv_TransacionImage, pickImage;
    private ImageView btn_Copy;
    private EditText ed_usdAmount, ed_btcAmount, ed_getN, ed_PayToId, ed_extraInfo;
    private Button btn_tradeBitCoin;
    Uri imageUri, uri;
    private ArrayList<Uri> imageUris;
    private static final int PICK_IMAGES_CODE = 0;
    int PICK_IMAGE_MULTIPLE = 1;
    ArrayList<MultipleImageModel> multipleImageModels;
    List<String> imagesEncodedList;
    String token;
    int position = 0;
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    ArrayList<String> base64_images = new ArrayList<>();
    private ClipboardManager myClipboard;
    private ClipData myClip;
    GetDataService service;
    Bitmap selected_img_bitmap;
    private Uri mCropImageUri;
    ImageView imgBack;
    private static final String TAG = TradeBitCoinActivity.class.getSimpleName();
    double last, finalBtcAmount, BtcAmountinD, finalRateinD, getfinalSellamount;
    String btcInOneDollar;
    String imageEncoded, bitcoin_rate, decimalString_finalBtcAmount;
    String input = "", photo = "", lastValue = "";
    Uri TransactionUri = null;
    String requestBody = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_bit_coin);
        String bitcoin_address = Utilities.getString(TradeBitCoinActivity.this, "bitcoin_address");
        bitcoin_rate = Utilities.getString(TradeBitCoinActivity.this, "bitcoin_rate");
        token = Utilities.getString(TradeBitCoinActivity.this, "access_token");
        btcInOneDollar = Utilities.getString(TradeBitCoinActivity.this, "btcInOneDollar");
        lastValue = Utilities.getString(TradeBitCoinActivity.this, "lastValue");

        BtcAmountinD = Double.parseDouble(btcInOneDollar);
        finalRateinD = Double.parseDouble(bitcoin_rate);

//        double finals=d*120;

        askPermissions();
        imageUris = new ArrayList<>();
        multipleImageModels = new ArrayList<MultipleImageModel>();
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        back_ic = findViewById(R.id.back_ic);
        iv_TransacionImage = findViewById(R.id.iv_transacionImage);
        btn_Copy = findViewById(R.id.btn_Copy);
        ed_usdAmount = findViewById(R.id.ed_usdAmount);
        ed_btcAmount = findViewById(R.id.ed_btcAmount);
        ed_getN = findViewById(R.id.ed_getN);
        imgBack = findViewById(R.id.imgBack);
        pickImage = findViewById(R.id.pickImage);
        ed_PayToId = findViewById(R.id.ed_id);
        ed_extraInfo = findViewById(R.id.ed_extraInfo);
        btn_tradeBitCoin = findViewById(R.id.btn_tradeBitCoin);
        ed_PayToId.setText(bitcoin_address);
        ed_usdAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(ed_usdAmount.getText().toString())) {
                    ed_btcAmount.setText("0");
                    return;
                } else {
                    int getusdAmmount = Integer.parseInt(ed_usdAmount.getText().toString());
//                    getNairaAmmout = String.valueOf(getusdAmmount * Integer.parseInt(rate));

                    finalBtcAmount = BtcAmountinD * (double) Integer.parseInt(String.valueOf(getusdAmmount));
/*
                    Double scientificDouble = Double.parseDouble(scientificNotation);
                    NumberFormat nf = new DecimalFormat("################################################.###########################################");
                    String decimalString = nf.format(scientificDouble);
                    Log.v(TAG, "decimalString: " + decimalString);
*/
                    String scientificNotation = String.valueOf(finalBtcAmount);
                    Double scientificDouble = Double.parseDouble(scientificNotation);
                    NumberFormat nf = new DecimalFormat("################################################.###########################################");
                    decimalString_finalBtcAmount = nf.format(scientificDouble);

/*
                    Log.v(TAG, "finalBtcAmount: " + finalBtcAmount);
                    Log.v(TAG, "decimalString_finalBtcAmount: " + decimalString_finalBtcAmount);
                    Log.v(TAG, "decimalString_finalBtcAmount_double: " + Double.valueOf(decimalString_finalBtcAmount));
*/


//                    Y get (N)(Amount to send * bitcoin price in usd * naira rate)
                    double finalNairaAmount = finalRateinD * (double) Integer.parseInt(String.valueOf(getusdAmmount));
//                    double finalNairaAmount = finalRateinD*(double)Integer.parseInt(String.valueOf(getusdAmmount))*Double.parseDouble(lastValue);
//bitcoinrate*usd*nairaRate
//                    ed_btcAmount.setText(String.valueOf(finalBtcAmount));
                    ed_btcAmount.setText(String.valueOf(decimalString_finalBtcAmount));
                    double vat = finalNairaAmount * 0.15;
                    getfinalSellamount = finalNairaAmount - vat;
                    ed_getN.setText(String.valueOf(finalNairaAmount));

                }

            }
        });
        btn_Copy.setOnClickListener(v -> {
            myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            String text;
            text = Utilities.getString(TradeBitCoinActivity.this, "bitcoin_address");
            myClip = ClipData.newPlainText("text", text);
            myClipboard.setPrimaryClip(myClip);

            Toast.makeText(TradeBitCoinActivity.this, "Text Copied", Toast.LENGTH_SHORT).show();
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_tradeBitCoin.setOnClickListener(v -> {
            String getBtcAmount = ed_btcAmount.getText().toString();
            String getUsdAmount = ed_usdAmount.getText().toString();
            String getNaira = ed_getN.getText().toString();
            String getNote = ed_extraInfo.getText().toString();
            if (multipleImageModels != null && !multipleImageModels.isEmpty()) {
                if (!getBtcAmount.equals("")) {
                    if (!getUsdAmount.equals("")) {
                        if (!getNaira.equals("")) {
//                                makerequest(getUsdAmount, String.valueOf(finalBtcAmount), String.valueOf(getfinalSellamount), getNote, token);
//                            makerequest(getUsdAmount, Double.valueOf(decimalString_finalBtcAmount), getNote);
                            makerequest(getUsdAmount, finalBtcAmount, getNote);
                        } else {
                            Toast.makeText(TradeBitCoinActivity.this, "enter amount", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(TradeBitCoinActivity.this, "enter amount", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(TradeBitCoinActivity.this, "btc amount not found", Toast.LENGTH_SHORT).show();

                }
            } else {

                Toast.makeText(TradeBitCoinActivity.this, "upload image ", Toast.LENGTH_SHORT).show();

            }


        });
        back_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ChooseImage();
                if (ContextCompat.checkSelfPermission(TradeBitCoinActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TradeBitCoinActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            2);
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), PICK_IMAGES_CODE);
                }
            }
        });

    }

    //    private void makerequest(String getToalAmount, String btcAmount, String getNote) {
    private void makerequest(String getToalAmount, double btcAmount, String getNote) {
        hud = KProgressHUD.create(TradeBitCoinActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        try {

            JSONObject userData = new JSONObject();

            userData.put("btc_amount", btcAmount);
            userData.put("total_amount", getToalAmount);
            userData.put("sell_amount", getfinalSellamount);
            userData.put("note", getNote);
            userData.put("images", new JSONArray(base64_images));

            requestBody = userData.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, "https://gcbuying.com/api/trade", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hud.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 200) {
                        hud.dismiss();
                        showCustomDialogforExit();
//                        Toast.makeText(TradeBitCoinActivity.this, "Sell Bitcoin Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        hud.dismiss();
                        String data = object.getString("message");
                        Toast.makeText(TradeBitCoinActivity.this, data, Toast.LENGTH_SHORT).show();
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


            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {

                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            ;

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Accept", "application/json");
//                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + token);

                return headers;

            }
        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(TradeBitCoinActivity.this).addToRequestQueue(RegistrationRequest);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                if (data.getClipData() != null) {
                    //picked multiple images

                    int cout = data.getClipData().getItemCount(); //number of picked images
                    for (int i = 0; i < cout; i++) {
                        //get image uri at specific index
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
//                    Toast.makeText(this, imageUris.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            selected_img_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                        iv_CardImage.setImageBitmap(selected_img_bitmap);

                            Bitmap immagex = selected_img_bitmap;
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            immagex.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] b = baos.toByteArray();
                            imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

                            input = imageEncoded;
                            input = input.replace("\n", "");
                            input = "data:image/png;base64," + input.trim();
                            base64_images.add(input);
                            imageUris.add(imageUri); //add to list

                            multipleImageModels.add(new MultipleImageModel(input));
//                            Toast.makeText(this, multipleImageModels.toString(), Toast.LENGTH_SHORT).show();
//                    input = "data:image/png;base64," + input;
                            Utilities.saveString(TradeBitCoinActivity.this, "image", input);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    //set first image to oure.setImageURI(imageUris.get(0));
                    position = 0;
                } else {
                    //picked single image
                    Uri imageUri = data.getData();
                    imageUris.add(imageUri);
//                Toast.makeText(this, imageUris.toString(), Toast.LENGTH_SHORT).show();
                    try {
                        selected_img_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                        iv_CardImage.setImageBitmap(selected_img_bitmap);

                        Bitmap immagex = selected_img_bitmap;
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        immagex.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                        byte[] b = baos.toByteArray();
                        imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

                        input = imageEncoded;
                        input = input.replace("\n", "");
                        input = "data:image/png;base64," + input.trim();
                        base64_images.add(input);
                        imageUris.add(imageUri); //add to list

                        multipleImageModels.add(new MultipleImageModel(input));
//                        Toast.makeText(this, multipleImageModels.toString(), Toast.LENGTH_SHORT).show();
//                    input = "data:image/png;base64," + input;
                        Utilities.saveString(TradeBitCoinActivity.this, "image", input);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //set image to our image switcher
                    iv_TransacionImage.setImageURI(imageUris.get(0));
                    position = 0;
                }

            }

        }
    }


    public void sellBitvoin(View view) {
        Toast.makeText(TradeBitCoinActivity.this, "under developing", Toast.LENGTH_SHORT).show();

    }

    private void askPermissions() {

        int permissionCheckStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // we already asked for permisson & Permission granted, call camera intent
        if (permissionCheckStorage == PackageManager.PERMISSION_GRANTED) {

            //do what you want

        } else {

            // if storage request is denied
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("You need to give permission to access storage in order to work this feature.");
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("GIVE PERMISSION", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        // Show permission request popup
                        ActivityCompat.requestPermissions(TradeBitCoinActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                STORAGE_PERMISSION_REQUEST_CODE);
                    }
                });
                builder.show();

            } //asking permission for first time
            else {
                // Show permission request popup for the first time
                ActivityCompat.requestPermissions(TradeBitCoinActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_REQUEST_CODE);

            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // check whether storage permission granted or not.
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //do what you want;
                    }
                }
                break;
            default:
                break;
        }
    }


    private void ChooseImage() {
        Intent imagepicker = new Intent();
        imagepicker.setType("image/*");
        imagepicker.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(imagepicker, 01);
    }

    private void selectImage() {
        CropImage.startPickImageActivity(this);
    }

    private void sellBitcointFromExternalWallet(Uri images, String total_amount, String btcAmount,
                                                String sellAmount, String note, String token) {

        hud = KProgressHUD.create(TradeBitCoinActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        MultipartBody.Part[] multipartTypedOutput = new MultipartBody.Part[multipleImageModels.size()];

        for (int index = 0; index < multipleImageModels.size(); index++) {
            Log.d("Upload request", "requestUploadSurvey: survey image " + index + "  " + multipleImageModels.get(index).getPath());
            File file2 = new File(multipleImageModels.get(index).getPath());
            RequestBody surveyBody = RequestBody.create(MediaType.parse("images"), file2);
            multipartTypedOutput[index] = MultipartBody.Part.createFormData("imageFiles[]", file2.getPath(), surveyBody);
        }
        RequestBody getTotalAmount = RequestBody.create(MediaType.parse("text/plain"), total_amount);
        RequestBody getBTC = RequestBody.create(MediaType.parse("text/plain"), btcAmount);
        RequestBody getSellAmount = RequestBody.create(MediaType.parse("text/plain"), sellAmount);
        RequestBody getNote = RequestBody.create(MediaType.parse("text/plain"), note);

        GetDataService restService = UrlController.createService(GetDataService.class);


        Call<ResponseBody> fileUpload = restService.sellBitCoinApi(multipartTypedOutput, getTotalAmount, getBTC, getSellAmount, getNote, "Bearer " + token);
        fileUpload.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                hud.dismiss();
                Toast.makeText(TradeBitCoinActivity.this, "sent", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hud.dismiss();
                Toast.makeText(TradeBitCoinActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error " + t.getMessage());
            }

        });
    }


    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAllowFlipping(false)
                .setActivityTitle("Crop Image")
                .setCropMenuCropButtonIcon(R.drawable.ic_check)
                .setAllowRotation(true)
                .setInitialCropWindowPaddingRatio(0)
                .setFixAspectRatio(true)
                .setAspectRatio(1, 1)
                .setOutputCompressQuality(80)
                .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public void getImageFilePath(Uri uri) {

        File file = new File(uri.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];
        Cursor cursor = getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            multipleImageModels.add(new MultipleImageModel(imagePath));
            cursor.close();
        }
    }

    private void showCustomDialogforExit() {

        final PrettyDialog pDialog = new PrettyDialog(TradeBitCoinActivity.this);
        pDialog
                .setTitle("Done!")
                .setMessage("Sold Bitcoin successfully")
                .setIcon(R.drawable.checked)
//                .setIconTint(R.color.greenSucess)

//                .setIconTint(R.color.colorPrimary)
                /* .addButton(
                         "Ok",
                         R.color.colorPrimary,
                         R.color.pdlg_color_white,
                         new PrettyDialogCallback() {
                             @Override
                             public void onClick() {
                                 finishAffinity();
                                 pDialog.dismiss();
                             }
                         }
                 )
                 .addButton("No",
                         R.color.pdlg_color_red,
                         R.color.pdlg_color_white,
                         new PrettyDialogCallback() {
                             @Override
                             public void onClick() {
                                 pDialog.dismiss();
                             }
                         })*/
                .show();

        Thread myThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
/*

                    Intent intent = new Intent(getApplicationContext(), ContinueAs.class);
                    startActivity(intent);
*/
                    pDialog.dismiss();
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }

}