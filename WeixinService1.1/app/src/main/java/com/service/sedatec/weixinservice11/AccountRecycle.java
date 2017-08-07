package com.service.sedatec.weixinservice11;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.service.sedatec.adparter.WxRecyclerAdparter;
import com.service.sedatec.base.BaseActivity;
import com.service.sedatec.dbentity.AccountEntity;
import com.service.sedatec.weixinmanager.WeiXinManager;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class AccountRecycle extends BaseActivity {
    private List<AccountEntity> accountEntityList ;
    public static void startAction(Context context){
        Intent intent = new Intent(context,AccountRecycle.class);
        context.startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_recycle);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.AccountRecycle_layout);
        accountEntityList = DataSupport.findAll(AccountEntity.class);//初始化查询数据
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        WxRecyclerAdparter adparter = new WxRecyclerAdparter(accountEntityList);
        recyclerView.setAdapter(adparter);


    }
}
