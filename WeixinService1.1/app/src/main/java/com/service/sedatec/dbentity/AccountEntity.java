package com.service.sedatec.dbentity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by SedateC on 2017/8/4.
 */

public class AccountEntity extends DataSupport{

    @Column(unique = true)
    private int id;
    @Column(nullable = false,unique = true)
    private String account;
    @Column(nullable = false)
    private String password;

    public AccountEntity() {
    }

    public AccountEntity(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
