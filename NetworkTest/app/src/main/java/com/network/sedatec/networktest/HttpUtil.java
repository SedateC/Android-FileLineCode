package com.network.sedatec.networktest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by SedateC on 2017/7/27.
 */

public class HttpUtil {
    public static void sendHttpRespone (final String address,final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader read;
                try{
                    URL url = new URL(address);
                    connection  = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(7000);
                    InputStream in = connection.getInputStream();
                    read = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line=read.readLine())!=null){
                        sb.append(line);
                    }
                    if (listener!=null){
                        listener.onFinsh(sb.toString());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if (listener!=null){
                        listener.onError(e);
                    }
                }finally {
                    if (connection !=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }
}
