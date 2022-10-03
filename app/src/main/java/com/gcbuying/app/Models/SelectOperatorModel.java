package com.gcbuying.app.Models;

public class SelectOperatorModel {
    private int id;
    private String productName,rate;

    public SelectOperatorModel(int id, String productName, String rate) {
        this.id = id;
        this.productName = productName;
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
