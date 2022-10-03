package com.gcbuying.app.Models;

public class HistoryDummyModel {
    String itemName,price,priceinNGN,date;

    public HistoryDummyModel(String itemName, String price, String priceinNGN, String date) {
        this.itemName = itemName;
        this.price = price;
        this.priceinNGN = priceinNGN;
        this.date = date;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceinNGN() {
        return priceinNGN;
    }

    public void setPriceinNGN(String priceinNGN) {
        this.priceinNGN = priceinNGN;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

