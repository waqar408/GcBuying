package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

public class UserResponseModel {

    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private UserDataModel data;


    public UserResponseModel(int status, UserDataModel data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserDataModel getData() {
        return data;
    }

    public void setData(UserDataModel data) {
        this.data = data;
    }
}
