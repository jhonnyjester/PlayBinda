package com.jhony.jester.play.Model;

/**
 * Created by JAR on 17-01-2018.
 */

public class AllPlayers {

    private String uniqueId;
    private PlayerInfo playerInfo;

    public AllPlayers(PlayerInfo playerInfo){
        this.playerInfo = playerInfo;
    }

    public AllPlayers(String uniqueid, PlayerInfo info) {
        this.uniqueId = uniqueid;
        this.playerInfo = info;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }
}

