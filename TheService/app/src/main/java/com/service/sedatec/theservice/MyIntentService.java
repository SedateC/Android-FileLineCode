package com.service.sedatec.theservice;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by SedateC on 2017/7/30.
 */

public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("MyIntentService","Thread id is  "+Thread.currentThread().getId());
    }
    public void onDestroy(){
        super.onDestroy();
        Log.d("message","ondestrouy");
    }
}
