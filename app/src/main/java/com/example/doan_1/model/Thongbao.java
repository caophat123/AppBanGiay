package com.example.doan_1.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;


@IgnoreExtraProperties
public class Thongbao implements Serializable {

    public String tenthongbao;
    public  String noidung;

    public String hinh;

    public String datethongbao;

    public Thongbao() {
    }

    //    public Thongbao(String tenthongbao, String noidung, String hinh, String datethongbao) {
//        this.tenthongbao = tenthongbao;
//        this.noidung = noidung;
//        this.hinh = hinh;
//        this.datethongbao = datethongbao;

    public Thongbao(String tenthongbao, String noidung, String datethongbao) {
        this.tenthongbao = tenthongbao;
        this.noidung = noidung;
        this.datethongbao = datethongbao;
    }
//    }

    public String getTenthongbao() {
        return tenthongbao;
    }

    public void setTenthongbao(String tenthongbao) {
        this.tenthongbao = tenthongbao;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getDatethongbao() {
        return datethongbao;
    }

    public void setDatethongbao(String datethongbao) {
        this.datethongbao = datethongbao;
    }
}
