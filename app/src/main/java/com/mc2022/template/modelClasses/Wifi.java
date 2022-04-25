package com.mc2022.template.modelClasses;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "WifiTable")
public class Wifi {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "WifiName")
    private String wifiName;

    @ColumnInfo(name = "RSSIValue")
    private int rssiVal;

    public Wifi(String wifiName, int rssiVal) {
        this.wifiName = wifiName;
        this.rssiVal = rssiVal;
    }

    public String getWifiName() {
        return wifiName;
    }

    public int getRssiVal() {
        return rssiVal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public void setRssiVal(int rssiVal) {
        this.rssiVal = rssiVal;
    }
}
