package com.gcbuying.app.Models;

public class ReceivedHistoryModel {


    String id;
    String amount;
    String received_from;
    String confirmation;
    String time;
    private boolean bottomShown;

    public ReceivedHistoryModel(String id, String amount, String received_from, String confirmation, String time, boolean bottomShown) {
        this.id = id;
        this.amount = amount;
        this.received_from = received_from;
        this.confirmation = confirmation;
        this.time = time;
        this.bottomShown = bottomShown;
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

    public String getReceived_from() {
        return received_from;
    }

    public void setReceived_from(String received_from) {
        this.received_from = received_from;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
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
