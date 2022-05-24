package com.example.test;

public class Sukien {
    private int id;
    private String Tensukien;
    private String Mota;
    private String Diadiem;
    private String Ngay;
    private String Gio;

    public Sukien(int id, String tensukien, String mota, String diadiem, String ngay, String gio) {
        this.id = id;
        Tensukien = tensukien;
        Mota = mota;
        Diadiem = diadiem;
        Ngay = ngay;
        Gio = gio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensukien() {
        return Tensukien;
    }

    public void setTensukien(String tensukien) {
        Tensukien = tensukien;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public String getDiadiem() {
        return Diadiem;
    }

    public void setDiadiem(String diadiem) {
        Diadiem = diadiem;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getGio() {
        return Gio;
    }

    public void setGio(String gio) {
        Gio = gio;
    }
}
