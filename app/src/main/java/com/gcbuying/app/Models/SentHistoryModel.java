package com.gcbuying.app.Models;

public class SentHistoryModel {
    String tvBtc,tvBtcTo,tvDate;

    public SentHistoryModel(String tvBtc, String tvBtcTo, String tvDate) {
        this.tvBtc = tvBtc;
        this.tvBtcTo = tvBtcTo;
        this.tvDate = tvDate;
    }

    public String getTvBtc() {
        return tvBtc;
    }

    public void setTvBtc(String tvBtc) {
        this.tvBtc = tvBtc;
    }

    public String getTvBtcTo() {
        return tvBtcTo;
    }

    public void setTvBtcTo(String tvBtcTo) {
        this.tvBtcTo = tvBtcTo;
    }

    public String getTvDate() {
        return tvDate;
    }

    public void setTvDate(String tvDate) {
        this.tvDate = tvDate;
    }
}
