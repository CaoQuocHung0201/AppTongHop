package com.example.test;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //truy van khong tra ket qua: create, insert, update, delete...
    public void QueryData(String sql){
        SQLiteDatabase database= getWritableDatabase();
        database.execSQL(sql);
    }
    //truy van co tra kq: select
    public Cursor GetData(String sql){
        SQLiteDatabase database= getWritableDatabase();//write vhay read deu dc
        return database.rawQuery(sql, null);

    }

    public void insert_map(String ten, String mota, byte[]hinh, String kinhdo, String vido){
        SQLiteDatabase database= getWritableDatabase();
        String sql="INSERT INTO MapDB VALUES(null,?,?,?,?,?)";
        SQLiteStatement sqLiteStatement=database.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1, ten);
        sqLiteStatement.bindString(2, mota);
        sqLiteStatement.bindBlob(3, hinh);
        sqLiteStatement.bindString(4, kinhdo);
        sqLiteStatement.bindString(5, vido);

        sqLiteStatement.executeInsert();
    }

    public void insert_sukien(String ten, String mota, String diadiem, String ngay, String gio){
        SQLiteDatabase database= getWritableDatabase();
        String sql="INSERT INTO SuKienDB VALUES(null,?,?,?,?,?)";
        SQLiteStatement sqLiteStatement=database.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1, ten);
        sqLiteStatement.bindString(2, mota);
        sqLiteStatement.bindString(3, diadiem);
        sqLiteStatement.bindString(4, ngay);
        sqLiteStatement.bindString(5, gio);

        sqLiteStatement.executeInsert();
    }
    public void insert_vi(String ten, String sotien){
        SQLiteDatabase database= getWritableDatabase();
        String sql="INSERT INTO ViTien VALUES(null,?,?)";
        SQLiteStatement sqLiteStatement=database.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1, ten);
        sqLiteStatement.bindString(2, sotien);


        sqLiteStatement.executeInsert();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
