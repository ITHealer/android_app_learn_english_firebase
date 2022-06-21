package com.example.toeicexamapplication.listening;

import java.io.Serializable;

public class Listening implements Serializable {
    private String A;
    private String B;
    private String C;
    private String D;
    private String True;
    private String Image;
    private String Audio;

    public Listening() {
    }

    public Listening(String a, String b, String c, String d, String aTrue, String image, String audio) {
        A = a;
        B = b;
        C = c;
        D = d;
        True = aTrue;
        Image = image;
        Audio = audio;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getTrue() {
        return True;
    }

    public void setTrue(String aTrue) {
        True = aTrue;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAudio() {
        return Audio;
    }

    public void setAudio(String audio) {
        Audio = audio;
    }
}
