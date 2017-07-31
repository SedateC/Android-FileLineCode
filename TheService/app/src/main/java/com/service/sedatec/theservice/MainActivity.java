package com.service.sedatec.theservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MyService.DownloadBinder  downloadBinder ;
    private ServiceConnection serviceConnection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service; //把IBinder service) 转成DownloadBinder
            downloadBinder.getProgress();
            downloadBinder.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startServer = (Button) findViewById(R.id.start_server);
        Button StopServer = (Button) findViewById(R.id.stop_server);
        startServer.setOnClickListener(this);
        StopServer.setOnClickListener(this);
        Button onbindServer = (Button) findViewById(R.id.onbind_server);
        Button unbindServer = (Button) findViewById(R.id.unbind_server);
        onbindServer.setOnClickListener(this);
        unbindServer.setOnClickListener(this);
        Button startIntentService = (Button) findViewById(R.id.start_intent_service);
        startIntentService.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_server:
                Intent intent = new Intent(this,MyService.class);
                startService(intent);

                break;
            case R.id.stop_server:
                Intent stopintent = new Intent(this,MyService.class);
                stopService(stopintent);
                break;
            case R.id.onbind_server:
                Intent bindIntent = new Intent(this,MyService.class);
                bindService(bindIntent,serviceConnection,BIND_AUTO_CREATE);
                break;
            case R.id.unbind_server:
                Intent unbindIntent = new Intent(this,MyService.class);
                unbindService(serviceConnection);
                break;
            case R.id.start_intent_service:
                Log.d("message", "onClick: "+Thread.currentThread().getId());
                Intent intent1  = new Intent(this,MyIntentService.class);
                startService(intent1);
                break;
            default:
                break;
        }
    }
}
