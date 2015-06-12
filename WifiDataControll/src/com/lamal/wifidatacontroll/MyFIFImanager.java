package com.lamal.wifidatacontroll;

import android.content.Context;
import android.net.wifi.WifiManager;

public class MyFIFImanager {
	
	private Context context;
	
	public MyFIFImanager(Context c){
		context =c;
	}
	
	public void turnOnFIFI(){
		 WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
         wifi.setWifiEnabled(true);
	}
	
	public void turnOffWIFI(){
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
        wifi.setWifiEnabled(false); 
	}

}
