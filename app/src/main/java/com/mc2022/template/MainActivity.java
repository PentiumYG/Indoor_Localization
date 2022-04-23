package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView accX, accY, accZ, magX, magY, magZ, stepC, deg, dist;
    Switch accSwi, magSwi;

    double prevDisplacement = 0;
    Integer stepCount = 0;
    Integer threshold = 6;

    float[] gravity;
    float[] geoMagneticField;
    float azimut;
    float currentDeg = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //TextView ids fetched
        accX = (TextView) findViewById(R.id.accX);
        accY = (TextView) findViewById(R.id.accY);
        accZ = (TextView) findViewById(R.id.accZ);
        magX = (TextView) findViewById(R.id.magX);
        magY = (TextView) findViewById(R.id.magY);
        magZ = (TextView) findViewById(R.id.magZ);
        stepC = (TextView) findViewById(R.id.steps);
        deg = (TextView) findViewById(R.id.degrees);
        dist = (TextView) findViewById(R.id.distance);

        //Switch Ids Fetched
        accSwi = (Switch) findViewById(R.id.accSwitch);
        magSwi = (Switch) findViewById(R.id.magSwitch);


        //Sensor Service
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        //Accelerometer sensor
//        accSwi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(accSwi.isChecked()){
//                    Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//                    if(accelerometer !=null){
//                        sensorManager.registerListener((SensorEventListener) MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
//                        Toast.makeText(MainActivity.this, "Accelerometer Sensor Activated..!", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Log.i("Accel-check","Accelerometer NOT Supported!!");
//                    }
//                }
//                else{
//                    sensorManager.unregisterListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
//                    accX.setText("0");
//                    accY.setText("0");
//                    accZ.setText("0");
//                    Toast.makeText(MainActivity.this, "Accelerometer Sensor De-Activated..!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        //magnetic field sensor
//        magSwi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(magSwi.isChecked()){
//                    Sensor magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//                    if(magnetometer !=null){
//                        sensorManager.registerListener((SensorEventListener) MainActivity.this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
//                        Toast.makeText(MainActivity.this, "Magnetometer Sensor Activated..!", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Log.i("Magne-check","Magnetometer NOT Supported!!");
//                    }
//                }
//                else{
//                    sensorManager.unregisterListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
//                    magX.setText("0");
//                    magY.setText("0");
//                    magZ.setText("0");
//                    Toast.makeText(MainActivity.this, "Magnetometer Sensor De-Activated..!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        //Step detection
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener totalSteps = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Sensor sensorStep = sensorEvent.sensor;
                if(sensorStep != null){
                    float accelX = sensorEvent.values[0];
                    float accelY = sensorEvent.values[1];
                    float accelZ = sensorEvent.values[2];

                    double displacement = Math.sqrt((accelX * accelX) + (accelY * accelY) + (accelZ * accelZ));
                    double displacementChange = displacement - prevDisplacement;
                    prevDisplacement = displacement;

                    if(displacementChange > threshold){
                        stepCount++;
                    }
                    stepC.setText(stepCount.toString());

                    float distance = (float)(stepCount*78)/(float)100000;
                    dist.setText(Float.toString(distance));

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager.registerListener(totalSteps, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);


        //Direction detection
//      Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelerometer !=null){
            sensorManager.registerListener((SensorEventListener) MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(MainActivity.this, "Accelerometer Sensor Activated..!", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.i("Accel-check","Accelerometer NOT Supported!!");
        }

        Sensor magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if(magnetometer !=null){
            sensorManager.registerListener((SensorEventListener) MainActivity.this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(MainActivity.this, "Magnetometer Sensor Activated..!", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.i("Magne-check","Magnetometer NOT Supported!!");
        }


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            Log.i("ACCELEROMETER", "X-axis:" + sensorEvent.values[0] + "Y-axis:" + sensorEvent.values[1]
//                    + "Z-axis:" + sensorEvent.values[2]);

            accX.setText(Float.toString(sensorEvent.values[0]));
            accY.setText(Float.toString(sensorEvent.values[1]));
            accZ.setText(Float.toString(sensorEvent.values[2]));

            gravity = sensorEvent.values;


        }
        if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
//            Log.i("MAGNETOMETER", "X-axis:" + sensorEvent.values[0] + "Y-axis:" + sensorEvent.values[1]
//                    + "Z-axis:" + sensorEvent.values[2]);

            magX.setText(Float.toString(sensorEvent.values[0]));
            magY.setText(Float.toString(sensorEvent.values[1]));
            magZ.setText(Float.toString(sensorEvent.values[2]));

            geoMagneticField = sensorEvent.values;
        }

        //Direction detection
        if (gravity != null && geoMagneticField != null) {
            //R = Rotation Matrix
            float R[] = new float[9];
            float I[] = new float[9];

            if (SensorManager.getRotationMatrix(R, I, gravity, geoMagneticField)) {

                // orientation contains azimut, pitch and roll
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                //azimuth in radians
                azimut = orientation[0];
                //azimuth in degrees
                float degree = (float)(Math.toDegrees(azimut)+360)%360;

                if(degree < 45 || degree >= 315){
                    deg.setText("North");
                }
                else if(degree < 135 && degree >= 45){
                    deg.setText("East");
                }
                else if(degree < 225 && degree >= 135){
                    deg.setText("South");
                }
                else if(degree < 315 && degree >= 225){
                    deg.setText("West");
                }

                //deg.setText(Float.toString(degree));
                //System.out.println("degree " + degree);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.clear();
        e.putInt("Step-Count", stepCount);
        e.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.clear();
        e.putInt("Step-Count", stepCount);
        e.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        stepCount = sp.getInt("Step-Count", 0);
    }
}