package com.example.test;

import java.io.Serializable;

public class ObjectVi implements Serializable {
    private int Id;
    private String Ten;
    private String Sotien;

    public ObjectVi(int id, String ten, String sotien) {
        Id = id;
        Ten = ten;
        Sotien = sotien;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getSotien() {
        return Sotien;
    }

    public void setSotien(String sotien) {
        Sotien = sotien;
    }
}
