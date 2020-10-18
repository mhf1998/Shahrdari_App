package com.example.mayorapp.Model;

import com.google.gson.annotations.SerializedName;

public class MyResponse {
    @SerializedName("response")
    String response;

    public MyResponse(String response) {
        this.response = response;
    }

    public MyResponse() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
