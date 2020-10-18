package com.example.mayorapp.Model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("u_id")
    private String u_id;
    @SerializedName("name")
    private String name;
    @SerializedName("lname")
    private String lname;
    @SerializedName("meli")
    private String meli;
    @SerializedName("phone")
    private String phone;
    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("region")
    private String region;
    @SerializedName("status")
    private String status;

    public User(String u_id, String name, String lname, String meli, String phone, String email, String username, String password, String region, String status) {
        this.u_id = u_id;
        this.name = name;
        this.lname = lname;
        this.meli = meli;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
        this.region = region;
        this.status = status;
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMeli() {
        return meli;
    }

    public void setMeli(String meli) {
        this.meli = meli;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
