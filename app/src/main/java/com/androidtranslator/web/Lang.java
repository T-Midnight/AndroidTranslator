package com.androidtranslator.web;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Lang {
    @SerializedName("dirs")
    @Expose
    private String[] dirs;

    @SerializedName("langs")
    @Expose
    private Map<String, String> langs;

    public void setLangs(Map<String, String> langs) {
        this.langs = langs;
    }

    public void setDirs(String[] dirs) {
        this.dirs = dirs;
    }

    public Map<String, String> getLangs() {
        return langs;
    }

    public String[] getDirs() {
        return dirs;
    }
}
