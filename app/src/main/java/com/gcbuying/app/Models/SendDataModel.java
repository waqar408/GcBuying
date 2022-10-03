package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SendDataModel {

    @SerializedName("txid")
    String txid;

    @SerializedName("time")
    int time;

    @SerializedName("confirmations")
    int confirmations;

    @SerializedName("total_amount_sent")
    String total_amount_sent;


    @SerializedName("amounts_sent")
    ArrayList<AmountSentDataModel> amounts_sent;



    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }


    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(int confirmations) {
        this.confirmations = confirmations;
    }

    public String getTotal_amount_sent() {
        return total_amount_sent;
    }

    public void setTotal_amount_sent(String total_amount_sent) {
        this.total_amount_sent = total_amount_sent;
    }

    public ArrayList<AmountSentDataModel> getAmounts_sent() {
        return amounts_sent;
    }

    public void setAmounts_sent(ArrayList<AmountSentDataModel> amounts_sent) {
        this.amounts_sent = amounts_sent;
    }


}

