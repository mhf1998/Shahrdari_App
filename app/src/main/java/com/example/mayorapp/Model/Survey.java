package com.example.mayorapp.Model;

import com.google.gson.annotations.SerializedName;

public class Survey {
    @SerializedName("v_id")
    private String v_id;
    @SerializedName("subject")
    private String subject;
    @SerializedName("pic")
    private String pic;
    @SerializedName("number")
    private String number;

    public Survey(String v_id, String subject, String pic, String number) {
        this.v_id = v_id;
        this.subject = subject;
        this.pic = pic;
        this.number = number;
    }

    public Survey() {
    }

    public String getV_id() {
        return v_id;
    }

    public void setV_id(String v_id) {
        this.v_id = v_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
