package com.example.doan_1.model;

import java.io.Serializable;

public class Chitiet implements Serializable {
    private String surl; // Thay đổi từ int hinhsp sang String imageUrl
    private String ten_sp;
    private String mo_ta;
    private int gia;


    public Chitiet() {
    }

    public Chitiet(String ten_sp, String mo_ta, String surl, int gia) {
        this.ten_sp = ten_sp;
        this.mo_ta = mo_ta;
        this.surl = surl;
        this.gia = gia;

    }

    public String getTen_sp() {
        return ten_sp;
    }

    public void setTen_sp(String ten_sp) {
        this.ten_sp = ten_sp;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }


}
