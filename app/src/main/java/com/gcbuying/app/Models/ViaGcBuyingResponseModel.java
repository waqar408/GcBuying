package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ViaGcBuyingResponseModel {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("sold_via_gc")
    private ArrayList<ViaGcBuyingDataModel> sendDataModels;

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

    public ArrayList<ViaGcBuyingDataModel> getSendDataModels() {
        return sendDataModels;
    }

    public void setSendDataModels(ArrayList<ViaGcBuyingDataModel> sendDataModels) {
        this.sendDataModels = sendDataModels;
    }
}
