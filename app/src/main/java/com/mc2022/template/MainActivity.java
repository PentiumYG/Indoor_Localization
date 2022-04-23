package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

    TextView accX, accY, accZ, magX, magY, magZ, stepC;
    Switch accSwi, magSwi;

    double prevDisplacement = 0;
    Integer stepCount = 0;
    Integer threshold = 6;

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

        //Switch Ids Fetched
        accSwi = (Switch) findViewById(R.id.accSwitch);
        magSwi = (Switch) findViewById(R.id.magSwitch);


        //Sensor Service
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        //Accelerometer sensor
        accSwi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(accSwi.isChecked()){
                    Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    if(accelerometer !=null){
                        sensorManager.registerListener((SensorEventListener) MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, "Accelerometer Sensor Activated..!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.i("Accel-check","Accelerometer NOT Supported!!");
                    }
                }
                else{
                    sensorManager.unregisterListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
                    accX.setText("0");
                    accY.setText("0");
                    accZ.setText("0");
                    Toast.makeText(MainActivity.this, "Accelerometer Sensor De-Activated..!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //magnetic field sensor
        magSwi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(magSwi.isChecked()){
                    Sensor magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                    if(magnetometer !=null){
                        sensorManager.registerListener((SensorEventListener) MainActivity.this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, "Magnetometer Sensor Activated..!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.i("Magne-check","Magnetometer NOT Supported!!");
                    }
                }
                else{
                    sensorManager.unregisterListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
                    magX.setText("0");
                    magY.setText("0");
                    magZ.setText("0");
                    Toast.makeText(MainActivity.this, "Magnetometer Sensor De-Activated..!", Toast.LENGTH_SHORT).show();
                }
            }
        });


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
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager.registerListener(totalSteps, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.i("ACCELEROMETER", "X-axis:" + sensorEvent.values[0] + "Y-axis:" + sensorEvent.values[1]
                    + "Z-axis:" + sensorEvent.values[2]);

            accX.setText(Float.toString(sensorEvent.values[0]));
            accY.setText(Float.toString(sensorEvent.values[1]));
            accZ.setText(Float.toString(sensorEvent.values[2]));


        }
        else if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            Log.i("MAGNETOMETER", "X-axis:" + sensorEvent.values[0] + "Y-axis:" + sensorEvent.values[1]
                    + "Z-axis:" + sensorEvent.values[2]);

            magX.setText(Float.toString(sensorEvent.values[0]));
            magY.setText(Float.toString(sensorEvent.values[1]));
            magZ.setText(Float.toString(sensorEvent.values[2]));


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}