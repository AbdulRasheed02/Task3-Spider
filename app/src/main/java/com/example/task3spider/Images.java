package com.example.task3spider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images {
    @SerializedName("xs")
    @Expose
    private String xs;
    @SerializedName("sm")
    @Expose
    private String sm;
    @SerializedName("md")
    @Expose
    private String md;
    @SerializedName("lg")
    @Expose
    private String lg;

    public String getXs() {
        return xs;
    }

    public void setXs(String xs) {
        this.xs = xs;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public String getLg() {
        return lg;
    }

    public void setLg(String lg) {
        this.lg = lg;
    }
}
