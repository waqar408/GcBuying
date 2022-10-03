package com.gcbuying.app.Models;

public class SendHistoryModel {


    String id;
    String amount;
    String send_from;
    int confirmation;
    String time;
    private boolean bottomShown;

    public SendHistoryModel(String id, String amount, String send_from, int confirmation, String time, boolean bottomShown) {
        this.id = id;
        this.amount = amount;
        this.send_from = send_from;
        this.confirmation = confirmation;
        this.time = time;
        this.bottomShown = bottomShown;
    }

    public SendHistoryModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSend_from() {
        return send_from;
    }

    public void setSend_from(String send_from) {
        this.send_from = send_from;
    }

    public int getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(int confirmation) {
        this.confirmation = confirmation;
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
