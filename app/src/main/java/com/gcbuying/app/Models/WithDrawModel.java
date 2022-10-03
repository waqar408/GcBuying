package com.gcbuying.app.Models;

public class WithDrawModel {


    String id;
    String bankName;
    String accountName;
    String accountNo;
    String receivedAmount;
    String status;
    String createdDate;
    String updatedDate;
    String action;
    private boolean bottomShown;

    public WithDrawModel(String id, String bankName, String accountName, String accountNo, String receivedAmount, String status, String createdDate, String updatedDate, String action, boolean bottomShown) {
        this.id = id;
        this.bankName = bankName;
        this.accountName = accountName;
        this.accountNo = accountNo;
        this.receivedAmount = receivedAmount;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.action = action;
        this.bottomShown = bottomShown;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(String receivedAmount) {
        this.receivedAmount = receivedAmount;
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
