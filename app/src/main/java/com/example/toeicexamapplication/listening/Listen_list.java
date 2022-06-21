package com.example.toeicexamapplication.listening;

public class Listen_list {
    int imgicon;
    int imgbg;
    String bode;

    public Listen_list() {
    }

    public Listen_list(int imgicon, int imgbg, String bode) {
        this.imgicon = imgicon;
        this.imgbg = imgbg;
        this.bode = bode;
    }

    public int getImgicon() {
        return imgicon;
    }

    public void setImgicon(int imgicon) {
        this.imgicon = imgicon;
    }

    public int getImgbg() {
        return imgbg;
    }

    public void setImgbg(int imgbg) {
        this.imgbg = imgbg;
    }

    public String getBode() {
        return bode;
    }

    public void setBode(String bode) {
        this.bode = bode;
    }
}
