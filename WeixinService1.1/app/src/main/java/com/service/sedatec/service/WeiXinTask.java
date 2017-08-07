package com.service.sedatec.service;

import android.os.AsyncTask;

/**
 * Created by SedateC on 2017/8/4.
 */

public class WeiXinTask extends AsyncTask<String,Integer,Integer> {
    public static final int TYPE_FAILD=0;
    public static final int TYPE_SUCCESS=1;
    public static final int TYPE_PAUSED=2;
    public static final int TYPE_CANCELD=3;

    private WeixinListener listener;
    private Boolean isCancel =false;
    private Boolean isPaused =false;
    private int lastProgress;

    public WeiXinTask(WeixinListener listener) {
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
        publishProgress();
        return TYPE_FAILD;
    }


}
