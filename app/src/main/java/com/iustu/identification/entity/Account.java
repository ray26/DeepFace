package com.iustu.identification.entity;

import android.content.ContentValues;

import com.google.gson.Gson;
import com.iustu.identification.util.MSP;

import retrofit2.http.GET;

/**
 * created by sgh, 2019-4-2
 *
 * 用来记录登录账户的数据表
 */
public class Account {
    public String name = "admin";     // 作为主键
    public String password = "123456";  // not null, 且最多10位，不含中文字符

    public Account(){}
    public Account(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", this.name);
        contentValues.put("password", password);
        return contentValues;
    }
    // 将对象转化为json字符串，存进SharePreference中
    private String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // 将json字符串转化为ParameterConfig对象
    // 主要是从SharePreference中取出String后转化为对象
    private static Account fromJsonString(String json) {
        if (json == null)
            return null;
        Gson gson = new Gson();
        return gson.fromJson(json, Account.class);
    }

    // 将ParameterConfig对象转化为json字符串之后保存到SharePreference中
    public void save(String type) {
        String jsonString = this.toJsonString();
        switch (type) {
            case MSP.SP_ADMIN:
                MSP.getInstance(MSP.SP_ADMIN).edit().putString(MSP.SP_ADMIN, jsonString).commit();
                return;
            case MSP.SP_ACCOUNT:
                MSP.getInstance(MSP.SP_ACCOUNT).edit().putString(MSP.SP_ACCOUNT, jsonString).commit();
                return;
        }
    }

    // 从json中解析对象并返回
    public static Account getFromSP(String type) {
        Account account = null;
        switch (type) {
            case MSP.SP_ADMIN:
                account = fromJsonString(MSP.getInstance(MSP.SP_ADMIN).getString(MSP.SP_ADMIN, new Account("admin", "123456").toJsonString()));
            case MSP.SP_ACCOUNT:
                account = fromJsonString(MSP.getInstance(MSP.SP_ACCOUNT).getString(MSP.SP_ACCOUNT, new Account("user", "123456").toJsonString()));
        }
        return account;
    }
}
