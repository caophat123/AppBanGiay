package com.example.doan_1.model;

import java.io.Serializable;

public class Shipnhanh implements Serializable {
    private int hinhshipnhanh;
    private String HDon;
    private String ma;
    private int soluong;
    private String magiam;

    public Shipnhanh(int hinhshipnhanh, String HDon, String ma, int soluong, String magiam) {
        this.hinhshipnhanh = hinhshipnhanh;
        this.HDon = HDon;
        this.ma = ma;
        this.soluong = soluong;
        this.magiam = magiam;
    }

    public int getHinhshipnhanh() {
        return hinhshipnhanh;
    }

    public void setHinhshipnhanh(int hinhshipnhanh) {
        this.hinhshipnhanh = hinhshipnhanh;
    }

    public String getHDon() {
        return HDon;
    }

    public void setHDon(String HDon) {
        this.HDon = HDon;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getMagiam() {
        return magiam;
    }

    public void setMagiam(String magiam) {
        this.magiam = magiam;
    }
}