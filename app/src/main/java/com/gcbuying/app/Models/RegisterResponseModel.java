package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

public class RegisterResponseModel {
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private RegisterDataModel data;

    public RegisterResponseModel() {
    }

    public RegisterResponseModel(int status,RegisterDataModel data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public RegisterDataModel getData() {
        return data;
    }

    public void setData(RegisterDataModel data) {
        this.data = data;
    }


}

