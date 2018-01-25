package com.jhony.jester.play.Model;

import java.io.Serializable;

/**
 * Created by JAR on 18-01-2018.
 */
public class PlayerInfo implements Serializable {

    private String endpointId, mUserName, mUserDesc, mPassword;
    private int exp;
    private String userImage;
    private boolean status;

    public PlayerInfo(String endId, String name, String desc, int ex, String image, String pass){
        this.endpointId = endId;
        this.mUserName = name;
        this.mUserDesc = desc;
        this.exp = ex;
        this.userImage = image;
        this.mPassword = pass;
    }

    public PlayerInfo(String endId, String name, String desc, int ex, String image){
        this.endpointId = endId;
        this.mUserName = name;
        this.mUserDesc = desc;
        this.exp = ex;
        this.userImage = image;
    }

    public PlayerInfo(String name, String desc, int ex, String image){
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

    public String getEndpointId() {
        return endpointId;
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

    public String getUserImage() {
        return userImage;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
