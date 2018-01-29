package com.jhony.jester.play.Connections;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.Payload;
import com.google.gson.Gson;
import com.jhony.jester.play.Activitys.GameActivity;
import com.jhony.jester.play.Interfaces.OnAnalysePayloadListener;
import com.jhony.jester.play.Model.AllPlayers;
import com.jhony.jester.play.Model.GsonModel;
import com.jhony.jester.play.Model.PlayerInfo;
import com.jhony.jester.play.Utils.DataSingleton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JAR on 18-01-2018.
 */

public class AnalysePayload {
    //    private static AnalysePayload anInstance;
    private static final String TAG = "AnalysePayload";
    Context co;
    GameActivity activity;
    private String payload;
    private String endPoint;
    private String uniqueID;
    private OnAnalysePayloadListener joinListener, forGameActivity;
    private ConnectionsClient connectionsClient;
    private DataSingleton dataSingleton;
    private Gson gson;
    private GsonModel gsonModel;
/*    public static AnalysePayload getanInstance() {
        if (anInstance == null)
            anInstance = new AnalysePayload();
        return anInstance;
    }*/

    public AnalysePayload(String endPoint, String payload, ConnectionsClient connectionsClient, OnAnalysePayloadListener onJoinCallback) {
        this.payload = payload;
        this.endPoint = endPoint;
        co = activity.getParent();
        forGameActivity = (OnAnalysePayloadListener) co;
        this.joinListener = onJoinCallback;
        this.connectionsClient = connectionsClient;
        dataSingleton = DataSingleton.getOneInstance();
        analyse();
    }

    public AnalysePayload() {
    }

  /*  public void setJoinListener(JoinListener joinListener) {
        this.joinListener = joinListener;
    }

    public void setSplitListener(SplitListener splitListener) {
        this.splitListener = splitListener;
    }*/

    private void analyse() {
        /*
        *  1 --> INFO       --> playerInfo (player name, image, experience, desc
        *  2 --> NUM        --> number received from other players
        *  3 --> TURN       --> gives the player id to determine the turn
        *  4 --> JOIN       --> gives the newly joined player info
        *  5 --> STATUS     --> boolean saying if the player is ready or not
        *  6 --> WINNER     --> says who won
        * */
        splitPayload();
        Log.d(TAG, "analyse: PayLoad received " + gsonModel.getPayloadId());

        switch (gsonModel.getPayloadId()) {
            case 1:
                dataSingleton.getHosts().add(new AllPlayers(gsonModel.getPlayerId(),
                        new PlayerInfo(endPoint,
                                gsonModel.getPlayerName(),
                                gsonModel.getPlayerDesc(),
                                gsonModel.getPlayerExp(),
                                gsonModel.getPlayerImage(),
                                gsonModel.getPassword())));
                joinListener.OnAnalysePayload(gsonModel);
                break;
            case 2:
                if (dataSingleton.isHost()) {
                    connectionsClient.sendPayload(dataSingleton.getEndPoints(),
                            Payload.fromBytes(payload.getBytes()));
                }
                dataSingleton.setCurrentNumber(gsonModel.getNum());
                forGameActivity.OnAnalysePayload(gsonModel);
                break;
            case 3:
                forGameActivity.OnAnalysePayload(gsonModel);
                break;
            case 4:
                uniqueID = generateUID();
                if (dataSingleton.isHost()) {
                    try {
                        JSONObject jsonObject = new JSONObject(gson.toJson(gsonModel));
                        jsonObject.put("playerId", uniqueID);
                        connectionsClient.sendPayload(dataSingleton.getEndPoints(),
                                Payload.fromBytes(jsonObject.toString().getBytes()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                dataSingleton.getAllPlayer().add(0, new AllPlayers(uniqueID,
                        new PlayerInfo(endPoint,
                                gsonModel.getPlayerName(),
                                gsonModel.getPlayerDesc(),
                                gsonModel.getPlayerExp(),
                                gsonModel.getPlayerImage())));
                break;
            case 5:
                if (dataSingleton.isHost()) {
                    connectionsClient.sendPayload(dataSingleton.getEndPoints(),
                            Payload.fromBytes(payload.getBytes()));
                }
                for (int i = 0; i < dataSingleton.getAllPlayer().size(); i++) {
                    if (dataSingleton.getAllPlayer().get(i).getUniqueId().equals(gsonModel.getPlayerId())) {
                        dataSingleton.getAllPlayer().get(i).getPlayerInfo().setStatus(gsonModel.isStatusBool());
                        forGameActivity.OnAnalysePayload(gsonModel);
                    }
                }
                break;
            case 6:
                if (dataSingleton.isHost()) {
                    connectionsClient.sendPayload(dataSingleton.getEndPoints(),
                            Payload.fromBytes(payload.getBytes()));
                }
                forGameActivity.OnAnalysePayload(gsonModel);
                break;
        }
    }

    private void splitPayload() {
        //splitPayLoad = payload.split("^");
        gson = new Gson();
        gsonModel = gson.fromJson(payload, GsonModel.class);
    }

    private String generateUID() {
        return "keep this for now";
    }

}



/*if (payload.startsWith("1")) {
            dataSingleton.getHosts().add(
                    new AllPlayers(endPoint,
                            new PlayerInfo(splitPayLoad[1],
                                    splitPayLoad[2],
                                    Integer.valueOf(splitPayLoad[3]))));
            //makeInterface onSplitCompleted
            splitListener.onSplitCompleted(1);
            return;
        }
        if (payload.startsWith("2")) {
            if (dataSingleton.isHost()) {
                connectionsClient.sendPayload(dataSingleton.getEndPoints(), Payload.fromBytes(payload.getBytes()));
            }
            dataSingleton.setCurrentNumber(Integer.valueOf(splitPayLoad[1]));
            splitListener.onSplitCompleted(2);
            return;
        }
        if (payload.startsWith("3")) {
            dataSingleton.setMyTurn(true);
            return;
        }
        if (payload.startsWith("4")) {
            splitPayload();
            dataSingleton.getAllPlayer().add(0,
                    new AllPlayers(endPoint,
                            new PlayerInfo(splitPayLoad[1],
                                    splitPayLoad[2],
                                    Integer.valueOf(splitPayLoad[3]))));
            if (dataSingleton.isHost()) {
                dataSingleton.getEndPoints().add(endPoint);
                            //send payload
            payloadString = "4" + "^" +
                    dataSingleton.getAllPlayer().get(0).getPlayerInfo().getmUserName() + "^" +
                    dataSingleton.getAllPlayer().get(0).getPlayerInfo().getmUserDesc() + "^" +
                    dataSingleton.getAllPlayer().get(0).getPlayerInfo().getExp();

            connectionsClient.sendPayload(
                    dataSingleton.getEndPoints(),
                    Payload.fromBytes(payloadString.getBytes()));
            }
            return;
        }
        if (payload.startsWith("5")) {
            return;
        }
        if (payload.startsWith("6")) {

            return;
        }
        if (payload.startsWith("7")) {

        }*/
