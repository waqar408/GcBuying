package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SendAndWithdrawalResponseModel {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("sent")
    private ArrayList<SendDataModel> sendDataModels;


   /* @SerializedName("recieved")
    String recieved;

    @SerializedName("sold_via_gc")
    String sold_via_gc;*/

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

//    public ArrayList<SendDataModel> getSendDataModels() {
//        return sendDataModels;
//    }
//
//    public void setSendDataModels(ArrayList<SendDataModel> sendDataModels) {
//        this.sendDataModels = sendDataModels;
//    }

    public ArrayList<SendDataModel> getSendDataModels() {
        return sendDataModels;
    }

    public void setSendDataModels(ArrayList<SendDataModel> sendDataModels) {
        this.sendDataModels = sendDataModels;
    }
}
