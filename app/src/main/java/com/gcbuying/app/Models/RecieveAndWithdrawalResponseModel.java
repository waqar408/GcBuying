package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecieveAndWithdrawalResponseModel {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("recieved")
    private ArrayList<RecieveDataModel> recieveDataModels;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<RecieveDataModel> getRecieveDataModels() {
        return recieveDataModels;
    }

    public void setRecieveDataModels(ArrayList<RecieveDataModel> recieveDataModels) {
        this.recieveDataModels = recieveDataModels;
    }
}
