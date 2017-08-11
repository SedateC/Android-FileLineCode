package com.camera.sedatec.cameratest;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    public final static int TAKE_PHOTO =1;
    public final static String TAG ="MESSAGE";
    private ImageView picture;
    private Uri imageuri;
    public static final int CHOOSE_PHOTP = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button takePhoto = (Button)findViewById(R.id.takeph_button);
        picture = (ImageView)findViewById(R.id.image_view);
        takePhoto.setOnClickListener(this);
        Button choose_album = (Button)findViewById(R.id.choose_album);
        choose_album.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.takeph_button:
                //创建对象用于存储拍照后的图片
                File outputimage = new File(getExternalCacheDir(),"output_image.jpg");
                Log.d(TAG, "onClick: "+ getExternalCacheDir().toString());
                try {
                    if (outputimage.exists()){
                        outputimage.delete();
                        outputimage.createNewFile();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT>=24){
                    imageuri = FileProvider.getUriForFile(MainActivity.this,
                            "com.camera.sedatec.cameratest.fileprovider",outputimage);
                }else {
                    imageuri = Uri.fromFile(outputimage);
                }
                //启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
                startActivityForResult(intent,TAKE_PHOTO);
                break;
            case R.id.choose_album:
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE
                )!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                Log.d(TAG, "onActivityResult: _______TAKE_PHOTO________"+(resultCode+"--000-----0000"+RESULT_OK));
                if (resultCode == RESULT_OK){
                    try {
                        //将照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(imageuri));
                        picture.setImageBitmap(bitmap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTP:
                Log.d(TAG, "onActivityResult: ________CHOOSE_PHOTP_______"+(resultCode+"---0000-----0000"+RESULT_OK));
                if (resultCode==RESULT_OK){
                    //判断手机版本号
                    if (Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data);
                    }else {
                        handleImagebeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri  =data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            //如果是DOCUMENT的RUL 则通过DOCUMENT ID处理
            String docID = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                Log.d(TAG, "handleImageOnKitKat:docID "+docID);
                String id = docID.split(":")[1];
                String select = MediaStore.Images.Media._ID+"="+id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,select);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docID));
                imagePath = getImagePath(contentUri, null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
                imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
                imagePath= uri.getPath();
        }
            displayImage(imagePath);
    }
    private void handleImagebeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }
    private void displayImage(String imagePath) {
        if (imagePath !=null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        }else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri externalContentUri, String select) {
        String path =null;
        Cursor cursor = getContentResolver().query(externalContentUri,null,select,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path =cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTP);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this, "you denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


}
