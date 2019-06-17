package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class JoystickActivity extends AppCompatActivity implements Joystick.JoystickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String ipAdress = getIntent().getExtras().getString("ip");
        String port = getIntent().getExtras().getString("port");
        int portNum = Integer.parseInt(port);
        ClientConnect clientConnect = ClientConnect.getInstance();
        clientConnect.initializeClient(ipAdress, portNum);
        clientConnect.connectToServer();
        setContentView(R.layout.activity_joystick);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ClientConnect.getInstance().closeClient();
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent) {
        yPercent*=-1;
        ClientConnect client = ClientConnect.getInstance();
        String aileron = "set /controls/flight/aileron ";
        String elevator = "set /controls/flight/elevator ";
        elevator += String.valueOf(yPercent);
        aileron += String.valueOf(xPercent);
        elevator += "\r\n";
        aileron += "\r\n";
        client.sendData(elevator);
        client.sendData(aileron);


    }
}
