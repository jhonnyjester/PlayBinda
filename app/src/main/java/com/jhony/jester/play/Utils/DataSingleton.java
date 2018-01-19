package com.jhony.jester.play.Utils;

import android.net.Uri;

import com.jhony.jester.play.Model.AllPlayers;

import java.util.ArrayList;

/**
 * Created by JAR on 23-08-2017.
 */

public class DataSingleton {
    private static DataSingleton oneInstance;
    private int mUserLevel = 13;
    private int mCurrentProgress = 124;
    private int mLevelUpTarget = 500;
    private int gameSize = 5;
    private int currentNumber;
    private int getRotation = Constants.ROTATION;
    private boolean didWin = false;
    private boolean myTurn = true;
    private boolean isMusic = true;
    private boolean isHost = true;
    private boolean isVisible = false;
    private boolean isSound = true;
    private boolean isVibration = true;
    private String mUserDesc = "World Classified";
    private String myId = "12346";
    private String mUserName = "Jhony Jester";
    private String hostEndPoint = "";
    private String mUserImage = null;
    private ArrayList<Integer> numbers = new ArrayList<>();
    private ArrayList<Boolean> isTicked = new ArrayList<>();
    private ArrayList<AllPlayers> allPlayer = new ArrayList<>();
    private ArrayList<String> endPoints = new ArrayList<>();
    private ArrayList<AllPlayers> hosts = new ArrayList<>();

    private DataSingleton() {

    }

    public static DataSingleton getOneInstance() {
        if (oneInstance == null)
            oneInstance = new DataSingleton();
        return oneInstance;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmUserDesc() {
        return mUserDesc;
    }

    public void setmUserDesc(String mUserDesc) {
        this.mUserDesc = mUserDesc;
    }

    public String getHostEndPoint() {
        return hostEndPoint;
    }

    public void setHostEndPoint(String hostEndPoint) {
        this.hostEndPoint = hostEndPoint;
    }

    public int getmUserLevel() {
        return mUserLevel;
    }

    public void setmUserLevel(int mUserLevel) {
        this.mUserLevel = mUserLevel;
    }

    public int getmCurrentProgress() {
        return mCurrentProgress;
    }

    public void setmCurrentProgress(int mCurrentProgress) {
        this.mCurrentProgress = mCurrentProgress;
    }

    public int getmLevelUpTarget() {
        return mLevelUpTarget;
    }

    public void setmLevelUpTarget(int mLevelUpTarget) {
        this.mLevelUpTarget = mLevelUpTarget;
    }

    public String getmUserImage() {
        return mUserImage;
    }

    public void setmUserImage(String mUserImage) {
        this.mUserImage = mUserImage;
    }

    public int getGameSize() {
        return gameSize;
    }

    public void setGameSize(int gameSize) {
        this.gameSize = gameSize;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public boolean isDidWin() {
        return didWin;
    }

    public void setDidWin(boolean didWin) {
        this.didWin = didWin;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public boolean isMusic() {
        return isMusic;
    }

    public void setMusic(boolean music) {
        isMusic = music;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getGetRotation() {
        return getRotation;
    }

    public void setGetRotation(int getRotation) {
        this.getRotation = getRotation;
    }

    public boolean isSound() {
        return isSound;
    }

    public void setSound(boolean sound) {
        isSound = sound;
    }

    public boolean isVibration() {
        return isVibration;
    }

    public void setVibration(boolean vibration) {
        isVibration = vibration;
    }

    public ArrayList<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(ArrayList<Integer> numbers) {
        this.numbers = numbers;
    }

    public ArrayList<Boolean> getIsTicked() {
        return isTicked;
    }

    public void setIsTicked(ArrayList<Boolean> isTicked) {
        this.isTicked = isTicked;
    }

    public ArrayList<AllPlayers> getAllPlayer() {
        return allPlayer;
    }

    public void setAllPlayer(ArrayList<AllPlayers> allPlayer) {
        this.allPlayer = allPlayer;
    }

    public ArrayList<String> getEndPoints() {
        return endPoints;
    }

    public void setEndPoints(ArrayList<String> endPoints) {
        this.endPoints = endPoints;
    }

    public ArrayList<AllPlayers> getHosts() {
        return hosts;
    }

    public void setHosts(ArrayList<AllPlayers> hosts) {
        this.hosts = hosts;
    }
}
