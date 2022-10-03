package com.gcbuying.app.Models.sellgiftCard;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SellGiftResponseModel {

    @SerializedName("status")
    private int status;

    @SerializedName("data")
    private List<SellGiftCardDataModel> data;

    public SellGiftResponseModel(int status, List<SellGiftCardDataModel> data) {
        this.status = status;

        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public List<SellGiftCardDataModel> getData() {
        return data;
    }

    public void setData(List<SellGiftCardDataModel> data) {
        this.data = data;
    }
}
