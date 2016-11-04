package com.alvin.sideview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Title User
 * @Description:
 * @Author: alvin
 * @Date: 2016/11/3.14:30
 * @E-mail: 49467306@qq.com
 */
public class User implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mPinyin);
        dest.writeString(this.mHeader);
    }

    protected User(Parcel in) {
        this.mName = in.readString();
        this.mPinyin = in.readString();
        this.mHeader = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
