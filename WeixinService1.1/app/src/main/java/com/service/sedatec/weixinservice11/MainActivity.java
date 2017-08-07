package com.service.sedatec.weixinservice11;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.service.sedatec.base.BaseActivity;
import com.service.sedatec.dbentity.AccountEntity;
import com.service.sedatec.service.WeiXinService;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener{


    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startServer = (Button) findViewById(R.id.button_start);
        Button StopServer = (Button) findViewById(R.id.button_stop);
        startServer.setOnClickListener(this);
        StopServer.setOnClickListener(this);
        Button actionStart = (Button) findViewById(R.id.button_CRUDAccount);
        actionStart.setOnClickListener(this);

        SQLiteDatabase db = LitePal.getDatabase();
        List<AccountEntity> accounts = DataSupport.findAll(AccountEntity.class);
      //  init();
        for (AccountEntity accountEntity:accounts) {
            Log.d("message", "onCreate: "+accountEntity);
        }


    /*   if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BIND_ACCESSIBILITY_SERVICE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.BIND_ACCESSIBILITY_SERVICE},1);
            Log.d("message", "onClick: "+"PERMISSION_GRANTED give");
       }*/

    }

    private void init() {

        DataSupport.deleteAll(AccountEntity.class);
        AccountEntity accountEntity2= new AccountEntity("18477354946","www12345");
        accountEntity2.save();
        AccountEntity accountEntity3= new AccountEntity("CCgentleman","aa84857766");
        accountEntity3.save();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_start:
                Intent  intent = new Intent(this, WeiXinService.class);
                startService(intent);

                break;
            case R.id.button_stop:
                Intent  intent1 = new Intent(this, WeiXinService.class);
               stopService(intent1);
                break;
            case R.id.button_CRUDAccount:
                CRUDAccount.actionStart(MainActivity.this);
                Toast.makeText(this, "actionStartOnclick", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
/*    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                Log.d("message", "onRequestPermissionsResult: "+grantResults[0]+"PACKAGE"+PackageManager.PERMISSION_GRANTED);
                if (grantResults.length>0 && grantResults[0]!= PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "拒绝权限将无法运行程序", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}