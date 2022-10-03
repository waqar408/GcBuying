//package com.example.gc_buying.Activities;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.ClipData;
//import android.content.ClipboardManager;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.provider.MediaStore;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.Base64;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.NetworkError;
//import com.android.volley.NoConnectionError;
//import com.android.volley.ParseError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.ServerError;
//import com.android.volley.TimeoutError;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.example.gc_buying.Models.AuthDataModel;
//import com.example.gc_buying.Models.AuthResponseModel;
//import com.example.gc_buying.Models.SellBitCoinDataModel;
//import com.example.gc_buying.Models.SellBitCoinModel;
//import com.example.gc_buying.R;
//import com.example.gc_buying.Server.MySingleton;
//import com.example.gc_buying.Server.Server;
//import com.example.gc_buying.networks.GetDataService;
//import com.example.gc_buying.networks.RetrofitClientInstance;
//import com.example.gc_buying.networks.UrlController;
//import com.example.gc_buying.utilities.Utilities;
//import com.kaopiz.kprogresshud.KProgressHUD;
//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//
//public class TradeBitCoinActivityOld extends AppCompatActivity {
//    KProgressHUD hud;
//    private ImageView back_ic, iv_TransacionImage, iv_edit;
//    private ImageButton btn_Copy;
//    private EditText ed_usdAmount, ed_btcAmount, ed_getN, ed_PayToId, ed_extraInfo;
//    private Button btn_tradeBitCoin;
//    Uri imageUri;
//    String token;
//    private ClipboardManager myClipboard;
//    private ClipData myClip;
//    GetDataService service;
//    Bitmap selected_img_bitmap;
//    private Uri mCropImageUri;
//    private static final String TAG = TradeBitCoinActivityOld.class.getSimpleName();
//    double last,finalBtcAmount,BtcAmountinD,finalRateinD,getfinalSellamount;
//    String btcInOneDollar;
//    String imageEncoded, bitcoin_rate;
//    String input = "", photo = "";
//    Uri TransactionUri = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_trade_bit_coin);
//        String bitcoin_address = Utilities.getString(TradeBitCoinActivityOld.this, "bitcoin_address");
//        bitcoin_rate = Utilities.getString(TradeBitCoinActivityOld.this, "bitcoin_rate");
//        token = Utilities.getString(TradeBitCoinActivityOld.this, "access_token");
//        btcInOneDollar = Utilities.getString(TradeBitCoinActivityOld.this, "btcInOneDollar");
//         BtcAmountinD = Double.parseDouble(btcInOneDollar);
//         finalRateinD = Double.parseDouble(bitcoin_rate);
//
////        double finals=d*120;
//
//
//        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        back_ic = findViewById(R.id.back_ic);
//        iv_TransacionImage = findViewById(R.id.iv_transacionImage);
//        btn_Copy = findViewById(R.id.btn_Copy);
//        iv_edit = findViewById(R.id.iv_edit);
//        ed_usdAmount = findViewById(R.id.ed_usdAmount);
//        ed_btcAmount = findViewById(R.id.ed_btcAmount);
//        ed_getN = findViewById(R.id.ed_getN);
//        ed_PayToId = findViewById(R.id.ed_id);
//        ed_extraInfo = findViewById(R.id.ed_extraInfo);
//        btn_tradeBitCoin = findViewById(R.id.btn_tradeBitCoin);
//        ed_PayToId.setText(bitcoin_address);
//        ed_usdAmount.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (TextUtils.isEmpty(ed_usdAmount.getText().toString())) {
//                    ed_btcAmount.setText("0");
//                    return;
//                } else {
//                    int getusdAmmount = Integer.parseInt(ed_usdAmount.getText().toString());
////                    getNairaAmmout = String.valueOf(getusdAmmount * Integer.parseInt(rate));
//                    finalBtcAmount = BtcAmountinD*(double)Integer.parseInt(String.valueOf(getusdAmmount));
//
//                    double finalNairaAmount = finalRateinD*(double)Integer.parseInt(String.valueOf(getusdAmmount));
//                    ed_btcAmount.setText(String.valueOf(finalBtcAmount));
//                    double vat=finalNairaAmount*0.15;
//                    getfinalSellamount=finalNairaAmount-vat;
//                    ed_getN.setText(String.valueOf(finalNairaAmount));
//
//
//                }
//
//            }
//        });
//        btn_Copy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//                String text;
//                text = Utilities.getString(TradeBitCoinActivityOld.this, "bitcoin_address");
//                myClip = ClipData.newPlainText("text", text);
//                myClipboard.setPrimaryClip(myClip);
//
//                Toast.makeText(TradeBitCoinActivityOld.this, "Text Copied", Toast.LENGTH_SHORT).show();
//            }
//        });
//        btn_tradeBitCoin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String getBtcAmount = ed_btcAmount.getText().toString();
//                String getUsdAmount = ed_usdAmount.getText().toString();
//                String getNaira = ed_getN.getText().toString();
//                String getNote = ed_extraInfo.getText().toString();
//                if (!(imageUri == null)) {
//                    if (!getBtcAmount.equals("")) {
//                        if (!getUsdAmount.equals("")) {
//                            if (!getNaira.equals("")) {
//                                sellBitcointFromExternalWallet(imageUri, getUsdAmount, String.valueOf(finalBtcAmount), String.valueOf(getfinalSellamount), getNote, token);
//                            } else {
//                                Toast.makeText(TradeBitCoinActivityOld.this, "enter amount", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//
//                            Toast.makeText(TradeBitCoinActivityOld.this, "enter amount", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//
//                        Toast.makeText(TradeBitCoinActivityOld.this, "btc amount not found", Toast.LENGTH_SHORT).show();
//
//                    }
//                } else {
//
//                    Toast.makeText(TradeBitCoinActivityOld.this, "upload image ", Toast.LENGTH_SHORT).show();
//
//                }
//
//
//            }
//        });
//        back_ic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                finish();
//            }
//        });
//        iv_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                ChooseImage();
//                selectImage();
//            }
//        });
//
//
//    }
//
//    private void ChooseImage() {
//        Intent imagepicker = new Intent();
//        imagepicker.setType("image/*");
//        imagepicker.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(imagepicker, 01);
//    }
//
//    private void selectImage() {
//        CropImage.startPickImageActivity(this);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        // handle result of pick image chooser
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            imageUri = CropImage.getPickImageResultUri(this, data);
//
//            // For API >= 23 we need to check specifically that we have permissions to read external storage.
//            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
//                // request permissions and handle the result in onRequestPermissionsResult()
//                mCropImageUri = imageUri;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
//                }
//            } else {
//                // no permissions required or already grunted, can start crop image activity
//                startCropImageActivity(imageUri);
//            }
//        }
//
//        // handle result of CropImageActivity
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
////                Uri uri = result.getUri();
////                try {
////                    //Uses https://github.com/zetbaitsu/Compressor library to compress selected image
////                    File file = new Compressor(this).compressToFile(new File(uri.getPath()));
////                    Picasso.get().load(file).into(iv_CardImage);
////                    Toast.makeText(this, "Compressed", Toast.LENGTH_SHORT).show();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                    Toast.makeText(this, "Failed Compress", Toast.LENGTH_SHORT).show();
////                    Picasso.get().load(uri).into(iv_CardImage);
////                }
//
//                imageUri = result.getUri();
//
//                try {
//
//                    selected_img_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                    iv_TransacionImage.setImageBitmap(selected_img_bitmap);
//
//                    Bitmap immagex = selected_img_bitmap;
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                    byte[] b = baos.toByteArray();
//                    imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
//
//                    input = imageEncoded;
//                    input = input.replace("\n", "");
//                    input = input.trim();
////                    input = "data:image/png;base64," + input;
//                    Utilities.saveString(TradeBitCoinActivityOld.this, "image", input);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////                        uploadAvatar();
//                    }
//                }, 1000);
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                //TODO handle cropping error
//                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    private void sellBitcointFromExternalWallet(Uri images, String total_amount, String btcAmount,
//                                                String sellAmount, String note, String token) {
//
//        hud = KProgressHUD.create(TradeBitCoinActivityOld.this)
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("Please wait")
//                .setCancellable(true)
//                .setAnimationSpeed(2)
//                .setDimAmount(0.5f)
//                .show();
//        String filePath = getRealPathFromURIPath(images, TradeBitCoinActivityOld.this);
//        File file = new File(filePath);
//        Log.d(TAG, "filePath=" + filePath);
//        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//        RequestBody mFile = RequestBody.create(MediaType.parse("*/*"), file);
//        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("images", file.getName(), mFile);
//
//        RequestBody getTotalAmount = RequestBody.create(MediaType.parse("text/plain"), total_amount);
//        RequestBody getBTC = RequestBody.create(MediaType.parse("text/plain"), btcAmount);
//        RequestBody getSellAmount = RequestBody.create(MediaType.parse("text/plain"), sellAmount);
//        RequestBody getNote = RequestBody.create(MediaType.parse("text/plain"), note);
//
//        GetDataService restService = UrlController.createService(GetDataService.class);
//
//
//        Call<ResponseBody> fileUpload = restService.sellBitCoinApi(fileToUpload, getTotalAmount, getBTC, getSellAmount, getNote, "Bearer " + token);
//        fileUpload.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//                hud.dismiss();
//                Toast.makeText(TradeBitCoinActivityOld.this, "sent", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                hud.dismiss();
//                Toast.makeText(TradeBitCoinActivityOld.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "Error " + t.getMessage());
//            }
//
//        });
//    }
//
//    public void sellBitvoin(View view) {
//        Toast.makeText(TradeBitCoinActivityOld.this, "under developing", Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            // required permissions granted, start crop image activity
//            startCropImageActivity(mCropImageUri);
//        } else {
//            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private void startCropImageActivity(Uri imageUri) {
//        CropImage.activity(imageUri)
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setAllowFlipping(false)
//                .setActivityTitle("Crop Image")
//                .setCropMenuCropButtonIcon(R.drawable.ic_check)
//                .setAllowRotation(true)
//                .setInitialCropWindowPaddingRatio(0)
//                .setFixAspectRatio(true)
//                .setAspectRatio(1, 1)
//                .setOutputCompressQuality(80)
//                .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
//                .setMultiTouchEnabled(true)
//                .start(this);
//    }
//
//    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
//        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
//        if (cursor == null) {
//            return contentURI.getPath();
//        } else {
//            cursor.moveToFirst();
//            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            return cursor.getString(idx);
//        }
//    }
//
//
//}