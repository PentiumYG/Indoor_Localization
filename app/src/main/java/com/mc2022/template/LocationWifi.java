package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.mc2022.template.databases.LocDatabase;
import com.mc2022.template.modelClasses.CurrentLocation;

import java.util.List;

public class LocationWifi extends AppCompatActivity {

    ListView wifiListView;
    List<CurrentLocation> currentLocations;
    WifiLocAdapter wifiLocAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_wifi);

        //ListView id fetched
        wifiListView = (ListView) findViewById(R.id.WifiListView);

        LocDatabase locDatabase = LocDatabase.getInstance(getApplicationContext());
        currentLocations = locDatabase.locDAO().getList();
        wifiLocAdapter = new WifiLocAdapter(getApplicationContext(), currentLocations);
        wifiListView.setAdapter(wifiLocAdapter);

    }
}