package com.service.sedatec.theservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {
    private DownloadBinder mBinder = new DownloadBinder();
        @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d("MyService","start download excuted");
        }
        public int getProgress(){
            Log.d("MyService","getProgress excuted");
            return 0;
        }

    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("message","start server");
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        Notification  notification =new NotificationCompat.Builder(this).setContentTitle("this is contentTitle")
                .setContentText("this is contenxtext")
                .setContentInfo("this is contenxt info")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent).build();
                startForeground(1,notification);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("message","stop server");
        super.onDestroy();
    }


}
