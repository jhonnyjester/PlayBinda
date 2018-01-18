package com.jhony.jester.play.Connections;

import android.app.admin.DeviceAdminInfo;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.jhony.jester.play.Interfaces.SplitListener;
import com.jhony.jester.play.Model.AllPlayers;
import com.jhony.jester.play.Utils.Constants;
import com.jhony.jester.play.Utils.DataSingleton;

/**
 * Created by JAR on 15-01-2018.
 */

public class EverythingNearby implements SplitListener{
    private static final String TAG = "EverythingNearby";
    private Context context;
    private ConnectionsClient connectionsClient;
    private AnalysePayload analysePayload;
    private final PayloadCallback payloadCallback = new PayloadCallback() {
        @Override
        public void onPayloadReceived(String endPoint, Payload payload) {
            Log.d("onPayLoadReceived", "RECEIVED");
            String receivedPayloadString = new String(payload.asBytes());
            analysePayload = new AnalysePayload(endPoint, receivedPayloadString, connectionsClient);
//            Log.d("Payload Received", receivedPayloadString);
//            Toast.makeText(context, "Payload recieved: " + receivedPayloadString, Toast.LENGTH_SHORT).show();
            //  int otherPlayerClicked = Arrays.binarySearch(DataSingleton.numbers.toArray(), receivedPayloadString);
        }

        @Override
        public void onPayloadTransferUpdate(String s, PayloadTransferUpdate payloadTransferUpdate) {
            Log.d("onTransferUpdate", "TRANSFER UPDATE");
        }
    };
    private final ConnectionLifecycleCallback connectionLifecycleCallback = new ConnectionLifecycleCallback() {
        @Override
        public void onConnectionInitiated(String endPoint, ConnectionInfo connectionInfo) {
            Log.d("onConnectionInitiated", "INITIATED");
            connectionsClient.acceptConnection(endPoint, payloadCallback);
//            DataSingleton.allPlayer.add(new AllPlayers(endPoint, connectionInfo.getEndpointName()));
            //do this when join is clicked
        }

        @Override
        public void onConnectionResult(String endPoint, ConnectionResolution connectionResolution) {
            switch (connectionResolution.getStatus().getStatusCode()) {
                case ConnectionsStatusCodes.STATUS_OK:
                    Log.d("onConnectionResult", "CONNECTED");
                    String initialPayload = " ";
                    if (which.equals(Constants.HOST)) {
                        initialPayload = "1" + "^" +
                                DataSingleton.mUserName + "^" +
                                DataSingleton.mUserDesc + "^" +
                                DataSingleton.mUserLevel + "^" +
                                //send image string
                                "password";
                    }
                    connectionsClient.sendPayload(endPoint, Payload.fromBytes(initialPayload.getBytes()));


                    break;
                case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                    Log.d("onConnectionResult", "REJECTED");
                    break;
                case ConnectionsStatusCodes.STATUS_ERROR:
                    Log.d("onConnectionResult", "ERROR");
                    break;
            }
        }

        @Override
        public void onDisconnected(String s) {
            Log.d("onDisconnected", "DISCONNECTED");
        }
    };
    private final EndpointDiscoveryCallback endpointDiscoveryCallback = new EndpointDiscoveryCallback() {
        @Override
        public void onEndpointFound(String endPoint, DiscoveredEndpointInfo discoveredEndpointInfo) {

            connectionsClient.requestConnection(
                    DataSingleton.mUserName,
                    endPoint,
                    connectionLifecycleCallback)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("EndPoint", "SUCCESS");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("EndPoint", "FAIL");
                        }
                    });
        }

        @Override
        public void onEndpointLost(String s) {
            Log.d("EndPoint", "LOST");
        }
    };
    private String payloadString = "Hey binda you got it right this time", which;
    private String SERVICE_ID = "com.binda";

    public EverythingNearby(Context context, String which) {
        this.context = context;
        this.connectionsClient = Nearby.getConnectionsClient(context);
        this.which = which;
        switch (which) {
            case Constants.HOST:
                startAdvertising();
                break;
            case Constants.JOIN:
                startDiscovery();
                break;
        }
    }

    private void startAdvertising() {
        connectionsClient.startAdvertising(
                DataSingleton.mUserName,
                SERVICE_ID,
                connectionLifecycleCallback,
                new AdvertisingOptions(Strategy.P2P_STAR))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("startAdvertising", "SUCCESS");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("startAdvertising", "FAIL");
                    }
                });
    }

    private void startDiscovery() {
        connectionsClient.startDiscovery(
                SERVICE_ID,
                endpointDiscoveryCallback,
                new DiscoveryOptions(Strategy.P2P_STAR)
        )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("startDiscovery", "SUCCESS");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("startDiscovery", "FAIL");
                    }
                });
    }

    @Override
    public void onSplitCompleted(int id) {
        if (id == 4){
            //send payload
            payloadString = "4" + "^" +
                    DataSingleton.allPlayer.get(0).getPlayerInfo().getmUserName() + "^" +
                    DataSingleton.allPlayer.get(0).getPlayerInfo().getmUserDesc()+ "^" +
                    DataSingleton.allPlayer.get(0).getPlayerInfo().getExp();

            connectionsClient.sendPayload(
                    DataSingleton.endPoints,
                    Payload.fromBytes(payloadString.getBytes()));
        }
    }
}
