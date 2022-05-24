package com.example.test;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ThongBaoTKB extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        Intent intentMusic=new Intent(context, MusicThongBao.class);
        context.startService(intentMusic);

        Intent intentDi=new Intent(context, ActivityThoiKhoaBieu.class);
        intentDi.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentDi.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intentDi,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"thongbaoTKB")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_baseline_alarm_on_24)
                .setContentTitle("Nhắc nhở Thời khóa biểu")
                .setContentText("Đã lâu không găp bạn, soạn TKB đi nào!!")
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(0, builder.build());



    }
}
