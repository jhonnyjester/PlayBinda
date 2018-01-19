package com.jhony.jester.play.Model;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by JAR on 18-01-2018.
 */
public class PlayerInfo implements Serializable {

    private String uniqueId, mUserName, mUserDesc;
    private int exp;
    private Uri userImage;
    private boolean status;

    public PlayerInfo(String id, String name, String desc, int ex, Uri image){
        this.uniqueId = id;
        this.mUserName = name;
        this.mUserDesc = desc;
        this.exp = ex;
        this.userImage = image;
    }

    public PlayerInfo(String name, String desc, int ex, Uri image){
        this.mUserName = name;
        this.mUserDesc = desc;
        this.userImage = image;
        this.exp = ex;
    }

    public PlayerInfo(String name, String desc, int ex){
        this.mUserName = name;
        this.mUserDesc = desc;
        this.exp = ex;
    }

    public String getmUserName() {
        return mUserName;
    }

    public String getmUserDesc() {
        return mUserDesc;
    }

    public int getExp() {
        return exp;
    }

    public Uri getUserImage() {
        return userImage;
    }

    public boolean isStatus() {
        return status;
    }
}
