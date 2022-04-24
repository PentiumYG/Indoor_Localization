package com.mc2022.template;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<ScanResult> wifiList;
    //int wifiRSSI;


    public ListAdapter(Context context, List<ScanResult> wifiList) {
        this.context = context;
        this.wifiList = wifiList;
      //  this.wifiRSSI = wifiRSSI;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return wifiList.size();
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
        ViewHolder holder = new ViewHolder();
        if(view == null){
            view = inflater.inflate(R.layout.list_item, null);
            holder.wifiName = (TextView) view.findViewById(R.id.wifiName);
            holder.rssiValue = (TextView) view.findViewById(R.id.rssiValue);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        holder.wifiName.setText(wifiList.get(i).SSID);
        holder.rssiValue.setText(Integer.toString(wifiList.get(i).level));

        return view;
    }

    class ViewHolder{
        TextView wifiName;
        TextView rssiValue;
    }

}
