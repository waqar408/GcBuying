package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

public class AmountSentDataModel {
    @SerializedName("recipient")
    String recipient;

    @SerializedName("amount")
    String amount;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
