package com.network.sedatec.networktest;



import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by SedateC on 2017/7/27.
 */

public class OkHttpUtil {
    public static void sendHttpRespone (final String address,okhttp3.Callback callback) {
        OkHttpClient client  = new OkHttpClient();
        Request request =   new Request.Builder()
                .url(address).build();
         client.newCall(request).enqueue(callback);
    }
}
