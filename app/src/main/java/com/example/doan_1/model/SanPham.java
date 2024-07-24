    package com.example.doan_1.model;


    import com.google.firebase.database.IgnoreExtraProperties;

    import java.io.Serializable;
    @IgnoreExtraProperties

    public class SanPham implements Serializable {
        private String surl; // Thay đổi từ int hinhsp sang String imageUrl
        private String ten_sp, mo_ta, ID;
        private int gia, so_luong;

        public SanPham() {

        }

        // Cập nhật constructor để nhận URL hình ảnh
        public SanPham(   int gia, String mo_ta, int so_luong, String surl, String ten_sp, String ID) {

            this.gia = gia;
            this.mo_ta=mo_ta;
            this.so_luong=so_luong;
            this.surl = surl;
            this.ten_sp = ten_sp;
            this.ID=ID;
        }
        public SanPham(String hinh, String ten, int gia, String mota) {
            this.surl = hinh;
            this.ten_sp = ten;
            this.gia = gia;
            this.mo_ta = mota;
        }

        public String getMo_ta() {
            return mo_ta;
        }

        public void setMo_ta(String mo_ta) {
            this.mo_ta = mo_ta;
        }

        public int getSo_luong() {
            return so_luong;
        }

        public void setSo_luong(int so_luong) {
            this.so_luong = so_luong;
        }

        public String getSurl() {
            return surl;
        }
        public void setSurl(String surl) {
            this.surl = surl;
        }
        public String getTen_sp() {
            return ten_sp;
        }

        public void setTen_sp(String ten_sp) {
            this.ten_sp = ten_sp;
        }


        public int getGia() {
            return gia;
        }

        public void setGia(int gia) {
            this.gia = gia;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public void setTen_sp() {
        }

        public void setGia() {
        }
    }
