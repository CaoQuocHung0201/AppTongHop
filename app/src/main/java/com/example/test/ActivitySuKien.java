package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ActivitySuKien extends AppCompatActivity {

    ListView listviewSukien;
    AdapterSukien adapter;
    ArrayList<Sukien> arrSukien;

    public static Database databaseSukien;

    Calendar calendar;

    ImageView imgAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_su_kien);

        databaseSukien=new Database(this, "Sukien.sqlite", null, 1);
        databaseSukien.QueryData("CREATE TABLE IF NOT EXISTS SuKienDB (Id INTEGER PRIMARY KEY AUTOINCREMENT, TenSuKien VARCHAR(150), MoTaSuKien VARCHAR(250), DiaDiemSuKien VARCHAR(250), NgaySuKien VARCHAR(150), GioSuKien VARCHAR(150))");

        listviewSukien=(ListView)findViewById(R.id.listviewSukien);
        arrSukien=new ArrayList<>();
        adapter=new AdapterSukien(this, R.layout.dong_sukien, arrSukien);
        listviewSukien.setAdapter(adapter);

        imgAdd=(ImageView)findViewById(R.id.imageAddSukien);

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemSuKien();
            }
        });

        GetDataSukien();

        calendar=Calendar.getInstance();


        int ngayhientai=calendar.get(Calendar.DATE);
        int thanghientai=calendar.get(Calendar.MONTH);
        int giohientai=calendar.get(Calendar.HOUR_OF_DAY);

        String ngaythangtrongmang="", giotrongmang="";

        int ngaytrongmangformat, thangtrongmangformat, giotrongmangformat;



        Cursor cursorNgay=databaseSukien.GetData("SELECT NgaySuKien FROM SuKienDB");
        while (cursorNgay.moveToNext()){

            ngaythangtrongmang=cursorNgay.getString(0);
            String []word=ngaythangtrongmang.split("/");
            ngaytrongmangformat=Integer.valueOf(word[0]);
            thangtrongmangformat=Integer.valueOf(word[1]);

            if(thanghientai==thangtrongmangformat && ngayhientai == ngaytrongmangformat-1){

                AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                calendar.set(Calendar.HOUR_OF_DAY,7);
                calendar.set(Calendar.MINUTE, 00);

                Intent intent=new Intent(ActivitySuKien.this, ThongBaoSuKienNgay.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(ActivitySuKien.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                createNotificationSukien();
            }

        }
        Cursor cursorGio=databaseSukien.GetData("SELECT GioSuKien FROM SuKienDB");

        while (cursorGio.moveToNext()){

            giotrongmang=cursorGio.getString(0);
            String []word=giotrongmang.split(":");
            giotrongmangformat=Integer.valueOf(word[0]);

            if(giohientai == giotrongmangformat-1){

                AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                calendar.set(Calendar.HOUR_OF_DAY,giohientai);
                calendar.set(Calendar.MINUTE, 00);

                Intent intent=new Intent(ActivitySuKien.this, ThongBaoSuKienGio.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(ActivitySuKien.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                createNotificationSukien();
            }
        }

    }


    public void GetDataSukien(){
        Cursor cursor=databaseSukien.GetData("SELECT * FROM SuKienDB");
        arrSukien.clear();
        while (cursor.moveToNext()){
            arrSukien.add(new Sukien(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            ));
        }

        adapter.notifyDataSetChanged();
    }


    public void DialogSuaSuKien(int id,String ten, String mota, String diadiem, String ngay, String gio){
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themsukien);

        EditText edtTen=(EditText) dialog.findViewById(R.id.edtTensukien);
        EditText edtMota=(EditText) dialog.findViewById(R.id.edtMotasukien);
        EditText edtDiadiem=(EditText) dialog.findViewById(R.id.edtDiadiemsukien);
        EditText edtNgay=(EditText) dialog.findViewById(R.id.edtNgaysukien);
        EditText edtGio=(EditText) dialog.findViewById(R.id.edtGiosukien);
        ImageView btnXacNhan=(ImageView) dialog.findViewById(R.id.imgXacNhanSuKien);
        ImageView btnHuy=(ImageView) dialog.findViewById(R.id.imgHuySuKien);

        ImageView imgXoatext=(ImageView)dialog.findViewById(R.id.imgXoaTextSuKien);

        ImageView imgChonNgay=(ImageView)dialog.findViewById(R.id.btnChonNgaysukien);
        ImageView imgChonGio=(ImageView)dialog.findViewById(R.id.btnChonGiosukien);


        imgChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int Ngay=calendar.get(Calendar.DATE);
                int Thang=calendar.get(Calendar.MONTH);
                int Nam=calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(ActivitySuKien.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                        edtNgay.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, Nam, Thang,Ngay);
                datePickerDialog.show();
            }
        });

        imgChonGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int gio=calendar.get(Calendar.HOUR_OF_DAY);
                int phut=calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(ActivitySuKien.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
                        calendar.set(0,0,0,hourOfDay, minute);
                        edtGio.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, gio, phut, true);
                timePickerDialog.show();
            }
        });

        imgXoatext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTen.setText("");
                edtMota.setText("");
                edtDiadiem.setText("");
            }
        });


        edtTen.setText(ten);
        edtMota.setText(mota);
        edtDiadiem.setText(diadiem);
        edtNgay.setText(ngay);
        edtGio.setText(gio);


        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenmoi=edtTen.getText().toString().trim();
                String motamoi=edtMota.getText().toString().trim();
                String diadiemmoi=edtDiadiem.getText().toString().trim();
                String ngaymoi=edtNgay.getText().toString().trim();
                String giomoi=edtGio.getText().toString().trim();
                if(ten.equals("")) Toast.makeText(ActivitySuKien.this, "Nhập tên sự kiện vô!!", Toast.LENGTH_SHORT).show();
                else {
                    databaseSukien.QueryData("UPDATE SuKienDB SET TenSuKien='" + tenmoi + "' WHERE ID='" + id + "'");
                    databaseSukien.QueryData("UPDATE SuKienDB SET MoTaSuKien='" + motamoi + "' WHERE ID='" + id + "'");
                    databaseSukien.QueryData("UPDATE SuKienDB SET DiaDiemSuKien='" + diadiemmoi + "' WHERE ID='" + id + "'");
                    databaseSukien.QueryData("UPDATE SuKienDB SET NgaySuKien='" + ngaymoi + "' WHERE ID='" + id + "'");
                    databaseSukien.QueryData("UPDATE SuKienDB SET GioSuKien='" + giomoi + "' WHERE ID='" + id + "'");
                    Toast.makeText(ActivitySuKien.this, "Đã cập nhật sự kiện ^^!", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    GetDataSukien();
                }
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


    private void DialogThemSuKien(){
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themsukien);
        dialog.show();

        EditText edtTen=(EditText) dialog.findViewById(R.id.edtTensukien);
        EditText edtMota=(EditText) dialog.findViewById(R.id.edtMotasukien);
        EditText edtDiadiem=(EditText) dialog.findViewById(R.id.edtDiadiemsukien);
        EditText edtNgay=(EditText) dialog.findViewById(R.id.edtNgaysukien);
        EditText edtGio=(EditText) dialog.findViewById(R.id.edtGiosukien);
        ImageView btnXacNhan=(ImageView) dialog.findViewById(R.id.imgXacNhanSuKien);
        ImageView btnHuy=(ImageView) dialog.findViewById(R.id.imgHuySuKien);

        ImageView imgXoatext=(ImageView)dialog.findViewById(R.id.imgXoaTextSuKien);

        ImageView imgChonNgay=(ImageView)dialog.findViewById(R.id.btnChonNgaysukien);
        ImageView imgChonGio=(ImageView)dialog.findViewById(R.id.btnChonGiosukien);


        imgChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int Ngay=calendar.get(Calendar.DATE);
                int Thang=calendar.get(Calendar.MONTH);
                int Nam=calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(ActivitySuKien.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                        edtNgay.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, Nam, Thang,Ngay);
                datePickerDialog.show();
            }
        });

        imgChonGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int gio=calendar.get(Calendar.HOUR_OF_DAY);
                int phut=calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(ActivitySuKien.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
                        calendar.set(0,0,0,hourOfDay, minute);
                        edtGio.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, gio, phut, true);
                timePickerDialog.show();
            }
        });

        imgXoatext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTen.setText("");
                edtMota.setText("");
                edtDiadiem.setText("");
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten=edtTen.getText().toString().trim();
                String mota=edtMota.getText().toString().trim();
                String diadiem=edtDiadiem.getText().toString().trim();
                String ngay=edtNgay.getText().toString().trim();
                String gio=edtGio.getText().toString().trim();
                if(ten.equals("")) Toast.makeText(ActivitySuKien.this, "Nhập tên sự kiện vô!!", Toast.LENGTH_LONG).show();
                else{
                    databaseSukien.QueryData("INSERT INTO SuKienDB VALUES(null, '"+ten+"', '"+mota+"', '"+diadiem+"', '"+ngay+"', '"+gio+"')");
                    Toast.makeText(ActivitySuKien.this, "Đã thêm sự kiện ^^!", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    GetDataSukien();
                }

            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void DialogXoaSuKien(String tensukien, int id){
        AlertDialog.Builder dialogXoa=new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa sự kiện : "+tensukien+" không?");

        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseSukien.QueryData("DELETE FROM SuKienDB WHERE ID='"+id+"'");
                Toast.makeText(ActivitySuKien.this, "Đã xóa sự kiện: "+tensukien, Toast.LENGTH_LONG).show();
                GetDataSukien();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }


    private void createNotificationSukien(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name="Thông báo";
            String description="Thông báo sự kiện";
            int important= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=new NotificationChannel("thongbaoSuKien",name,important);
            channel.setDescription(description);

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}