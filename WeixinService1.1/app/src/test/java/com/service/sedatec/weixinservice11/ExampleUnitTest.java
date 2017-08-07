package com.service.sedatec.weixinservice11;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.service.sedatec.dbentity.AccountEntity;

import org.junit.Test;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void dbutil()  {
        SQLiteDatabase db =  LitePal.getDatabase();
        List<AccountEntity>  accounts = DataSupport.findAll(AccountEntity.class);
        for(AccountEntity account:accounts){
            Log.d("message", "account: " + account.toString());
        }
    }
}