package com.gcbuying.app.networks;


import com.gcbuying.app.Models.AuthResponseModel;
import com.gcbuying.app.Models.PendingWithdrawsResponseModel;
import com.gcbuying.app.Models.RecieveAndWithdrawalResponseModel;
import com.gcbuying.app.Models.SendAndWithdrawalResponseModel;
import com.gcbuying.app.Models.ViaGcBuyingResponseModel;
import com.gcbuying.app.Models.tradehistory.TradeHistoryApi;
import com.gcbuying.app.Models.UserResponseModel;

import com.gcbuying.app.Models.WithdrawResponseModel;
import com.gcbuying.app.Models.sellgiftCard.SellGiftResponseModel;
import com.gcbuying.app.Models.tradehistory.TradeHistoryResponseModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface GetDataService {
    //
    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("auth/register")
    Call<UserResponseModel> registerUser(@Field("name") String name,
                                         @Field("email") String email,
                                         @Field("phone") String phone,
                                         @Field("password") String password);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("withdraws/create")
    Call<WithdrawResponseModel> withdrawNow(@Field("amount") String amount,
                                            @Field("notes") String notes,
                                            @Header("Authorization") String auth);


    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("auth/login")
    Call<UserResponseModel> loginUser(@Field("email") String email,
                                      @Field("password") String password);


    @Headers({
            "Accept: application/json",
    })
    @GET
    Call<TradeHistoryResponseModel> tradeHistory(@Url String url);


    @Headers({
            "Accept: application/json",
    })

    @Multipart
    @POST("trade")
    Call<AuthResponseModel> updateUserProfile(@Part MultipartBody.Part[] multipartTypedOutput,
                                              @Part("total_amount") RequestBody total_amount,
                                              @Part("sell_amount") RequestBody sell_amount,
                                              @Part("note") RequestBody note,
                                              @Part("card") RequestBody car,
                                              @Header("Authorization") String auth);

    @Headers({
            "Accept: application/json",
    })

    @Multipart
    @POST("trade")
    Call<ResponseBody> sellBitCoinApi(@Part MultipartBody.Part[] multipartTypedOutput,
                                      @Part("total_amount") RequestBody total_amount,
                                      @Part("btc_amount") RequestBody btc_amount,
                                      @Part("sell_amount") RequestBody sell_amount,
                                      @Part("note") RequestBody note,
                                      @Header("Authorization") String auth);

    @Headers({
            "Accept: application/json",
    })
    @POST("card/category")
    Call<SellGiftResponseModel> getCardCategory();


    @POST("wallet/transactions/sentandrecieved")
    Call<SendAndWithdrawalResponseModel> sendAndWithdraw(@HeaderMap Map<String, String> headers);

    @POST("wallet/transactions/sentandrecieved")
    Call<RecieveAndWithdrawalResponseModel> sendAndWithdraw2(@HeaderMap Map<String, String> headers);


    @POST("wallet/transactions/sentandrecieved")
    Call<ViaGcBuyingResponseModel> sendAndWithdraw3(@HeaderMap Map<String, String> headers);


    @POST("withdraws")
    Call<PendingWithdrawsResponseModel> withdrawResponseModel(@HeaderMap Map<String, String> headers);


}
