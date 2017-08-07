package com.service.sedatec.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SedateC on 2017/8/3.
 */

public class ActivityCollector {
    public static List<Activity> activityList = new ArrayList();
    public static void addActivity (Activity activity){
        activityList.add(activity);
    }
    public static void  removeActivity(Activity activity){
        activityList.remove(activity);
    }
    public static void finshAllActivity(){
        for (Activity activity :activityList ){
            if (!activity.isFinishing()){
            activity.finish();
            }
        }
    }
}
