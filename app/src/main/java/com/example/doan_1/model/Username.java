package com.example.doan_1.model;

public class Username {
    private  String username;
    private  String gmail;
    private  String phone;

    public Username() {
    }

    public Username(String username, String gmail, String phone) {
        this.username = username;
        this.gmail = gmail;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
