package com.example.mayorapp.Model;

import com.google.gson.annotations.SerializedName;

public class News {
    @SerializedName("n_id")
    private String n_id;

    @SerializedName("subject")
    private String subject;

    @SerializedName("body")
    private String body;

    @SerializedName("pic")
    private String pic;

    @SerializedName("seen")
    private String seen;

    @SerializedName("date")
    private String date;

    @SerializedName("tag")
    private String tag;

    public News(String n_id, String subject, String body, String pic, String seen, String date, String tag) {
        this.n_id = n_id;
        this.subject = subject;
        this.body = body;
        this.pic = pic;
        this.seen = seen;
        this.date = date;
        this.tag = tag;
    }

    public News() {
    }

    public String getN_id() {
        return n_id;
    }

    public void setN_id(String n_id) {
        this.n_id = n_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

}
