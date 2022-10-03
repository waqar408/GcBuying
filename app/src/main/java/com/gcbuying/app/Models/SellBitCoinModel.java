package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

public class SellBitCoinModel {
    @SerializedName("status")
    private int status;

    @SerializedName("data")
    private SellBitCoinDataModel data;

    public SellBitCoinModel(int status, SellBitCoinDataModel data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SellBitCoinDataModel getData() {
        return data;
    }

    public void setData(SellBitCoinDataModel data) {
        this.data = data;
    }
}
