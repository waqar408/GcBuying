package com.gcbuying.app.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.gcbuying.app.Adapters.CardCategoryAdapter;
import com.gcbuying.app.Models.MultipleImageModel;
import com.gcbuying.app.Models.SellCardModel;
import com.gcbuying.app.Models.sellgiftCard.SellGiftCardDataModel;
import com.gcbuying.app.Models.sellgiftCard.SellGiftResponseModel;
import com.gcbuying.app.R;
import com.gcbuying.app.Server.NotInUse.MySingleton;
import com.gcbuying.app.Server.Server;
import com.gcbuying.app.SessionManager.SessionManager;
import com.gcbuying.app.api.MyApplication;
import com.gcbuying.app.networks.GetDataService;
import com.gcbuying.app.networks.RetrofitClientInstance;
import com.gcbuying.app.utilities.Utilities;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;


public class SellGiftActivity extends AppCompatActivity implements CardCategoryAdapter.Callback {
    KProgressHUD dialog0;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    private CardCategoryAdapter adpter;
    private final int SPLASH_DISPLAY_LENGTH = 1500;
    SpinnerDialog spinnerDialog;
    TextView tvCardRate;
    RelativeLayout rlSelectCardCategoty;
    //    private  String CardTypeArray [] = {"Visa Card","Master Card","Gold Card","Silver Card"};
    private static final String TAG = SellGiftActivity.class.getSimpleName();
    private ImageView mAvatar;
    private Uri mCropImageUri;
    private LinearLayout mContainer;
    private SessionManager mSessionManager;
    private ImageView back_ic, iv_CardImage, pickImage;
    private EditText ed_totalAmount, ed_GetN, ed_GrderNote;
    private Button btn_SellGiftCard;
    Uri imageUri, uri;
    private ArrayList<SellCardModel> sellCardModels;
    List<String> sellCards;
    int getSellCard;
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 1;

    String uris[];
    private static MyApplication mInstance;
    private RequestQueue mRequestQueue;
    double getNairaAmmout;
    String rate, rate11 = "", getBTCAmount;
    KProgressHUD hud;
    private static final int PICK_IMAGE = 1;
    private ImageView imgBack;
    private Button upload;
    private EditText caption;
    private Bitmap bitmap;
    Dialog dialoggg;
    private ProgressDialog dialog;
    ArrayList<String> mArrayUri;
    private List<SellGiftCardDataModel> sellGiftCardDataModels;
    String token;
    MultipartBody.Part[] multipartTypedOutput;
    private ArrayList<Uri> imageUris;
    ArrayList<String> base64_images = new ArrayList<>();
    String requestBody = "";


    //request code to pick images
    private static final int PICK_IMAGES_CODE = 0;

    //positoion of selected image
    int position = 0;
    GetDataService service;
    Bitmap selected_img_bitmap;
    String input = "", photo = "";
    ArrayList<MultipleImageModel> multipleImageModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_gift);
        token = Utilities.getString(SellGiftActivity.this, "access_token");
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        back_ic = findViewById(R.id.back_ic);
        imgBack = findViewById(R.id.imgBack);
        iv_CardImage = findViewById(R.id.iv_cardImage);
        pickImage = findViewById(R.id.pickImage);
        ed_totalAmount = findViewById(R.id.ed_totalAmount);
        ed_GetN = findViewById(R.id.ed_getN);
        rlSelectCardCategoty = findViewById(R.id.rlSelectCardCategoty);
        ed_GrderNote = findViewById(R.id.ed_orderNote);
        btn_SellGiftCard = findViewById(R.id.btn_sellGiftCard);
        tvCardRate = findViewById(R.id.tvCardRate);
        multipleImageModels = new ArrayList<MultipleImageModel>();

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sellCards);
//        CardType.setAdapter(arrayAdapter);


        onClicks();
        askPermissions();
        imageUris = new ArrayList<>();

    }

    private void onClicks() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rlSelectCardCategoty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialogueForCardCategory();
            }
        });

        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SellGiftActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SellGiftActivity.this,
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

        ed_totalAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(ed_totalAmount.getText().toString())) {
                    ed_GetN.setText("0");
                    return;
                } else {

//                    getNairaAmmout = String.valueOf(getusdAmmount * Integer.parseInt(rate));
                    if (rate11.equals("")) {
                        Toast.makeText(SellGiftActivity.this, "Please Select the Card First", Toast.LENGTH_SHORT).show();
                    } else {
                        int getusdAmmount = Integer.parseInt(ed_totalAmount.getText().toString());
                        getNairaAmmout = Double.parseDouble(rate11) * Integer.parseInt(String.valueOf(getusdAmmount));

                        getBTCAmount = String.valueOf(getusdAmmount * 0.000052);
                        ed_GetN.setText(String.valueOf(getNairaAmmout));
                    }

                }

            }
        });


        btn_SellGiftCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getToalAmount = ed_totalAmount.getText().toString();
                String getNairaAmmount = ed_GetN.getText().toString();
                String getNote = ed_GrderNote.getText().toString();

                if (getSellCard != 0) {
                    if (!getToalAmount.equals("")) {
                        if (!getNairaAmmount.equals("")) {
                            if (multipleImageModels != null && !multipleImageModels.isEmpty()) {
                                makerequest(getToalAmount, getNairaAmmount, getNote);

                            } else {
                                alertDialog("Please Click the blue icon to Upload card image");
                            }
                        } else {

                            Toast.makeText(SellGiftActivity.this, "Enter (N) Amount", Toast.LENGTH_SHORT).show();

                        }
                    } else {

                        Toast.makeText(SellGiftActivity.this, "Enter Total Amount ", Toast.LENGTH_SHORT).show();

                    }
                } else {

                    Toast.makeText(SellGiftActivity.this, "Select Your Card ", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void makerequest(String getToalAmount, String getNairaAmmount, String getNote) {

        hud = KProgressHUD.create(SellGiftActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        try {
            JSONObject userData = new JSONObject();

            userData.put("card", String.valueOf(getSellCard));
            userData.put("total_amount", getToalAmount);
            userData.put("sell_amount", getNairaAmmount);
            userData.put("note", getNote);
            userData.put("images", new JSONArray(base64_images));

            requestBody = userData.toString();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mInstance, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.trade, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hud.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 200) {
                        hud.dismiss();
                        Toast.makeText(SellGiftActivity.this, "Sell Gift Card Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SellGiftActivity.this, CompletionActivity.class));

                    } else {
                        hud.dismiss();
                        String data = object.getString("message");
                        Toast.makeText(SellGiftActivity.this, data, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SellGiftActivity.this, "" + message, Toast.LENGTH_SHORT).show();

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

        MySingleton.getInstance(SellGiftActivity.this).addToRequestQueue(RegistrationRequest);

    }

    private void getSellCardApi(String category) {
        hud = KProgressHUD.create(SellGiftActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final StringRequest RegistrationRequest = new StringRequest(Request.Method.POST, Server.card_category_show, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    sellCardModels = new ArrayList<SellCardModel>();
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 200) {
                        hud.dismiss();
                        sellCards = new ArrayList<>();
                        JSONObject jsonObject;
                        JSONArray objUser = object.getJSONArray("data");
                        for (int i = 0; i < objUser.length(); i++) {
                            jsonObject = objUser.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            String rate = jsonObject.getString("rate");
                            sellCardModels.add(new SellCardModel(id, name, rate));
                            sellCards.add(name);


                        }
//                        spinnerDialog = new SpinnerDialog(SellGiftActivity.this, (ArrayList<String>) sellCards, "Select Your Card", "Close ");// With No Animation
                        spinnerDialog = new SpinnerDialog(SellGiftActivity.this, (ArrayList<String>) sellCards, "Select Your Card", R.style.DialogAnimations_SmileWindow, "Close");// With 	Animation

                        spinnerDialog.setCancellable(true); // for cancellable
                        spinnerDialog.setShowKeyboard(false);// for open keyboard by default


                        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                            @Override
                            public void onClick(String item, int position) {
                                dialoggg.dismiss();
                                rate11 = sellCardModels.get(position).getRate();
//                selectedItems.setText(item + " Position: " + position);sdsdfgg
                                getSellCard = sellCardModels.get(position).getId();

                                tvCardRate.setText(sellCardModels.get(position).getProductName());
                                ed_totalAmount.setEnabled(true);
                                ed_GetN.setText(rate11);
                            }
                        });


                    } else {
                        hud.dismiss();

                    }

                } catch (JSONException e) {
                    hud.dismiss();

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
                if (SellGiftActivity.this != null)
                    Toast.makeText(SellGiftActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("category", category);
//                params.put("do", "area_units");
//                params.put("apikey", "travces.com");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
//                params.put("Authorization", "Bearer " + accessToken);
                return params;
            }
        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(SellGiftActivity.this).addToRequestQueue(RegistrationRequest);


    }

    //    private void sellGiftCardApi(String carddId, String images, String total_amount, String sell_amount, String note, String token) {

    private void selectImage() {
        CropImage.startPickImageActivity(this);
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
                            Utilities.saveString(SellGiftActivity.this, "image", input);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    //set first image to our image switcher
                    iv_CardImage.setImageURI(imageUris.get(0));
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
                        Utilities.saveString(SellGiftActivity.this, "image", input);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //set image to our image switcher
                    iv_CardImage.setImageURI(imageUris.get(0));
                    position = 0;
                }

            }

        }
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

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
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
                        ActivityCompat.requestPermissions(SellGiftActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                STORAGE_PERMISSION_REQUEST_CODE);
                    }
                });
                builder.show();

            } //asking permission for first time
            else {
                // Show permission request popup for the first time
                ActivityCompat.requestPermissions(SellGiftActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_REQUEST_CODE);

            }

        }
    }

    private void showdialogueForCardCategory() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        dialoggg = new Dialog(SellGiftActivity.this);
        dialoggg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialoggg.setContentView(R.layout.help_dialog);
        dialoggg.getWindow().setLayout((int) (width * 0.8), (int) (height * 0.92)); //Controlling width and height.
        dialoggg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RecyclerView category_recyclerview = dialoggg.findViewById(R.id.category_recyclerview);
        ImageView imgclose = dialoggg.findViewById(R.id.imgclose);
        TextView tvStatus = dialoggg.findViewById(R.id.tvStatus);
        AVLoadingIndicatorView aviLoader = dialoggg.findViewById(R.id.aviLoader);


        getCardCategotyApi(category_recyclerview, aviLoader, tvStatus);


        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialoggg.dismiss();
            }
        });


        dialoggg.show();
    }

    private void getCardCategotyApi(RecyclerView category_recyclerview, AVLoadingIndicatorView aviLoader, TextView tvStatus) {

        aviLoader.setVisibility(View.VISIBLE);


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SellGiftResponseModel> call = service.getCardCategory();
        call.enqueue(new Callback<SellGiftResponseModel>() {
            @Override
            public void onResponse(Call<SellGiftResponseModel> call, retrofit2.Response<SellGiftResponseModel> response) {

                assert response.body() != null;
                int status = response.body().getStatus();
                sellGiftCardDataModels = response.body().getData();
                if (sellGiftCardDataModels != null && !sellGiftCardDataModels.isEmpty()) {
                    aviLoader.setVisibility(View.GONE);
                    if (status == 200) {
                        aviLoader.setVisibility(View.GONE);
                        Toast.makeText(SellGiftActivity.this, "Card Category Found", Toast.LENGTH_SHORT).show();
                    } else {
                        aviLoader.setVisibility(View.GONE);
                        Toast.makeText(SellGiftActivity.this, "Card Categoty Not Found", Toast.LENGTH_SHORT).show();
                    }
                    setData(category_recyclerview, sellGiftCardDataModels);
                    tvStatus.setVisibility(View.GONE);

                } else {
                    aviLoader.setVisibility(View.GONE);
                    tvStatus.setVisibility(View.VISIBLE);
                    Toast.makeText(SellGiftActivity.this, "no Data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SellGiftResponseModel> call, Throwable t) {

                aviLoader.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });

    }

    private void setData(RecyclerView rvAvailableDate, List datalist) {

        adpter = new CardCategoryAdapter(SellGiftActivity.this, datalist, SellGiftActivity.this);
        GridLayoutManager manager = new GridLayoutManager(SellGiftActivity.this, 2, RecyclerView.VERTICAL, false);
        rvAvailableDate.setHasFixedSize(true);
        rvAvailableDate.setLayoutManager(manager);
        rvAvailableDate.setAdapter(adpter);
    }

    @Override
    public void onItemClick(int pos) {
        String category_name = sellGiftCardDataModels.get(pos).getSlug();
        getSellCardApi(category_name);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sellCards != null && !sellCards.isEmpty()) {
                    spinnerDialog.showSpinerDialog();
                } else {
                    Toast.makeText(SellGiftActivity.this, "Data not Found OR its Loading ,Please Try again", Toast.LENGTH_SHORT).show();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void alertDialog(String message) {
        android.app.AlertDialog.Builder saveDialog = new android.app.AlertDialog.Builder(this);
        saveDialog.setTitle("ALERT!!");
        saveDialog.setMessage(message);
        saveDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
//        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });

        saveDialog.show();
    }
}