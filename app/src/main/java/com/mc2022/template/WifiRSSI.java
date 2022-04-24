package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class WifiRSSI extends AppCompatActivity {

    WifiManager wifiMan;
    WifiReceiver wifiRec;
    ListAdapter adapter;
    ListView listView;
    List wifiInfo;
//    int wifiRSSI;
//    List wifiRSSIValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_rssi);

        //ListView id fetched
        listView = (ListView) findViewById(R.id.listView);


        //Wifi Services
        wifiMan = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiRec = new WifiReceiver();
        registerReceiver(wifiRec, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        else{
            scanWifiFunc();
        }


    }

    private void scanWifiFunc() {
        wifiMan.startScan();
        wifiInfo = wifiMan.getScanResults();
     //   wifiRSSI = wifiMan.getConnectionInfo().getRssi();
        setAdapter();
    }

    private void setAdapter() {
        adapter = new ListAdapter(getApplicationContext(), wifiInfo);
        listView.setAdapter(adapter);
    }

    class WifiReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }


}