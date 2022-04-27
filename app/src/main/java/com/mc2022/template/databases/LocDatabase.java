package com.mc2022.template.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mc2022.template.interfaces.LocDAO;
import com.mc2022.template.modelClasses.CurrentLocation;

@Database(entities = {CurrentLocation.class},version = 5)
public abstract class LocDatabase extends RoomDatabase {
    public abstract LocDAO locDAO();

    // instance of database
    // we should have single instance of database and should ensure that our database class should be singleton

    public static LocDatabase locDatabaseInst;

    public static LocDatabase getInstance(Context context){

        if(locDatabaseInst == null){
            locDatabaseInst = Room.databaseBuilder(context.getApplicationContext(), LocDatabase.class, "location_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }

        return locDatabaseInst;
    }
}