package com.example.doan_1.model;

import java.io.Serializable;

public class User implements Serializable {
    String tennhanhang,sodienthoai,diachi,ten,gia,sosp,img,id;
    public int trangThai;


   
    public User() {
    }

    public User(String idGH, String name, String sdt, String diachi, String ten, String gia, String sosp, String img) {
        this.tennhanhang = name;
        this.sodienthoai = sdt;
        this.diachi = diachi;
        this.ten = ten;
        this.gia = gia;
        this.sosp = sosp;
        this.img = img;
        this.id = idGH;
    }

    public User(String idGH, String name, String sdt, String diachi) {
        this.tennhanhang = name;
        this.sodienthoai = sdt;
        this.diachi = diachi;
        this.id = idGH;

    }

    public User(String name, String sdt, String diachi) {
    }

    public String getTennhanhang() {
        return tennhanhang;
    }

    public void setTennhanhang(String tennhanhang) {
        this.tennhanhang = tennhanhang;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getSosp() {
        return sosp;
    }

    public void setSosp(String sosp) {
        this.sosp = sosp;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
