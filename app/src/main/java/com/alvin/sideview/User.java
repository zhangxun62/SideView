package com.alvin.sideview;

/**
 * @Title User
 * @Description:
 * @Author: alvin
 * @Date: 2016/11/3.14:30
 * @E-mail: 49467306@qq.com
 */
public class User {
    private String mName;
    private String mPinyin;
    private String mHeader;

    public User(String name) {
        mName = name;
    }

    public User() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPinyin() {
        return mPinyin;
    }

    public void setPinyin(String pinyin) {
        mPinyin = pinyin;
    }

    public String getHeader() {
        return mHeader;
    }

    public void setHeader(String header) {
        mHeader = header;
    }
}
