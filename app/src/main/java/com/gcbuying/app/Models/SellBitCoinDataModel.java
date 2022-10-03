package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

public class SellBitCoinDataModel {

    @SerializedName("user_id")
    private  int user_id;
    @SerializedName("card_id")
    private  String card_id;
       @SerializedName("get_amount")
    private  String get_amount;

     @SerializedName("rate")
    private  String rate;
     @SerializedName("method")
    private  String method;

    public SellBitCoinDataModel(int user_id, String card_id, String get_amount, String rate, String method) {
        this.user_id = user_id;
        this.card_id = card_id;
        this.get_amount = get_amount;
        this.rate = rate;
        this.method = method;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getGet_amount() {
        return get_amount;
    }

    public void setGet_amount(String get_amount) {
        this.get_amount = get_amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
