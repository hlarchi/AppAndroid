package com.example.app2;

public class Site {
    private String url;
    private int note;

    public Site(String url, int note) {
        this.url = url;
        this.note = note;
    }

    public String getUrl() {
        return url;
    }

    public int getNote() {
        return note;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setNote(int note) {
        this.note = note;
    }
}
