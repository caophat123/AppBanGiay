package com.example.doan_1.model;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_1.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Products {

    String ten_sp,  mo_ta, surl, id;
    Integer gia, so_luong;

    public Products(){}
    public Products( String id, String ten_sp, Integer gia, Integer so_luong, String mo_ta, String surl) {
        this.ten_sp = ten_sp;
        this.gia = gia;
        this.so_luong = so_luong;
        this.mo_ta = mo_ta;
        this.surl = surl;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public String getTen_sp() {
        return ten_sp;
    }

    public void setTen_sp(String ten_sp) {
        this.ten_sp = ten_sp;
    }

    public Integer getGia() {
        return gia;
    }

    public void setGia(Integer gia) {
        this.gia = gia;
    }

    public Integer getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(Integer so_luong) {
        this.so_luong = so_luong;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

}
