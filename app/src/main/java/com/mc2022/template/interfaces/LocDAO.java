package com.mc2022.template.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mc2022.template.modelClasses.CurrentLocation;
import com.mc2022.template.modelClasses.Wifi;

import java.util.List;

@Dao
public interface LocDAO {
    //List all
    @Query("Select * from LocationTable")
    List<CurrentLocation> getList();

    // Insert
    @Insert
    void insert(CurrentLocation currentLocation);

    // Delete using id
    @Query("DELETE from LocationTable where id = :id")
    void deleteUsingID(int id);

//    @Query("SELECT * FROM (SELECT * FROM LocationTable ORDER BY WifiRSSI DESC) ORDER BY id ASC")
//    List<Wifi> getBestWifi();

    // Delete using object
    @Delete
    void delete(CurrentLocation currentLocation);
}