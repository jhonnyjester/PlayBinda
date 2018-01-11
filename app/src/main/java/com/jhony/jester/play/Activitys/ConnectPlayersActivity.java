/*
package com.jhony.jester.play.Activitys;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jhony.jester.play.Connections.Client;
import com.jhony.jester.play.Connections.Server;
import com.jhony.jester.play.R;
import com.rafakob.nsdhelper.NsdHelper;
import com.rafakob.nsdhelper.NsdListener;
import com.rafakob.nsdhelper.NsdService;
import com.rafakob.nsdhelper.NsdType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;

import static com.jhony.jester.play.Utils.Constants.HOST;
import static com.jhony.jester.play.Utils.Constants.JOIN;
import static com.jhony.jester.play.Utils.Constants.WHICH;

public class ConnectPlayersActivity extends AppCompatActivity implements NsdListener {

    private final IntentFilter intentFilter = new IntentFilter();
    public static final String TAG = "Connect Players";

    Server server;
    ServerSocket serverSocket;
    Client clientThread = null;
    NsdHelper nsdHelper;
    WifiManager wifiManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nsdHelper = new NsdHelper(this, this);
        nsdHelper.setAutoResolveEnabled(true);
        nsdHelper.setDiscoveryTimeout(30);
        nsdHelper.setLogEnabled(true);

        Log.d(TAG, getIntent().getStringExtra(WHICH));
        switch (getIntent().getStringExtra(WHICH)) {
            case HOST:
                //     if (isConnected())
                setContentView(R.layout.hosting_layout);
                nsdHelper.registerService("OHLONG", NsdType.HTTP);
                server = new Server(this);
                //     else myConnector();
                break;
            case JOIN:
                //    if (isConnected())
                setContentView(R.layout.joining_layout);

                nsdHelper.startDiscovery(NsdType.HTTP);
                //    else myConnector();
                break;
            default:
                finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume", getIntent().getStringExtra(WHICH));
        switch (getIntent().getStringExtra(WHICH)) {
            case HOST:

                break;
            case JOIN:

                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPause", getIntent().getStringExtra(WHICH));
        switch (getIntent().getStringExtra(WHICH)) {
            case HOST:

                break;
            case JOIN:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", getIntent().getStringExtra(WHICH));
        switch (getIntent().getStringExtra(WHICH)) {
            case HOST:

                break;
            case JOIN:

                break;
        }
    }


    private boolean isConnected() {
        boolean isEnabled = false;

        //is Hotspot enabled
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        Method[] wmMethods = wifi.getClass().getDeclaredMethods();
        for (Method method : wmMethods) {
            if (method.getName().equals("isWifiApEnabled")) {

                try {
                    isEnabled = (boolean) method.invoke(wifi);
                } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        //is wifi enabled
        return true; //isEnabled | wifi.isWifiEnabled();
    }

    private void myConnector() {
        //Propt the user to switch on either wifi or hotspot
       */
/* Dialog dialog = new Dialog(this);
        dialog.setTitle("Switch on WiFi or Hotspot");
        dialog.show();*//*

    }

    @Override
    public void onNsdRegistered(NsdService nsdService) {

    }

    @Override
    public void onNsdDiscoveryFinished() {

    }

    @Override
    public void onNsdServiceFound(NsdService nsdService) {
        nsdHelper.resolveService(nsdService);
    }

    @Override
    public void onNsdServiceResolved(NsdService nsdService) {
        clientThread = new Client(
                "Custer",
                nsdService.getHostIp(),
                nsdService.getPort(),
               */
/* android.text.format.Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress())*//*
"");
        clientThread.start();
    }

    @Override
    public void onNsdServiceLost(NsdService nsdService) {

    }

    @Override
    public void onNsdError(String s, int i, String s1) {

    }
}*/
