package com.service.sedatec.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by SedateC on 2017/8/3.
 */

public class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("baseActivity", "onCreate: "+getClass().getSimpleName());
        ActivityCollector.addActivity(this);//zai SECONDStart FANGFA CONTENT STR1 STR2...
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
