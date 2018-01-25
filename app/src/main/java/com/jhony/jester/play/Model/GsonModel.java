package com.jhony.jester.play.Model;

/**
 * Created by JAR on 20-01-2018.
 */

public class GsonModel {

    private String
            playerId,
            playerName,
            playerDesc,
            playerImage,
            password;

    private boolean
            statusBool,
            winnerBool;

    private int
            num,
            payloadId,
            playerExp;

    public GsonModel() {

    }

    public String toString() {
        return " ";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(int payloadId) {
        this.payloadId = payloadId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerDesc() {
        return playerDesc;
    }

    public void setPlayerDesc(String playerDesc) {
        this.playerDesc = playerDesc;
    }

    public int getPlayerExp() {
        return playerExp;
    }

    public void setPlayerExp(int playerExp) {
        this.playerExp = playerExp;
    }

    public String getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(String playerImage) {
        this.playerImage = playerImage;
    }

    public boolean isStatusBool() {
        return statusBool;
    }

    public void setStatusBool(boolean statusBool) {
        this.statusBool = statusBool;
    }

    public boolean isWinnerBool() {
        return winnerBool;
    }

    public void setWinnerBool(boolean winnerBool) {
        this.winnerBool = winnerBool;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
