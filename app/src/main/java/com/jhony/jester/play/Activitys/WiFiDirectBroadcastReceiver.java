//package com.jhony.jester.play.Activitys;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.net.sip.SipAudioCall;
//import android.net.wifi.p2p.WifiP2pDevice;
//import android.net.wifi.p2p.WifiP2pDeviceList;
//import android.net.wifi.p2p.WifiP2pManager;
//import android.util.Log;
//
//import com.jhony.jester.play.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.jhony.jester.play.Activitys.SplashActivity.TAG;
//import static com.jhony.jester.play.Utils.DataSingleton.peers;
//import static com.jhony.jester.play.Utils.DataSingleton.refreshedPeers;
//
///**
// * Created by JAR on 29-08-2017.
// */
//
//public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {
//
//    private WifiP2pManager manager;
//    private WifiP2pManager.Channel channel;
//    private ConnectPlayersActivity activity;
//
//    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, ConnectPlayersActivity activity) {
//        this.manager = manager;
//        this.channel = channel;
//        this.activity = activity;
//    }
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        String action = intent.getAction();
//        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
//            Log.d("WiFiBroadcastReceiver", "WiFi STATE CHANGED");
//
//            //To check if Wifi P2P is on or not
//            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
//            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
//                //wifi is on
//                activity.setIsWifiEnabled(true);
//            } else {
//                //wifi is off
//                activity.setIsWifiEnabled(false);
//            }
//        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
//            Log.d("WiFiBroadcastReceiver", "PEERS CHANGED");
//            if (manager != null) {
//                manager.requestPeers(channel, peerListListener);
//            }
//
//
//        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
//            Log.d("WiFiBroadcastReceiver", "CONNECTION CHANGED");
//
//        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
//            Log.d("WiFiBroadcastReceiver", "THIS DEVIICE CONNECTION CHANGED");
//        }
//    }
//
//
//
//    private WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
//        @Override
//        public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
//            refreshedPeers = (List<WifiP2pDevice>) wifiP2pDeviceList.getDeviceList();
//            if (!refreshedPeers.equals(peers)) {
//                peers.clear();
//                peers.addAll(refreshedPeers);
//            }
//
//            if (peers.size() == 0){
//                Log.d("WiFiBroadcastReceiver", "NO DEVICES FOUND");
//
//            }
//
//
//        }
//    };
//
//
//}
