package com.service.sedatec.service;

import android.accessibilityservice.AccessibilityService;
import android.app.ActivityManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.util.List;

/**
 * Created by SedateC on 2017/8/8.
 */

public class WxMessageService extends AccessibilityService {
    private  final  static String TAG ="message";
    public WxMessageService() {
        super();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType){
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                List<CharSequence> texts = event.getText();
                Log.d(TAG, "texts: "+texts.toString());
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                List<CharSequence> text1 = event.getText();
                Log.d(TAG, "text1:   "+text1.toString());
                break;

        }

    }

    @Override
    public void onInterrupt() {

    }
}
