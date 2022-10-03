package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

public class WithdrawResponseModel {


    @SerializedName("status")
    private int status;

    @SerializedName("data")
    private WithDrawDataModel data;

    public WithdrawResponseModel(int status, WithDrawDataModel data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public WithDrawDataModel getData() {
        return data;
    }

    public void setData(WithDrawDataModel data) {
        this.data = data;
    }
}
