package com.network.sedatec.networktest;

/**
 * Created by SedateC on 2017/7/27.
 */

public interface HttpCallbackListener {
    void onFinsh(String response);
    void onError(Exception e);

}
