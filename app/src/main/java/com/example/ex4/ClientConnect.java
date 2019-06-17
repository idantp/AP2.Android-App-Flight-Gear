package com.example.ex4;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConnect {
    private boolean isConnected;
    private int porNum;
    private InetAddress ipAddress;
    private PrintWriter printWriter;
    private Socket socket;


    private ClientConnect() { }

    private static ClientConnect clientConnect = null;
    public static ClientConnect getInstance() {
        if (clientConnect == null) {
            clientConnect = new ClientConnect();
        }
        return clientConnect;
    }

    public void initializeClient(String ipAddress, int portNum) {
        this.porNum = portNum;
        this.isConnected = false;
        try {
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch(Exception e){
            //todo - error message
        }
    }

    public void sendData(final String data){
        Thread thread = new Thread(){
            @Override
            public void run(){
                try {
                    printWriter.println(data);
                    printWriter.flush();
                } catch(Exception e){
                    //todo - error
                }
            }
        };
        thread.start();

    }

    public void connectToServer(){
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    socket = new Socket(ipAddress, porNum);
                    printWriter = new PrintWriter(socket.getOutputStream());
                    isConnected = true;

                } catch(Exception e){
                    System.out.println(e.toString());
                    //todo - error message

                }
            }
        };
        thread.start();
    }

    public void closeClient(){
        if (isConnected){
            this.isConnected = false;
            this.printWriter.close();
            try{
                socket.close();
            } catch(Exception e){
                //todo - error.

            }
        }
    }

}
