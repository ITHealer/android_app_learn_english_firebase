package com.example.toeicexamapplication.vocabulary;

public class Word {
    String LoaiTu;
    String Nghia;
    String PhienAm;

    public Word() {}

    public Word(String loai,String phien,String nghia)
    {

        this.LoaiTu = loai;
        this.PhienAm = phien;
        this.Nghia = nghia;
    }

    public String getLoaiTu() {
        return LoaiTu;
    }

    public void setLoaiTu(String loaiTu) {
        LoaiTu = loaiTu;
    }

    public String getNghia() {
        return Nghia;
    }

    public void setNghia(String nghia) {
        Nghia = nghia;
    }

    public String getPhienAm() {
        return PhienAm;
    }

    public void setPhienAm(String phienAm) {
        PhienAm = phienAm;
    }
}
