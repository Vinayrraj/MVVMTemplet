package com.vinay.library.data.user;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
