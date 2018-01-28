package com.jhony.jester.play.Connections;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

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
import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.Constants;
import com.jhony.jester.play.Utils.DataSingleton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JAR on 15-01-2018.
 */

public class EverythingNearby {
    private static final String TAG = "EverythingNearby";
    private int which;
    private Context context;
    private ConnectionsClient connectionsClient;
    private DataSingleton dataSingleton;

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
                    JSONObject initialPayload = null;
                    if (which == (Constants.HOST)) {
                        initialPayload = new JSONObject();
                        try {
                            initialPayload.put(getString(R.string.payload_id), 1);
                            initialPayload.put(getString(R.string.user_id), dataSingleton.getMyId());
                            initialPayload.put(getString(R.string.user_name), dataSingleton.getmUserName());
                            initialPayload.put(getString(R.string.user_desc), dataSingleton.getmUserDesc());
                            initialPayload.put(getString(R.string.user_exp), dataSingleton.getmUserLevel());
                            initialPayload.put(getString(R.string.user_img), dataSingleton.getmUserImage());
                            initialPayload.put(getString(R.string.password), "Password");
                            connectionsClient.sendPayload(endPoint, Payload.fromBytes(initialPayload.toString().getBytes()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
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
                    dataSingleton.getmUserName(),
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

    private final String SERVICE_ID = "com.play.binda.jar";

    public EverythingNearby(Context context, int which) {
        this.context = context;
        this.connectionsClient = Nearby.getConnectionsClient(context);
        this.which = which;
        dataSingleton = DataSingleton.getOneInstance();
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
                dataSingleton.getmUserName(),
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


    private String getString(int id) {
        return context.getResources().getString(id);
    }
}
