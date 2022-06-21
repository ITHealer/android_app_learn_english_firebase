package com.example.toeicexamapplication.account;

public class User {
    private String idUser;
    private String hoTen;
    private String email;
    private String sdt;
    private int pointReading;
    private int pointListening;
    private int sumPoint;

    public User() {
        // firebase
    }

    public User(String idUser, String hoTen, String email, String sdt, int pointReading, int pointListening, int sumPoint) {
        this.idUser = idUser;
        this.hoTen = hoTen;
        this.email = email;
        this.sdt = sdt;
        this.pointReading = pointReading;
        this.pointListening = pointListening;
        this.sumPoint = sumPoint;
    }

    public int getSumPoint() {
        return sumPoint;
    }

    public void setSumPoint(int sumPoint) {
        this.sumPoint = sumPoint;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getPointReading() {
        return pointReading;
    }

    public void setPointReading(int pointReading) {
        this.pointReading = pointReading;
    }

    public int getPointListening() {
        return pointListening;
    }

    public void setPointListening(int pointListening) {
        this.pointListening = pointListening;
    }
}
