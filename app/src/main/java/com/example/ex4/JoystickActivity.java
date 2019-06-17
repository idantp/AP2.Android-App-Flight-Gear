package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class JoystickActivity extends AppCompatActivity implements Joystick.JoystickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the ip and port number from the previous activity.
        String ipAdress = getIntent().getExtras().getString("ip");
        String port = getIntent().getExtras().getString("port");
        //change it to a float.
        int portNum = Integer.parseInt(port);
        //Get the instance of the client connect, init the parameters and attempt to connect.
        ClientConnect clientConnect = ClientConnect.getInstance();
        clientConnect.initializeClient(ipAdress, portNum);
        clientConnect.connectToServer();
        setContentView(R.layout.activity_joystick);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //when destroyed, close the client.
        ClientConnect.getInstance().closeClient();
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent) {
        //multiply it by negative one since it is backwards.
        yPercent*=-1;
        //singleton of client connect.
        ClientConnect client = ClientConnect.getInstance();
        //syntax for sending to the server.
        String aileron = "set /controls/flight/aileron ";
        String elevator = "set /controls/flight/elevator ";
        elevator += String.valueOf(yPercent);
        aileron += String.valueOf(xPercent);
        //this is how the server knows the line is done.
        elevator += "\r\n";
        aileron += "\r\n";
        //send each one to the server.
        client.sendData(elevator);
        client.sendData(aileron);
    }
}
