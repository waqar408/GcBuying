package com.gcbuying.app.Models.tradehistory;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TradeHistoryResponseModel {
    @SerializedName("status")
    private int status;

    @SerializedName("data")
    private ArrayList<TradeHistoryApi> data;

    public TradeHistoryResponseModel(int status, ArrayList<TradeHistoryApi> data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<TradeHistoryApi> getData() {
        return data;
    }

    public void setData(ArrayList<TradeHistoryApi> data) {
        this.data = data;
    }
}
