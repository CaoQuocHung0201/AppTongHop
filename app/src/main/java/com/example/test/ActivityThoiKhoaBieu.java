package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static android.graphics.Color.BLACK;

public class ActivityThoiKhoaBieu extends AppCompatActivity {


    public static Database database;
    GridView gridTKB;
    ArrayList<ThoiKhoaBieu> arrTKB;
    Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoi_khoa_bieu);




        gridTKB=(GridView)findViewById(R.id.gridViewTKB);
        arrTKB=new ArrayList<>();

        adapter= new Adapter(this, R.layout.dong_thoikhoabieu, arrTKB);
        gridTKB.setAdapter(adapter);

        database=new Database(this, "thoikhoabieu.sqlite", null,1);
        database.QueryData("CREATE TABLE IF NOT EXISTS TKB(Id INTEGER PRIMARY KEY, Thu VARCHAR(200), TKBSang VARCHAR (200), TKBChieu VARCHAR(200))");



        Cursor data=database.GetData("SELECT * FROM TKB");
        int total=data.getCount();
        if(total==0){
            AnhXa();
            GetDataTKB();
        }else GetDataTKB();


    }
    private void GetDataTKB(){
        //select data
        Cursor dataTKB=database.GetData("SELECT * FROM TKB");
        arrTKB.clear();
        while (dataTKB.moveToNext()){

            int id=dataTKB.getInt(0);
            String thu=dataTKB.getString(1);
            String monhocSang=dataTKB.getString(2);
            String monhocChieu=dataTKB.getString(3);

            arrTKB.add(new ThoiKhoaBieu(id,thu, monhocSang, monhocChieu));
        }
        adapter.notifyDataSetChanged();
    }


    public void DialogSuaTKB(int id, String monsang, String monchieu){

        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_suatkb);

        EditText editMonhocsang=(EditText)dialog.findViewById(R.id.edtMonhocsang);
        EditText editMonhocchieu=(EditText)dialog.findViewById(R.id.edtMonhocchieu);

        ImageView xoatext=(ImageView)dialog.findViewById(R.id.imgXoaTextSuaTKB);

        xoatext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMonhocsang.setText("");
                editMonhocchieu.setText("");
            }
        });


        ImageView btnXacNhan=(ImageView)dialog.findViewById(R.id.imgXacNhanSuaTKB);
        ImageView btnHuy=(ImageView)dialog.findViewById(R.id.imgHuySuaTKB);

        editMonhocsang.setText(monsang);
        editMonhocchieu.setText(monchieu);


        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String monhocsangnew=editMonhocsang.getText().toString().trim();
                String monhocchieunew=editMonhocchieu.getText().toString().trim();
                database.QueryData("UPDATE TKB SET TKBSang='"+monhocsangnew+" 'WHERE ID='"+id+"'");
                database.QueryData("UPDATE TKB SET TKBChieu='"+monhocchieunew+" 'WHERE ID='"+id+"'");
                Toast.makeText(ActivityThoiKhoaBieu.this, "Đã thêm môn học nha!!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                GetDataTKB();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void DialogXoaTKB(int id, String thu){
        String rong="";
        AlertDialog.Builder dialogXoa=new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa dữ liệu môn học của "+thu+" không?");

        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("UPDATE TKB SET TKBSang='"+rong+"' WHERE ID='"+id+"'");
                database.QueryData("UPDATE TKB SET TKBChieu='"+rong+"' WHERE ID='"+id+"'");
                Toast.makeText(ActivityThoiKhoaBieu.this, "Đã xóa môn học của: "+thu, Toast.LENGTH_LONG).show();
                GetDataTKB();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }



    private void AnhXa(){
        database.QueryData("insert into TKB values (2,'Thứ 2', 'Nhập TKB sáng', 'Nhập TKB chiều')");
        database.QueryData("insert into TKB values (3,'Thứ 3', 'Nhập TKB sáng', 'Nhập TKB chiều')");
        database.QueryData("insert into TKB values (4,'Thứ 4', 'Nhập TKB sáng', 'Nhập TKB chiều')");
        database.QueryData("insert into TKB values (5,'Thứ 5', 'Nhập TKB sáng', 'Nhập TKB chiều')");
        database.QueryData("insert into TKB values (6,'Thứ 6', 'Nhập TKB sáng', 'Nhập TKB chiều')");
        database.QueryData("insert into TKB values (7,'Thứ 7', 'Nhập TKB sáng', 'Nhập TKB chiều')");
//        arrTKB.add(new ThoiKhoaBieu(2,"Thứ 2", "Nhập vô đi nè", "Nhập vô đi nè"));
//        arrTKB.add(new ThoiKhoaBieu(3,"Thứ 3", "Nhập vô đi nè", "Nhập vô đi nè"));
//        arrTKB.add(new ThoiKhoaBieu(4,"Thứ 4", "Nhập vô đi nè", "Nhập vô đi nè"));
//        arrTKB.add(new ThoiKhoaBieu(5,"Thứ 5", "Nhập vô đi nè", "Nhập vô đi nè"));
//        arrTKB.add(new ThoiKhoaBieu(6,"Thứ 6", "Nhập vô đi nè", "Nhập vô đi nè"));
//        arrTKB.add(new ThoiKhoaBieu(7,"Thứ 7", "Nhập vô đi nè", "Nhập vô đi nè"));
    }


}