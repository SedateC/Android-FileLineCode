package com.network.sedatec.networktest;

import android.net.http.HttpResponseCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView responseText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendrequestbutton  = (Button)findViewById(R.id.send_request);
        responseText = (TextView)findViewById(R.id.response_text);
        sendrequestbutton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.send_request){
          //  sendRequestWithHttpURLConnection();
            OkHttpUtil.sendHttpRespone("http://192.168.1.5:8088/get_data.json",new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                   String responsedata =  response.body().string();
                    showResponse(responsedata);
                }
            });
        }
    }


    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader read = null;
                try {
                    OkHttpClient client  = new OkHttpClient();
                    Request request =   new Request.Builder()
                            .url("http://192.168.1.5:8088/get_data.json").build();
                    Response reqResponse = client.newCall(request).execute();
                    String responsedata = reqResponse.body().string();
                    parseJSONWithJSONObject(responsedata);
                     /*  showResponse(responsedata);*/
                /*    URL url = new URL("http://www.baidu.com");
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
                    showResponse(sb.toString());*/
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (read!=null){
                        try {
                            read.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
      }

    private void parseJSONWithJSONObject(String responsedata) {
        try{
            JSONArray jasonarray = new JSONArray(responsedata);
            for (int i =0;i<jasonarray.length();i++){
                JSONObject jsonObject = jasonarray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String id = jsonObject.getString("id");
                String version = jsonObject.getString("version");
                Log.d("MainActivity", "parseJSONWithJSONObject:name= "+name);
                Log.d("MainActivity", "parseJSONWithJSONObject:id= "+id);
                Log.d("MainActivity", "parseJSONWithJSONObject:version= "+version);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void showResponse(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(s);
            }
        });
    }


}
