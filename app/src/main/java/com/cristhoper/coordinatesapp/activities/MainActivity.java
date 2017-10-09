package com.cristhoper.coordinatesapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cristhoper.coordinatesapp.R;
import com.cristhoper.coordinatesapp.services.MyService;

public class MainActivity extends AppCompatActivity {

    TextView coordinates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinates = (TextView) findViewById(R.id.txtCoordinates);
        MyService myService = new MyService(getApplicationContext());

        myService.getLocation();
        myService.setView(coordinates);
    }
}
