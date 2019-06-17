package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Joystick joystick = new Joystick(this);
        setContentView(R.layout.activity_main);
//        setContentView(joystick);
    }

   public void onClick (View view){
        Intent intent = new Intent(this, JoystickActivity.class);
        startActivity(intent);
   }
}
