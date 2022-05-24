package com.example.test;

public class ThoiKhoaBieu {
    private int Id;
    private String Thu;
    private String ThoiKhoaBieuSang;
    private String ThoiKhoaBieuChieu;

    public ThoiKhoaBieu(int id, String thu, String thoiKhoaBieuSang, String thoiKhoaBieuChieu) {
        Id=id;
        Thu = thu;
        ThoiKhoaBieuSang = thoiKhoaBieuSang;
        ThoiKhoaBieuChieu = thoiKhoaBieuChieu;
    }

    public String getThu() {
        return Thu;
    }

    public void setThu(String thu) {
        Thu = thu;
    }

    public String getThoiKhoaBieuSang() {
        return ThoiKhoaBieuSang;
    }

    public void setThoiKhoaBieuSang(String thoiKhoaBieuSang) {
        ThoiKhoaBieuSang = thoiKhoaBieuSang;
    }

    public String getThoiKhoaBieuChieu() {
        return ThoiKhoaBieuChieu;
    }

    public void setThoiKhoaBieuChieu(String thoiKhoaBieuChieu) {
        ThoiKhoaBieuChieu = thoiKhoaBieuChieu;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
