package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

public class WithDrawDataModel {

    @SerializedName("user_id")
    private int user_id;
    @SerializedName("id")
    private int id;
    @SerializedName("trx_id")
    private String trx_id;
    @SerializedName("amount")
    private String amount;
    @SerializedName("bank_name")
    private String bank_name;
    @SerializedName("account_name")
    private String account_name;
    @SerializedName("account_no")
    private String account_no;
    @SerializedName("notes")
    private String notes;
    @SerializedName("phone")
    private String phone;
    @SerializedName("status")
    private String status1;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("user")
    private WithDrawUserModel user;


    public WithDrawDataModel(int user_id, int id, String trx_id, String amount, String bank_name, String account_name, String account_no, String notes, String phone, String status1, String updated_at, String created_at, WithDrawUserModel user) {
        this.user_id = user_id;
        this.id = id;
        this.trx_id = trx_id;
        this.amount = amount;
        this.bank_name = bank_name;
        this.account_name = account_name;
        this.account_no = account_no;
        this.notes = notes;
        this.phone = phone;
        this.status1 = status1;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.user = user;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrx_id() {
        return trx_id;
    }

    public void setTrx_id(String trx_id) {
        this.trx_id = trx_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public WithDrawUserModel getUser() {
        return user;
    }

    public void setUser(WithDrawUserModel user) {
        this.user = user;
    }
}
