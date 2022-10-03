package com.gcbuying.app.Models;

public class WithdrawPendingHistoryModel {
    String time,ngnAmount;

    public WithdrawPendingHistoryModel(String time, String ngnAmount) {
        this.time = time;
        this.ngnAmount = ngnAmount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNgnAmount() {
        return ngnAmount;
    }

    public void setNgnAmount(String ngnAmount) {
        this.ngnAmount = ngnAmount;
    }
}
