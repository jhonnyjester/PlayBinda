package com.jhony.jester.play.Utils;

import android.net.Uri;
import android.net.wifi.p2p.WifiP2pDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JAR on 23-08-2017.
 */

public class DataSingleton {

    public static String mUserName = "Jhony Jester";

    public static int mCurrentProgress = 124;

    public static int mLevelUpTarget = 500;

    public static int mUserLevel = 13;

    public static Uri mUserImage = null;

    public static int gameSize = 5;

    public static boolean didWin = false;

    public static boolean myTurn = true;

    public static boolean isMusic = true;

    public static boolean isHost = true;

    public static boolean isVisible = false;

    public static String getRotation = Constants.ROTATION;

    public static boolean isSound = true;

    public static boolean isVibration = true;

    public static List<WifiP2pDevice> peers = new ArrayList<>();

    public static List<WifiP2pDevice> refreshedPeers;

    public static ArrayList<Integer> numbers = new ArrayList<>();

    public static ArrayList<Boolean> isTicked = new ArrayList<>();

}
