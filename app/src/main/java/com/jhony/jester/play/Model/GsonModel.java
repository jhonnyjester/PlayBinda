package com.jhony.jester.play.Model;

/**
 * Created by JAR on 20-01-2018.
 */

public class GsonModel {

    private String
            payloadId,
            playerId,
            playerName,
            playerDesc,
            playerExp,
            playerImage,
            password;

    private boolean
            turnBool,
            statusBool,
            winnerBool;

    private int
            num;

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

    public String getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(String payloadId) {
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

    public String getPlayerExp() {
        return playerExp;
    }

    public void setPlayerExp(String playerExp) {
        this.playerExp = playerExp;
    }

    public String getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(String playerImage) {
        this.playerImage = playerImage;
    }

    public boolean isTurnBool() {
        return turnBool;
    }

    public void setTurnBool(boolean turnBool) {
        this.turnBool = turnBool;
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
