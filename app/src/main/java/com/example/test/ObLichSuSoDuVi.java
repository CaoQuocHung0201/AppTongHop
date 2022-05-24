package com.example.test;

public class ObLichSuSoDuVi {
    private int Id;
    private String Tenvi;
    private String Sotien;
    private String Nhom;
    private String Ghichu;
    private String Ngay;
    private String Trangthai;

    public ObLichSuSoDuVi(int id, String tenvi, String sotien, String nhom, String ghichu, String ngay, String trangthai) {
        Id = id;
        Tenvi = tenvi;
        Sotien = sotien;
        Nhom = nhom;
        Ghichu = ghichu;
        Ngay = ngay;
        Trangthai = trangthai;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenvi() {
        return Tenvi;
    }

    public void setTenvi(String tenvi) {
        Tenvi = tenvi;
    }

    public String getSotien() {
        return Sotien;
    }

    public void setSotien(String sotien) {
        Sotien = sotien;
    }

    public String getNhom() {
        return Nhom;
    }

    public void setNhom(String nhom) {
        Nhom = nhom;
    }

    public String getGhichu() {
        return Ghichu;
    }

    public void setGhichu(String ghichu) {
        Ghichu = ghichu;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getTrangthai() {
        return Trangthai;
    }

    public void setTrangthai(String trangthai) {
        Trangthai = trangthai;
    }
}
