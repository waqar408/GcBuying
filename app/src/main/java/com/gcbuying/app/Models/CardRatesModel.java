package com.gcbuying.app.Models;

public class CardRatesModel {

    private int id;
    private String name, per_rate, dolar,image;

    public CardRatesModel(int id, String name, String per_rate, String image) {
        this.id = id;
        this.name = name;
        this.per_rate = per_rate;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPer_rate() {
        return per_rate;
    }

    public void setPer_rate(String per_rate) {
        this.per_rate = per_rate;
    }

    public String getDolar() {
        return dolar;
    }

    public void setDolar(String dolar) {
        this.dolar = dolar;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
