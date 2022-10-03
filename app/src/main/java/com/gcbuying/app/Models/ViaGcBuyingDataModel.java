package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

public class ViaGcBuyingDataModel {
    @SerializedName("txid")
    String txid;

    @SerializedName("amount_btc")
    String amount_btc;

    @SerializedName("amount_naira")
    String amount_naira;

    @SerializedName("status")
    String status;

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getAmount_btc() {
        return amount_btc;
    }

    public void setAmount_btc(String amount_btc) {
        this.amount_btc = amount_btc;
    }

    public String getAmount_naira() {
        return amount_naira;
    }

    public void setAmount_naira(String amount_naira) {
        this.amount_naira = amount_naira;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
