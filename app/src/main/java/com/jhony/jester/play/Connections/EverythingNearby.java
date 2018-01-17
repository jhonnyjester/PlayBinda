package com.jhony.jester.play.Connections;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
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
import com.jhony.jester.play.Utils.Constants;
import com.jhony.jester.play.Utils.DataSingleton;

/**
 * Created by JAR on 15-01-2018.
 */

public class EverythingNearby {
    private Context context;
    private String payloadString = "Hey binda you got it right this time";
    private String SERVICE_ID = "com.binda";
    private static final String TAG = "EverythingNearby";


    public EverythingNearby(Context context, String which) {
        this.context = context;
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
        Nearby.getConnectionsClient(context).startAdvertising(
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

    private final ConnectionLifecycleCallback connectionLifecycleCallback = new ConnectionLifecycleCallback() {
        @Override
        public void onConnectionInitiated(String s, ConnectionInfo connectionInfo) {
            Log.d("onConnectionInitiated", "INITIATED");
            Nearby.getConnectionsClient(context).acceptConnection(s, payloadCallback);
        }

        @Override
        public void onConnectionResult(String s, ConnectionResolution connectionResolution) {
            switch (connectionResolution.getStatus().getStatusCode()) {
                case ConnectionsStatusCodes.STATUS_OK:
                    Log.d("onConnectionResult", "CONNECTED");
                    sendPayload(s, Payload.fromBytes(payloadString.getBytes()));
                    Nearby.getConnectionsClient(context).stopAdvertising();
                    Nearby.getConnectionsClient(context).stopDiscovery();
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

    private void sendPayload(String endPointId, Payload payload) {
        Nearby.getConnectionsClient(context).sendPayload(endPointId, payload);
        Log.d(TAG, "sendPayload: sent");

    }

    private final PayloadCallback payloadCallback = new PayloadCallback() {
        @Override
        public void onPayloadReceived(String s, Payload payload) {
            Log.d("onPayLoadReceived", "RECEIVED");
            String stringsodasarbath = new String(payload.asBytes());
            Log.d("Payload Received", stringsodasarbath);
        }

        @Override
        public void onPayloadTransferUpdate(String s, PayloadTransferUpdate payloadTransferUpdate) {
            Log.d("onTransferUpdate", "TRANSFER UPDATE");
        }
    };

    private void startDiscovery() {
        Nearby.getConnectionsClient(context).startDiscovery(
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

    private final EndpointDiscoveryCallback endpointDiscoveryCallback = new EndpointDiscoveryCallback() {
        @Override
        public void onEndpointFound(String s, DiscoveredEndpointInfo discoveredEndpointInfo) {
            Nearby.getConnectionsClient(context).requestConnection(
                    DataSingleton.mUserName,
                    s,
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


}
