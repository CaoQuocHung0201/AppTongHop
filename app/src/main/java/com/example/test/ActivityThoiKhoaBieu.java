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
                Toast.makeText(ActivityThoiKhoaBieu.this, "???? th??m m??n h???c nha!!", Toast.LENGTH_LONG).show();
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
        dialogXoa.setMessage("B???n c?? mu???n x??a d??? li???u m??n h???c c???a "+thu+" kh??ng?");

        dialogXoa.setPositiveButton("C??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("UPDATE TKB SET TKBSang='"+rong+"' WHERE ID='"+id+"'");
                database.QueryData("UPDATE TKB SET TKBChieu='"+rong+"' WHERE ID='"+id+"'");
                Toast.makeText(ActivityThoiKhoaBieu.this, "???? x??a m??n h???c c???a: "+thu, Toast.LENGTH_LONG).show();
                GetDataTKB();
            }
        });
        dialogXoa.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }



    private void AnhXa(){
        database.QueryData("insert into TKB values (2,'Th??? 2', 'Nh???p TKB s??ng', 'Nh???p TKB chi???u')");
        database.QueryData("insert into TKB values (3,'Th??? 3', 'Nh???p TKB s??ng', 'Nh???p TKB chi???u')");
        database.QueryData("insert into TKB values (4,'Th??? 4', 'Nh???p TKB s??ng', 'Nh???p TKB chi???u')");
        database.QueryData("insert into TKB values (5,'Th??? 5', 'Nh???p TKB s??ng', 'Nh???p TKB chi???u')");
        database.QueryData("insert into TKB values (6,'Th??? 6', 'Nh???p TKB s??ng', 'Nh???p TKB chi???u')");
        database.QueryData("insert into TKB values (7,'Th??? 7', 'Nh???p TKB s??ng', 'Nh???p TKB chi???u')");
//        arrTKB.add(new ThoiKhoaBieu(2,"Th??? 2", "Nh???p v?? ??i n??", "Nh???p v?? ??i n??"));
//        arrTKB.add(new ThoiKhoaBieu(3,"Th??? 3", "Nh???p v?? ??i n??", "Nh???p v?? ??i n??"));
//        arrTKB.add(new ThoiKhoaBieu(4,"Th??? 4", "Nh???p v?? ??i n??", "Nh???p v?? ??i n??"));
//        arrTKB.add(new ThoiKhoaBieu(5,"Th??? 5", "Nh???p v?? ??i n??", "Nh???p v?? ??i n??"));
//        arrTKB.add(new ThoiKhoaBieu(6,"Th??? 6", "Nh???p v?? ??i n??", "Nh???p v?? ??i n??"));
//        arrTKB.add(new ThoiKhoaBieu(7,"Th??? 7", "Nh???p v?? ??i n??", "Nh???p v?? ??i n??"));
    }


}