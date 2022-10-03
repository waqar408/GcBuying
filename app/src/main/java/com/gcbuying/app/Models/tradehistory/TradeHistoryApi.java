package com.gcbuying.app.Models.tradehistory;

import com.google.gson.annotations.SerializedName;

public class TradeHistoryApi {
    @SerializedName("id")
    private int id;

    @SerializedName("card_id")
    private String card_id;

    @SerializedName("product_name")
    private String product_name;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("total_amount")
    private String total_amount;

    @SerializedName("rate")
    private String rate;

    @SerializedName("get_amount")
    private String get_amount;

    @SerializedName("method")
    private String method;

    @SerializedName("status")
    private String status;


    @SerializedName("txn_id")
    private String txn_id;

    @SerializedName("comment")
    private String comment;

    public TradeHistoryApi(int id, String card_id, String user_id, String total_amount, String rate, String get_amount, String method, String status, String txn_id, String comment) {
        this.id = id;
        this.card_id = card_id;
        this.user_id = user_id;
        this.total_amount = total_amount;
        this.rate = rate;
        this.get_amount = get_amount;
        this.method = method;
        this.status = status;
        this.txn_id = txn_id;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getGet_amount() {
        return get_amount;
    }

    public void setGet_amount(String get_amount) {
        this.get_amount = get_amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
