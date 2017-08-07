package com.service.sedatec.weixinmanager;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.service.sedatec.dbentity.AccountEntity;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by SedateC on 2017/8/6.
 */


public class WeiXinManager {
    private final static String TAG = "QUARY:";
    private SQLiteDatabase db = LitePal.getDatabase();

    public WeiXinManager() {

    }

    public AccountEntity findAccount(String acconunt){
       List<AccountEntity> entities  = DataSupport.where("account = ?",acconunt).find(AccountEntity.class);
        Log.d(TAG, "findAccount: "+entities.get(0));
        return entities.get(0);
    }

    public void addAccount(AccountEntity entity){
        entity.save();
    }

    public void updata(AccountEntity entity){
        AccountEntity entityNew = findAccount(entity.getAccount());
        entityNew.setAccount(entity.getAccount());
        entityNew.setPassword(entity.getPassword());
        entityNew.update(entityNew.getId());
    }

    public void delete( AccountEntity entity){
        AccountEntity entityNew = findAccount(entity.getAccount());
        DataSupport.delete(AccountEntity.class, entityNew.getId());
    }
    public void findByAll(){
        List<AccountEntity> accounts = DataSupport.findAll(AccountEntity.class);
        for (AccountEntity accountEntity:accounts) {
            Log.d("message", "onCreate: "+accountEntity);
        }
    }
}
