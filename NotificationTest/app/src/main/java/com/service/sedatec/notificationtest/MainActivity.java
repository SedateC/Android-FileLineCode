package com.service.sedatec.notificationtest;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG="message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendButton =(Button)findViewById(R.id.send_notification);
        sendButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_notification:
                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                notificationManager.cancel(1);//点击取消方式2
                Intent intent = new Intent(this,NotificationActivity.class);
                PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
                Notification notification = new NotificationCompat.Builder(this)
                        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/luna.ogg")))
                        //.setVibrate(new long[]{0,1000,1000,1000}) 震动
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentTitle("this is contentTitle")
                        .setWhen(System.currentTimeMillis())
                        .setContentText("learn how to build notifications send" +
                                "syndata and use voice actions get android IDE and developer tool to build app for Android")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setContentIntent(pi)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("learn how to build notifications send" +
                                "syndata and use voice actions get android IDE and developer tool to build app for Android")) //can't setContent
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
                notificationManager.notify(1,notification);

                ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
                Log.d(TAG, "onClick: "+activityManager.toString());
                break;
            default:
                break;
        }
    }
}
