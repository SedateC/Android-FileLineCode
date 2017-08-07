package com.service.sedatec.adparter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.service.sedatec.dbentity.AccountEntity;
import com.service.sedatec.weixinservice11.R;

import java.util.List;

/**
 * Created by SedateC on 2017/8/6.
 */

public class WxRecyclerAdparter extends RecyclerView.Adapter<WxRecyclerAdparter.ViewHolder> {
    private List<AccountEntity> Accounts;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View accountView;
        TextView AccountName;
        public ViewHolder(View view){
            super(view);
            accountView=view;
            AccountName = (TextView) view.findViewById(R.id.fruit_name);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accountitem_layout,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.accountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                AccountEntity entity = Accounts.get(position);
                Toast.makeText(v.getContext(),"you clicked view"+entity.getAccount(),Toast.LENGTH_SHORT).show();
            }
        });

        return viewHolder;
    }

    public WxRecyclerAdparter(List<AccountEntity> Accountlist){
        Accounts=Accountlist;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AccountEntity entity = Accounts.get(position);
        holder.AccountName.setText(entity.getAccount());

    }




    @Override
    public int getItemCount() {
        return Accounts.size();
    }
}

