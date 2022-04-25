package com.mc2022.template.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mc2022.template.modelClasses.Wifi;

import java.util.List;

@Dao
public interface wifiDAO {

    // list all
    @Query("Select * from WifiTable")
    List<Wifi> getList();

    // Insert
    @Insert
    void insert(Wifi wifi);

//    @Query("Select WifiTable.WifiName from WifiTable where id = 1")
//    String getConnectWifi();

    @Query("SELECT * FROM (SELECT * FROM WifiTable ORDER BY RSSIValue DESC LIMIT 10) ORDER BY id ASC")
    List<Wifi> getBestWifi();

    // Delete using id
    @Query("DELETE from WifiTable where id = :id")
    void deleteUsingID(int id);

    // Delete using object
    @Delete
    void delete(Wifi wifi);
}
