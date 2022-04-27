package com.mc2022.template;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mc2022.template.databases.LocDatabase;
import com.mc2022.template.databases.WifiRSSIDatabase;
import com.mc2022.template.modelClasses.CurrentLocation;
import com.mc2022.template.modelClasses.Wifi;

import java.util.List;

public class WifiLocAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<CurrentLocation> wifiLocationList;
    String lo = "";
    LocDatabase locDatabase;
    CurrentLocation currentLocation;


    public WifiLocAdapter(Context context, List<CurrentLocation> wifiLocationList) {
        this.context = context;
        this.wifiLocationList = wifiLocationList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return wifiLocationList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        WifiLocAdapter.ViewHolder holder = new WifiLocAdapter.ViewHolder();
        if(view == null){
            view = inflater.inflate(R.layout.wifi_list_item, null);
            holder.location = (TextView) view.findViewById(R.id.currentLocationName);
            holder.currentWifiName = (TextView) view.findViewById(R.id.currentWifiName);
            holder.currentRssiValue = (TextView) view.findViewById(R.id.currentRssiValue);
            // holder.wifiCurrent = (TextView) view.findViewById(R.id.nearesrWifiName);
            view.setTag(holder);
        }
        else{
            holder = (WifiLocAdapter.ViewHolder) view.getTag();
        }

        locDatabase = LocDatabase.getInstance(context);
//        CurrentLocation currentLocation = new CurrentLocation(wifiLocationList.get(i).getLocName(), wifiLocationList.get(i).getCurrentWifi(), wifiLocationList.get(i).getCurrentWifiRSSI());
//        locDatabase.locDAO().delete(currentLocation);

        // Get List
        List<CurrentLocation> wifiLocEntries = locDatabase.locDAO().getList();

//        for(CurrentLocation l : wifiLocEntries){
//            lo += l.getLocName()+" "+l.getCurrentWifi()+" "+l.getCurrentWifiRSSI()+"\n";
//        }
//        Log.i("WifiLocations:", lo);

        holder.location.setText(wifiLocEntries.get(i).getLocName());
        holder.currentWifiName.setText(wifiLocEntries.get(i).getCurrentWifi());
        holder.currentRssiValue.setText(Integer.toString(wifiLocEntries.get(i).getCurrentWifiRSSI()));

        return view;
    }

    class ViewHolder{
        TextView location;
        TextView currentWifiName;
        TextView currentRssiValue;
    }
}
