package com.example.doan_1.model;

import java.io.Serializable;

public class Shopping implements Serializable {
    private int giaSP;
    private String banchay;
    private int hinhsneaker;

    public Shopping() {
    }

    public Shopping(int giaSP, String banchay, int hinhsneaker) {
        this.giaSP = giaSP;
        this.banchay = banchay;
        this.hinhsneaker = hinhsneaker;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public String getBanchay() {
        return banchay;
    }

    public void setBanchay(String banchay) {
        this.banchay = banchay;
    }

    public int getHinhsneaker() {
        return hinhsneaker;
    }

    public void setHinhsneaker(int hinhsneaker) {
        this.hinhsneaker = hinhsneaker;
    }
}
