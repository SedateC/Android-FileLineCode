package com.service.sedatec.service;

/**
 * Created by SedateC on 2017/8/4.
 */

public interface WeixinListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
