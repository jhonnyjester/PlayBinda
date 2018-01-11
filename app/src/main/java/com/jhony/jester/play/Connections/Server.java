/*
package com.jhony.jester.play.Connections;

import android.util.Log;

import com.jhony.jester.play.Utils.Constants;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

*/
/**
 * Created by JAR on 06-09-2017.
 *//*


public class Server {
//    ConnectPlayersActivity activity;
    ServerSocket serverSocket = null;
    DataOutputStream dataOutputStream;
    BufferedReader bufferedReader;

//    public Server(ConnectPlayersActivity activity) {
//        this.activity = activity;
//        Thread serverSocketThread = new ServerSocketThread();
//        serverSocketThread.start();
//    }

    public void destroySocket() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ServerSocketThread extends Thread {
        @Override
        public void run() {
            Log.d("control", "SERVER");
            try {
                serverSocket = new ServerSocket(0);
                while (true) {
                    Socket clientSocket = serverSocket.accept();

                    ServerSocketReplyThread serverSocketReplyThread = new ServerSocketReplyThread(clientSocket);
                    serverSocketReplyThread.start();
                }
            } catch (IOException e) {
                Log.d("SST", "its me");
                e.printStackTrace();
            }
        }
    }

    private class ServerSocketReplyThread extends Thread {
        private Socket hostThreadSocket;

        ServerSocketReplyThread(Socket socket) {
            hostThreadSocket = socket;
        }

        @Override
        public void run() {
            Log.d("control", "SERVEREPLY");

            try {
                bufferedReader = new BufferedReader(new InputStreamReader(hostThreadSocket.getInputStream()));
                Log.d("message from client", bufferedReader.readLine());
                dataOutputStream = new DataOutputStream(hostThreadSocket.getOutputStream());
                dataOutputStream.writeUTF("Hello, Im SERVER");


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {

                    if (serverSocket != null) {
                        serverSocket.close();
                    }
                    bufferedReader.close();
                    dataOutputStream.close();
                    }catch(IOException e){
                        e.printStackTrace();

                }
            }
        }
    }
}


*/
