package com.mc2022.template.modelClasses;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LocationTable")
public class CurrentLocation {
    @PrimaryKey(autoGenerate = true)
    private int id;

//    @ColumnInfo(name = "Name")
//    private String username;

//    @ColumnInfo(name = "Address")
//    private String useraddress;

    @ColumnInfo(name = "Latitude")
    private double userlatitude;

    @ColumnInfo(name = "Longitude")
    private double userlongitude;

    public CurrentLocation(double userlatitude, double userlongitude) {
//        this.username = username;
//        this.useraddress = useraddress;
        this.userlatitude = userlatitude;
        this.userlongitude = userlongitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getUseraddress() {
//        return useraddress;
//    }
//
//    public void setUseraddress(String useraddress) {
//        this.useraddress = useraddress;
//    }

    public double getUserlatitude() {
        return userlatitude;
    }

    public void setUserlatitude(double userlatitude) {
        this.userlatitude = userlatitude;
    }

    public double getUserlongitude() {
        return userlongitude;
    }

    public void setUserlongitude(double userlongitude) {
        this.userlongitude = userlongitude;
    }
}