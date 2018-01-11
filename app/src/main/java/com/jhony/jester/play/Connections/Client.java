package com.jhony.jester.play.Connections;

import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by JAR on 06-09-2017.
 */



public class Client extends Thread {
    String name;
    String destAdd;
    String IP;
    int destPort;
    String infoFromServer;
    BufferedReader bufferedReader;
    DataOutputStream dataOutputStream;

    public Client(String n, String a, int p, String ip){
        name = n;
        destAdd = a;
        destPort = p;
        IP = ip;
    }

    @Override
    public void run() {
        Socket socket = null;
Log.d("control", "CLIENT");
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("ip_address", IP);
            jsonObject.put("client_name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            socket = new Socket(destAdd, destPort);
         dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Im your client");

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Log.d("Message from Server", bufferedReader.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null){
                try {
                    socket.close();
                    bufferedReader.close();
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        super.run();
    }
}

/*
public class Client extends AsyncTask<Void, Void, Void>{

    String destAddress;
    int destPort;
    String response;

    public Client(String IP, int port){
        this.destAddress = IP;
        this.destPort = port;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Socket socket = null;

        try {
            socket = new Socket(destAddress, destPort);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            byte[] buff = new byte[1024];
            int bytesRead;
            InputStream inputStream = socket.getInputStream();
            while ((bytesRead = inputStream.read(buff)) != -1){
                byteArrayOutputStream.write(buff, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d("Response", response);
        super.onPostExecute(aVoid);
    }
}
*/


