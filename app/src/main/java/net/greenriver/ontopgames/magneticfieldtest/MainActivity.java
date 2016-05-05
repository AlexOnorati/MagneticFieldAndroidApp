package net.greenriver.ontopgames.magneticfieldtest;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public Random random;

    public SensorManager sensorManager;
    public SensorEventListener sensorListener;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mGoogleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();
        }
        Grid.instance = new Grid(30, 30, 30);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorListener = new SensorEventListener() {

            @Override
            public void onAccuracyChanged(Sensor arg0, int arg1) {
            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                Sensor currentSensor = event.sensor;
                float azimuth_angle = event.values[0];
                float pitch_angle = event.values[1];
                float roll_angle = event.values[2];

                // Cases for each sensor that is being listened to.
                if (currentSensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

                    float magnitude = (float) Math.sqrt(Math.pow(azimuth_angle, 2.0) + Math.pow(pitch_angle, 2.0) + Math.pow(roll_angle, 2.0));



                    int i = 0;
                    int j = 0;
                    int k = 0;
                    //Sets Magnetic field values in grid
                    if (i < 30 && i > 0 && j < 30 && j > 0 && k < 30 && k > 0) {
                        Grid.instance.setCoridinate(i, j, k, magnitude);
                    }

                    TextView xCord = (TextView) findViewById(R.id.magX);
                    xCord.setText("X: " + azimuth_angle + " = " + i);

                    TextView yCord = (TextView) findViewById(R.id.magY);
                    yCord.setText("Y: " + pitch_angle + " = " + j);

                    TextView zCord = (TextView) findViewById(R.id.magZ);
                    zCord.setText("Z: " + roll_angle + " = " + k);

                    TextView magValue = (TextView) findViewById(R.id.magmag);
                    magValue.setText("value: " + magnitude);
                } else if (currentSensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                    TextView xCord = (TextView) findViewById(R.id.rotX);
                    xCord.setText("X: " + azimuth_angle);

                    TextView yCord = (TextView) findViewById(R.id.rotY);
                    yCord.setText("Y: " + pitch_angle);

                    TextView zCord = (TextView) findViewById(R.id.rotZ);
                    zCord.setText("Z: " + roll_angle);
                }

            }
        };

        //Register listeners for all wanted sensors
        sensorManager.registerListener(sensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(sensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);

        random = new Random();
    }


    @Override
    public void onConnectionSuspended(int index){

    }

    @Override
    public void onConnected(Bundle connectionHint) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void goToGraph(View view){
        Intent intent = new Intent(this, display.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.connect();

    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        mGoogleApiClient.disconnect();
    }


}
