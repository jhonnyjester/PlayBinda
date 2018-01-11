package com.jhony.jester.play.Model;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by JAR on 05-09-2017.
 */

public class PlayerInfo implements Serializable{

    public String mUserName, mUserDesc;
    public int exp;
    public Uri userImage;

    public PlayerInfo(String name, String desc, int ex, Uri image){
        this.mUserName = name;
        this.mUserDesc = desc;
        this.userImage = image;
        this.exp = ex;
    }
}
