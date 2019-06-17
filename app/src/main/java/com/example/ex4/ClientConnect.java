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
    //Singleton to use only one.
    private static ClientConnect clientConnect = null;
    public static ClientConnect getInstance() {
        if (clientConnect == null) {
            clientConnect = new ClientConnect();
        }
        return clientConnect;
    }

    /**
     * Sets the parameters of this class since it's a singleton.
     * @param ipAddress - ip address to connect to.
     * @param portNum - which port to use.
     */
    public void initializeClient(String ipAddress, int portNum) {
        this.porNum = portNum;
        this.isConnected = false;
        try {
            //parse it to an ip address the computer knows to open a socket for.
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch(Exception e){
        }
    }

    /**
     * Sends the data to the server.
     * @param data - what to send.
     */
    public void sendData(final String data){
        //Use a new thread in order to send the data.
        Thread thread = new Thread(){
            @Override
            public void run(){
                try {
                    printWriter.println(data);
                    printWriter.flush();
                } catch(Exception e){
                }
            }
        };
        thread.start();

    }

    /**
     * Connects to the server, using the ip and port number given.
     */
    public void connectToServer(){
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    //initialize the socket and the print writer to send data later.
                    socket = new Socket(ipAddress, porNum);
                    printWriter = new PrintWriter(socket.getOutputStream());
                    //is connected is true.
                    isConnected = true;

                } catch(Exception e){
                }
            }
        };
        thread.start();
    }

    /**
     * Closes the client and everything associated with it.
     */
    public void closeClient(){
        if (isConnected){
            this.isConnected = false;
            this.printWriter.close();
            try{
                socket.close();
            } catch(Exception e){
            }
        }
    }
}
