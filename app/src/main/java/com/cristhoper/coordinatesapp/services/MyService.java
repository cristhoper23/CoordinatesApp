package com.cristhoper.coordinatesapp.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Cris on 8/10/2017.
 */

//Cada servicio tiene que extender de una clase llamada Service
//Recomendación: Cuando se crea un servicio, es bueno manejar un contexto

//LocationListener es una interface que permite implementar métodos que notifiquen a través
//del LocationManager si la ubicación ha cambiado conectandose con los servicios de ubicacion y red
public class MyService extends Service implements LocationListener {

    private Context context;

    double latitude;
    double longitude;

    //Location is a data class representing a geographic location.
    Location location;

    //Bandera booleana para el estado del GPS
    boolean gpsEnable;

    //Texto donde se escribirá la coordenada
    TextView text;

    LocationManager locationManager;

    public MyService() {
        super();
    }

    public MyService(Context context) {
        //Como es un servicio es necesario utilizar el constructor de la clase Service
        super();
        this.context = context;
    }

    //Método para colocar el texto dentro de la vista TextView
    public void setView(View v) {
        text = (TextView) v;
        text.setText("Coordinates: " + latitude + ", " + longitude);
    }

    //Método para obtener la ubicación
    public void getLocation() {
        try {
            //LocationManager is a class that provides access to the system location services
            locationManager = (LocationManager) this.context.getSystemService(LOCATION_SERVICE);
            //isProviderEnabled Returns the current enabled/disabled status of the given provider.
            //If the user has enabled this provider in the Settings menu, true is returned otherwise false is returned
            gpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception e) {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }

        Log.d("gpsEnable", ""+gpsEnable);
        if (gpsEnable) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            //requestLocationUpdates --> register for location updates using the named provider, and a pending intent.
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60, 10, this);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            Log.d("LAT", "" + latitude);
            Log.d("LON", "" + longitude);

        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    //Método que está pendiente del cambio de ubicación
    public void onLocationChanged(Location location) {

    }

    @Override
    //Método que verifica el estado del usuario: on, off
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    //Método para el proveedor de Internet habilitado
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
