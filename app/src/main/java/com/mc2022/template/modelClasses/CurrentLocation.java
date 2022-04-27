package com.mc2022.template.modelClasses;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LocationTable")
public class CurrentLocation {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "LocName")
    private String locName;

    @ColumnInfo(name = "WifiName")
    private String currentWifi;

    @ColumnInfo(name = "WifiRSSI")
    private int currentWifiRSSI;

//    @ColumnInfo(name = "Latitude")
//    private double userlatitude;
//
//    @ColumnInfo(name = "Longitude")
//    private double userlongitude;

    public CurrentLocation(String locName, String currentWifi, int currentWifiRSSI) {
        this.locName = locName;
        this.currentWifi = currentWifi;
        this.currentWifiRSSI = currentWifiRSSI;
//        this.userlatitude = userlatitude;
//        this.userlongitude = userlongitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getCurrentWifi() {
        return currentWifi;
    }

    public void setCurrentWifi(String currentWifi) {
        this.currentWifi = currentWifi;
    }

    public int getCurrentWifiRSSI() {
        return currentWifiRSSI;
    }

    public void setCurrentWifiRSSI(int currentWifiRSSI) {
        this.currentWifiRSSI = currentWifiRSSI;
    }
//    public double getUserlatitude() {
//        return userlatitude;
//    }
//
//    public void setUserlatitude(double userlatitude) {
//        this.userlatitude = userlatitude;
//    }
//
//    public double getUserlongitude() {
//        return userlongitude;
//    }
//
//    public void setUserlongitude(double userlongitude) {
//        this.userlongitude = userlongitude;
//    }
}