package com.mc2022.template;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mc2022.template.databases.LocDatabase;
import com.mc2022.template.databases.WifiRSSIDatabase;
import com.mc2022.template.modelClasses.CurrentLocation;
import com.mc2022.template.modelClasses.Wifi;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WifiRSSI extends AppCompatActivity {

    TextView lVal, loVal, wifiN, currlocation;
    Button Loc, currLocB;
    EditText locName;
    int i = 0;


    WifiManager wifiMan;
    WifiReceiver wifiRec;
    ListAdapter adapter;
    ListView listView;
    List wifiInfo;
    List<ScanResult> wifiList2;

    String wifiC = "";
    String locationName = "";
    String currentLocationName = "";
    int currentRSSI;


    //Location related
    LocationManager locManager;
    LocationListener locLis;


    class LocationFunc implements LocationListener{

        @Override
        public void onLocationChanged(@NonNull Location location) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_rssi);

        //ListView id fetched
        listView = (ListView) findViewById(R.id.listView);

        //Location
//        lVal = (TextView) findViewById(R.id.latitudeValue);
//        loVal = (TextView) findViewById(R.id.longitudeValue);
        wifiN = (TextView) findViewById(R.id.nearesrtWifiName);
        Loc = (Button) findViewById(R.id.buttonLoc);
        locName = (EditText) findViewById(R.id.locValue);
        currLocB = (Button) findViewById(R.id.buttonCurrLoc);
        currlocation = (TextView) findViewById(R.id.currLoc);


        //Wifi Services
        wifiMan = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiRec = new WifiReceiver();
        registerReceiver(wifiRec, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        else{
            scanWifiFunc();
            wifiC = wifiList2.get(0).SSID;
            currentRSSI = wifiList2.get(0).level;
        }


        //K-NN implementation where K=n. (matching all to get correct location. (Highest Precision))
        List<CurrentLocation> currentLocations;
        LocDatabase locDatabase = LocDatabase.getInstance(getApplicationContext());
        currentLocations = locDatabase.locDAO().getList();


       // Location Related
        Loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(WifiRSSI.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(WifiRSSI.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(WifiRSSI.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);
                }
                locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locLis = new WifiRSSI.LocationFunc();
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, locLis);
                Toast.makeText(WifiRSSI.this, "GPS Activated..!", Toast.LENGTH_SHORT).show();

                LocDatabase locDatabase = LocDatabase.getInstance(getApplicationContext());

                locationName = locName.getText().toString();

                CurrentLocation currentLocation = new CurrentLocation(locationName, wifiC, wifiList2.get(0).level);
                locDatabase.locDAO().insert(currentLocation);

                wifiN.setText(wifiList2.get(0).SSID);

                Intent intent = new Intent(WifiRSSI.this, LocationWifi.class);
                startActivity(intent);

            }
        });

        //Current Location functionality
        currLocB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(CurrentLocation l : currentLocations) {
                    if (currentRSSI == l.getCurrentWifiRSSI()){
                        currentLocationName = l.getLocName();
                        i = 1;
                        Log.i("LOCATION: ", currentLocationName);
                    }
                }
                if(i == 0){
                    Toast.makeText(WifiRSSI.this, "Location Unavailable!!... Enter new Location..", Toast.LENGTH_SHORT).show();
                }
                currlocation.setText(currentLocationName);
            }
        });






//        if (ContextCompat.checkSelfPermission(WifiRSSI.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(WifiRSSI.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(WifiRSSI.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);
//        }
//        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        locLis = new WifiRSSI.LocationFunc();
//        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, locLis);
//        Toast.makeText(WifiRSSI.this, "GPS Activated..!", Toast.LENGTH_SHORT).show();
//
////        List<Wifi> wifiNames = adapter.wifidb.wifiDAO().getBestWifi();
//////                for(int i=0;i<wifiList2.size();i++){
//////                    Wifi wifi1 = new Wifi(wifiList2.get(i).SSID, wifiList2.get(i).level);
//////                    wifidb2.wifiDAO().insert(wifi1);
//////                }
////
////        for(Wifi w : wifiNames){
////            wifiC += w.getWifiName()+"\n";
////        }
////        Log.i("Top 10 WifiNames:", wifiC);
////        wifiN.setText(wifiNames.get(0).getWifiName());
////
////        wifiC = "";
//
//        wifiN.setText(wifiList2.get(0).SSID);



    }

    private void scanWifiFunc() {
        wifiMan.startScan();
        wifiInfo = wifiMan.getScanResults();
        wifiList2 = wifiInfo;
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