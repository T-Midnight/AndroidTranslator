package com.androidtranslator.web;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Translation {
    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("lang")
    @Expose
    private String lang;

    @SerializedName("text")
    @Expose
    private String[] text;

    public void setCode(int code) {
        this.code = code;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setText(String[] text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String getLang() {
        return lang;
    }

    public String[] getText() {
        return text;
    }
}
