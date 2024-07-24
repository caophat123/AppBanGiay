package com.example.doan_1.model;

import java.io.Serializable;

public class Voucher implements Serializable {
    private int ivvoucher;
    private String  giamgia1;
    private int soluong;

    public Voucher(int ivvoucher, String giamgia1, int soluong) {
        this.ivvoucher = ivvoucher;
        this.giamgia1 = giamgia1;
        this.soluong = soluong;
    }

    public int getIvvoucher() {
        return ivvoucher;
    }

    public void setIvvoucher(int ivvoucher) {
        this.ivvoucher = ivvoucher;
    }

    public String getGiamgia1() {
        return giamgia1;
    }

    public void setGiamgia1(String giamgia1) {
        this.giamgia1 = giamgia1;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public Voucher(){

    }
}