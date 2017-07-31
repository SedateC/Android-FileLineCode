package com.service.sedatec.servicebestpractice;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by SedateC on 2017/7/30.
 */

public class DownLoadTask extends AsyncTask<String,Integer,Integer>{
    public static final int TYPE_FAILD=0;
    public static final int TYPE_SUCCESS=1;
    public static final int TYPE_PAUSED=2;
    public static final int TYPE_CANCELD=3;

    private DownLoadListener listener;
    private Boolean isCancel =false;
    private Boolean isPaused =false;
    private int lastProgress;

    public DownLoadTask(DownLoadListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Integer status) {//结果 接收本类返回的结果
        switch (status){
            case TYPE_SUCCESS:
                listener.onSuccess();
            break;
              case TYPE_PAUSED:
                  listener.onPaused();
            break;
              case TYPE_CANCELD :
                  listener.onCanceled();
            break;
              case TYPE_FAILD:
                  listener.onFailed();
            break;
            default:
                break;

        }
    }
    public void pauseDownload(){
        isPaused = true;
    }

    public void cancelDownload(){
        isCancel =true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {//进度 pauseDownload方法传入
        int progress = values[0];
        if (progress>lastProgress){
            listener.onProgress(progress);
            lastProgress = progress;
        }

    }

    @Override
    protected void onCancelled(Integer integer) {
        super.onCancelled(integer);
    }

    @Override
    protected Integer doInBackground(String... params) {//参数类型 execute
        InputStream in = null;
        RandomAccessFile  saveFile =null;
        File file = null;
        try{
            long downLoadLength =0;
            String downloadUrl = params[0];
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory+fileName);
            if (file.exists()){
                downLoadLength = file.length();
            }
            long contentLength = getContentLength(downloadUrl);
            if (contentLength==0){
                return TYPE_FAILD;
            }else if(contentLength == downLoadLength){
                return TYPE_SUCCESS;
            }
            OkHttpClient client = new OkHttpClient();
            Request request  = new Request.Builder()
                    .addHeader("RANGE","bytes="+downLoadLength+"-")
                    .url(downloadUrl).build();
            Response response = client.newCall(request).execute();
            if (response!=null){
                in =response.body().byteStream();
                saveFile = new RandomAccessFile(file,"rw");
                saveFile.seek(downLoadLength);//跳过已经下载的字节
                byte[] b = new byte[512];
                int total = 0;
                int len;
                while ((len = in.read(b)) !=-1){
                    if (isCancel){
                        return TYPE_CANCELD;
                    }else if (isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total+=len;
                        saveFile.write(b,0,len);

                        //计算百分比
                        int progress = (int)((total+downLoadLength)*100/contentLength);
                        publishProgress(progress); //进度传到pauseDownload方法
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (in!=null){

                    in.close();
                }
                if (saveFile!=null){
                    saveFile.close();
                }if (isCancel&&file!=null){
                    file.delete();
                }
            }catch (Exception  e){
                e.printStackTrace();
            }
        }
        return TYPE_FAILD;
    }

    private long getContentLength(String downloadUrl) throws IOException{
            OkHttpClient client = new OkHttpClient();
            Request request = new  Request.Builder().url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response!=null&&response.isSuccessful()){
                long contentLength = response.body().contentLength();
                response.close();
                return contentLength;
            }
            return 0;
    }
}
