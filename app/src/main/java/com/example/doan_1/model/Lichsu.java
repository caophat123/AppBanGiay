package com.example.doan_1.model;

import java.io.Serializable;

public class Lichsu implements Serializable {
    private String tenLS, maLS, hinhLS;
    private String giaLS, soluongLS;

    public Lichsu() {
    }

    public Lichsu(String tenLS2, String hinhLS2, String giaLS2, String maLS2, String soluongLS2) {
        this.tenLS=tenLS2;
        this.hinhLS=hinhLS2;
        this.giaLS=giaLS2;
        this.maLS=maLS2;
        this.soluongLS=soluongLS2;
    }


    public String getTenLS() {
        return tenLS;
    }

    public void setTenLS(String tenLS) {
        this.tenLS = tenLS;
    }

    public String getMaLS() {
        return maLS;
    }

    public void setMaLS(String maLS) {
        this.maLS = maLS;
    }

    public String getHinhLS() {
        return hinhLS;
    }

    public void setHinhLS(String hinhLS) {
        this.hinhLS = hinhLS;
    }

    public String getGiaLS() {
        return giaLS;
    }

    public void setGiaLS(String giaLS) {
        this.giaLS = giaLS;
    }

    public String getSoluongLS() {
        return soluongLS;
    }

    public void setSoluongLS(String soluongLS) {
        this.soluongLS = soluongLS;
    }
}
