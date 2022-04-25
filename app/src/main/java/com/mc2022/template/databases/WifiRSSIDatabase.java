package com.mc2022.template.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mc2022.template.ListAdapter;
import com.mc2022.template.WifiRSSI;
import com.mc2022.template.interfaces.wifiDAO;
import com.mc2022.template.modelClasses.Wifi;

@Database(entities = {Wifi.class},version = 6)
public abstract class WifiRSSIDatabase extends RoomDatabase {

    // database object
    public abstract wifiDAO wifiDAO();

    // instance of database
    // we should have single instance of database and should ensure that our database class should be singleton

    public static WifiRSSIDatabase wifiDatainstance;

    public static  WifiRSSIDatabase getInstance(Context context){

        if(wifiDatainstance == null){
            wifiDatainstance = Room.databaseBuilder(context.getApplicationContext(), WifiRSSIDatabase.class, "wifi_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }

        return wifiDatainstance;
    }

}
