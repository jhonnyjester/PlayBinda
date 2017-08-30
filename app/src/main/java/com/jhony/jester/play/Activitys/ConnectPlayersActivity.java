package com.jhony.jester.play.Activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jhony.jester.play.R;

import static com.jhony.jester.play.Utils.Constants.HOST;
import static com.jhony.jester.play.Utils.Constants.JOIN;
import static com.jhony.jester.play.Utils.Constants.WHICH;

public class ConnectPlayersActivity extends AppCompatActivity {

    private final IntentFilter intentFilter = new IntentFilter();
    public static final String TAG = "Connect Players";
    private boolean isWifiP2pEnabled = false;

    WifiP2pManager.Channel channel;
    WifiP2pManager manager;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, getIntent().getStringExtra(WHICH));

        switch (getIntent().getStringExtra(WHICH)) {
            case HOST:
                setContentView(R.layout.hosting_layout);
                break;
            case JOIN:
                setContentView(R.layout.joining_layout);
                break;
            default:
                finish();
        }

        initiateP2P();
        manager = (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
        intiatePeers();
        peerConnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void initiateP2P(){
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    private void intiatePeers(){
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i) {

            }
        });
    }

    private void peerConnect(){
        WifiP2pDevice device = WiFiDirectBroadcastReceiver.peers.get(0);
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        manager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i) {

            }
        });
    }

    public void setIsWifiEnabled(boolean isEnabled){
        isWifiP2pEnabled = isEnabled;
    }


}
