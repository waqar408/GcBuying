package com.gcbuying.app.Models;

public class TradesModel {


    int id;
  String total_amount,card_id,rate,get_amount,comment,method,note,status1,txn_id,created_at,updated_at,action;
  Boolean isBottomShown;

    public TradesModel(int id, String total_amount, String card_id, String rate, String get_amount, String comment, String method,
                       String note, String status1, String txn_id, String created_at, String updated_at, String action, Boolean isBottomShown) {
        this.id = id;
        this.total_amount = total_amount;
        this.card_id = card_id;
        this.rate = rate;
        this.get_amount = get_amount;
        this.comment = comment;
        this.method = method;
        this.note = note;
        this.status1 = status1;
        this.txn_id = txn_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.action = action;
        this.isBottomShown = isBottomShown;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Boolean getBottomShown() {
        return isBottomShown;
    }

    public void setBottomShown(Boolean bottomShown) {
        isBottomShown = bottomShown;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getGet_amount() {
        return get_amount;
    }

    public void setGet_amount(String get_amount) {
        this.get_amount = get_amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
