package com.gcbuying.app.Models;

public class RechargeModel {


    int id;
    String bankName;
    String phonenumber;
    String nairaAmount;
    String btcAmount;
    String usdAmount;
    String status;
    String createdDate;
    String updatedDate;
    String action;
    private boolean bottomShown;

    public RechargeModel(int id, String bankName, String phonenumber, String nairaAmount, String btcAmount, String usdAmount, String status, String createdDate, String updatedDate, String action) {
        this.id = id;
        this.bankName = bankName;
        this.phonenumber = phonenumber;
        this.nairaAmount = nairaAmount;
        this.btcAmount = btcAmount;
        this.usdAmount = usdAmount;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.action = action;
        this.bottomShown = bottomShown;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getNairaAmount() {
        return nairaAmount;
    }

    public void setNairaAmount(String nairaAmount) {
        this.nairaAmount = nairaAmount;
    }

    public String getBtcAmount() {
        return btcAmount;
    }

    public void setBtcAmount(String btcAmount) {
        this.btcAmount = btcAmount;
    }

    public String getUsdAmount() {
        return usdAmount;
    }

    public void setUsdAmount(String usdAmount) {
        this.usdAmount = usdAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isBottomShown() {
        return bottomShown;
    }

    public void setBottomShown(boolean bottomShown) {
        this.bottomShown = bottomShown;
    }
}
