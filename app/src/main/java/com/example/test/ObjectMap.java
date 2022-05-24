package com.example.test;

public class ObjectMap {
    private int id;
    private String ten;
    private String mota;
    private byte[]hinh;
    private String lat;
    private String longt;

    public ObjectMap(int id, String ten, String mota, byte[] hinh, String lat, String longt) {
        this.id = id;
        this.ten = ten;
        this.mota = mota;
        this.hinh = hinh;
        this.lat = lat;
        this.longt = longt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongt() {
        return longt;
    }

    public void setLongt(String longt) {
        this.longt = longt;
    }
}
