package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ImageView btnThoikhoabieu, btnSuKien, imgCheckin, imgQuanlichitieu;


    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnThoikhoabieu = (ImageView) findViewById(R.id.imageViewThoiKhoaBieu);
        btnSuKien = (ImageView) findViewById(R.id.imageViewSuKien);
        imgCheckin=(ImageView)findViewById(R.id.imageViewCheckin);
        imgQuanlichitieu=(ImageView)findViewById(R.id.imageViewChitieu);

        calendar=Calendar.getInstance();
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);


        int gio=calendar.get(Calendar.HOUR_OF_DAY);
        int phut=calendar.get(Calendar.MINUTE);

        if(gio == 7){

            Toast.makeText(this, ""+gio, Toast.LENGTH_SHORT).show();

            intent=new Intent(MainActivity.this, ThongBaoTKB.class);
            calendar.set(Calendar.HOUR_OF_DAY, 7);
            calendar.set(Calendar.MINUTE, 05);
            pendingIntent=PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            createNotificationTKB();
        }

        btnThoikhoabieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(MainActivity.this, ActivityThoiKhoaBieu.class);
                startActivity(intent2);

            }
        });

        btnSuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/@10.776087,106.6728363,17z/"));
                Intent intent=new Intent(MainActivity.this, ActivitySuKien.class);
                startActivity(intent);
            }
        });

        imgCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ActivityDanhSachMap.class);
                startActivity(intent);
            }
        });

        imgQuanlichitieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ActivityQuanLyChiTieu.class);
                startActivity(intent);
            }
        });
    }
    private void createNotificationTKB(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name="Thong bao TKB";
            String description="Nhắc nhở soạn TKB";
            int important= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=new NotificationChannel("thongbaoTKB",name,important);
            channel.setDescription(description);

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
