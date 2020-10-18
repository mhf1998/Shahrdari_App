package com.example.mayorapp.Model;

import com.google.gson.annotations.SerializedName;

public class Option {
    @SerializedName("option")
    private String option;
    @SerializedName("num")
    private int num;

    public Option(String option, int num) {
        this.option = option;
        this.num = num;
    }

    public Option() {
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
