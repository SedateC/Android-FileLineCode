package com.service.sedatec.servicebestpractice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;

public class DownLoadService extends Service {
    private  DownLoadTask downLoadTask ;
    private String downloadUrl;
    private DownLoadListener downLoadListener = new DownLoadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1,getNotification("downloading",progress));
        }

        @Override
        public void onSuccess() {
            downLoadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("download success",-1));
            Toast.makeText(DownLoadService.this,"Download success",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            downLoadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("download faild",-1));
            Toast.makeText(DownLoadService.this,"Download faild",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downLoadTask = null;
            Toast.makeText(DownLoadService.this,"Download paused",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCanceled() {
            downLoadTask = null;
            stopForeground(true);
            Toast.makeText(DownLoadService.this,"Download Canceled",Toast.LENGTH_SHORT).show();

        }
    };

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {

        public void startDownload(String Url){
            if (downLoadTask==null){
                downloadUrl = Url;
                downLoadTask = new DownLoadTask(downLoadListener);
                downLoadTask.execute(downloadUrl);//三个参数
                startForeground(1,getNotification("downloading...",0));
                Toast.makeText(DownLoadService.this, "Downloading", Toast.LENGTH_SHORT).show();
            }
        }

        public void pausedDownload(){
            if (downLoadTask!=null){
                downLoadTask.pauseDownload();
            }
        }
        public void cancelDownload(){
            if (downLoadTask!=null){
                downLoadTask.cancelDownload();
            }else {
                if(downloadUrl!=null){
                    String filename = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory  = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory+filename);
                    if (file.exists()){
                        file.delete();
                    }
                }
                getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(DownLoadService.this, "cancel download", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public DownLoadService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    private NotificationManager getNotificationManager(){
        return (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }
    private Notification getNotification(String title,int progress){
        Intent intent =new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder  builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
      builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);
        if (progress> 0){
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }
}
