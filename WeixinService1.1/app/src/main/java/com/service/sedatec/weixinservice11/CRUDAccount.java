package com.service.sedatec.weixinservice11;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.service.sedatec.base.BaseActivity;
import com.service.sedatec.dbentity.AccountEntity;
import com.service.sedatec.weixinmanager.WeiXinManager;

import org.litepal.crud.DataSupport;

public class CRUDAccount extends BaseActivity implements View.OnClickListener {
    WeiXinManager weiXinManager = new WeiXinManager();
 /*   private SharedPreferences pref ;
    private SharedPreferences.Editor editor;*/
    EditText accountEdit;
    EditText passWordEidt;
    Button button_queryAllAcount;
    Button button_addAccount;
    Button button_updateAcount;
    Button button_deleteAcount;


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CRUDAccount.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crudaccount_layout);
        accountEdit = (EditText) findViewById(R.id.edit_Account);
        accountEdit.setOnClickListener(this);
        passWordEidt = (EditText) findViewById(R.id.edit_password);
        passWordEidt.setOnClickListener(this);

        button_queryAllAcount = (Button) findViewById(R.id.button_queryAllAcount);
        button_addAccount = (Button) findViewById(R.id.button_addAccount);
        button_updateAcount = (Button) findViewById(R.id.button_updateAcount);
        button_deleteAcount = (Button) findViewById(R.id.button_deleteAcount);
        button_queryAllAcount.setOnClickListener(this);
        button_addAccount.setOnClickListener(this);
        button_updateAcount.setOnClickListener(this);
        button_deleteAcount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_queryAllAcount:
                weiXinManager.findByAll();
                Toast.makeText(this, "button_queryAllAcount", Toast.LENGTH_SHORT).show();
                AccountRecycle.startAction(CRUDAccount.this);
                break;
            case R.id.button_addAccount:
                String accountAdd = accountEdit.getText().toString();
                String passwordAdd = passWordEidt.getText().toString();
                AccountEntity entityAdd = new AccountEntity(accountAdd,passwordAdd);
                weiXinManager.addAccount(entityAdd);
                Toast.makeText(this, "weiXinManager.addAccount(entityAdd);", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_updateAcount:
                String accountUpdate = accountEdit.getText().toString();
                String passwordUpdate = passWordEidt.getText().toString();
                AccountEntity entityUpdate = new AccountEntity(accountUpdate,passwordUpdate);
                weiXinManager.updata(entityUpdate);
                Toast.makeText(this, " weiXinManager.updata(entityUpdate);", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_deleteAcount:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CRUDAccount.this);
                alertDialog.setTitle("this is AletDialog");
                alertDialog.setMessage("someting important");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String accountDel = accountEdit.getText().toString();
                        String passwordDel = passWordEidt.getText().toString();
                        AccountEntity entityDel = new AccountEntity(accountDel,passwordDel);
                        Log.d("message", "onClick: "+entityDel.getAccount()+"---"+entityDel.getPassword());
                        weiXinManager.delete(entityDel);

                    }
                });
                alertDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
                Toast.makeText(this, "weiXinManager.delete(entityDel);", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }



}
