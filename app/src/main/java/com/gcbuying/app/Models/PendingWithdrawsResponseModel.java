package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PendingWithdrawsResponseModel {
    @SerializedName("status")
    private int status;

    @SerializedName("pending")
    private ArrayList<PendingDataModel> pendingDataModels;

    @SerializedName("completed")
    private ArrayList<PendingDataModel> completedDataModels;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<PendingDataModel> getPendingDataModels() {
        return pendingDataModels;
    }

    public void setPendingDataModels(ArrayList<PendingDataModel> pendingDataModels) {
        this.pendingDataModels = pendingDataModels;
    }

    public ArrayList<PendingDataModel> getCompletedDataModels() {
        return completedDataModels;
    }

    public void setCompletedDataModels(ArrayList<PendingDataModel> completedDataModels) {
        this.completedDataModels = completedDataModels;
    }
}
