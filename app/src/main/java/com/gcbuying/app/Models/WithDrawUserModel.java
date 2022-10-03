package com.gcbuying.app.Models;

import com.google.gson.annotations.SerializedName;

public class WithDrawUserModel {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("email_verified_at")
    private String email_verified_at;
    @SerializedName("phone")
    private String phone;
    @SerializedName("balance")
    private String balance;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("banned_until")
    private String banned_until;
}
