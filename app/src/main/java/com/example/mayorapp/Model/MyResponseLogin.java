package com.example.mayorapp.Model;

import com.google.gson.annotations.SerializedName;

public class MyResponseLogin {
    @SerializedName("response")
    String response;

    @SerializedName("u_id")
    String u_id;

    public MyResponseLogin(String response,String u_id) {

        this.response = response;
        this.u_id=u_id;

    }

    public MyResponseLogin() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }
}
