package com.gcbuying.app.Models;

public class SellHistoryModel {


    String id;
    String amount_BTC;
    String amount_NAIRA;
    String status;
    String time;
    private boolean bottomShown;

    public SellHistoryModel(String id, String amount_BTC, String amount_NAIRA, String status, String time, boolean bottomShown) {
        this.id = id;
        this.amount_BTC = amount_BTC;
        this.amount_NAIRA = amount_NAIRA;
        this.status = status;
        this.time = time;
        this.bottomShown = bottomShown;
    }

    public SellHistoryModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount_BTC() {
        return amount_BTC;
    }

    public void setAmount_BTC(String amount_BTC) {
        this.amount_BTC = amount_BTC;
    }

    public String getAmount_NAIRA() {
        return amount_NAIRA;
    }

    public void setAmount_NAIRA(String amount_NAIRA) {
        this.amount_NAIRA = amount_NAIRA;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isBottomShown() {
        return bottomShown;
    }

    public void setBottomShown(boolean bottomShown) {
        this.bottomShown = bottomShown;
    }
}
