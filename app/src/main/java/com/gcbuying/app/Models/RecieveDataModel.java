package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecieveDataModel {
    @SerializedName("txid")
    String txid;

    @SerializedName("time")
    int time;

    @SerializedName("confirmations")
    int confirmations;



    @SerializedName("amounts_received")
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

    public ArrayList<AmountSentDataModel> getAmounts_sent() {
        return amounts_sent;
    }

    public void setAmounts_sent(ArrayList<AmountSentDataModel> amounts_sent) {
        this.amounts_sent = amounts_sent;
    }
}
