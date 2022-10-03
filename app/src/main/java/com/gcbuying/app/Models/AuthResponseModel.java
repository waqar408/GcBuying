package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

public class AuthResponseModel {
    @SerializedName("status")
    private int status;

    @SerializedName("data")
    private AuthDataModel data;

    public AuthResponseModel(int status, AuthDataModel data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AuthDataModel getData() {
        return data;
    }

    public void setData(AuthDataModel data) {
        this.data = data;
    }
}
