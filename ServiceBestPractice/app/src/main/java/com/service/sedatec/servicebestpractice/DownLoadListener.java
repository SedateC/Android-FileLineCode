package com.service.sedatec.servicebestpractice;

/**
 * Created by SedateC on 2017/7/30.
 */

public interface DownLoadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
