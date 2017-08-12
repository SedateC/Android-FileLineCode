package com.video.sedatec.playvideotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button palyButton = (Button)findViewById(R.id.play_video);
        Button pauseButton = (Button)findViewById(R.id.pause_video);
        Button replayButton = (Button)findViewById(R.id.replay_video);
         videoView =(VideoView)findViewById(R.id.video_view);
        palyButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        replayButton.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            initVideoPath();

        }
    }

    private void initVideoPath() {
        File file = new File(Environment.getExternalStorageDirectory(),"movie.mp4");
        videoView.setVideoPath(file.getPath());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play_video:
                if (!videoView.isPlaying()){
                    videoView.start();
                }
                break;
            case R.id.pause_video:
                if (videoView.isPlaying()){
                    videoView.pause();
                }
                break;
            case R.id.replay_video:
                if (videoView.isPlaying()){
                    videoView.resume();
                }
                    break;
            default:
                break;
        }
    }

    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    initVideoPath();
                }else {
                    Toast.makeText(this, "you denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}

