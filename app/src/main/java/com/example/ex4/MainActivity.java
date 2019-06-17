package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, JoystickActivity.class);
        intent.putExtra("port", ((EditText)findViewById(R.id.portText)).getText().toString());
        intent.putExtra("ip", ((EditText)findViewById(R.id.IpText)).getText().toString());
        startActivity(intent);
    }
}
