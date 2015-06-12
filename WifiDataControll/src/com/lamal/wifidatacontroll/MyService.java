package com.lamal.wifidatacontroll;

import java.util.Calendar;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyService extends IntentService {

	public MyService() {
		super("myIntentService");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
		String wifion = arg0.getStringExtra("wifion");
		String wifioff = arg0.getStringExtra("wifioff");
		String[] on = wifion.split(":");
		String[] off = wifioff.split(":");
		
		int startHour = Integer.parseInt(on[0]);
		int sartMin = Integer.parseInt(on[1]);
		int startTime = (startHour*100) + sartMin;
		
		
		int stopHour = Integer.parseInt(off[0]);
		int stopMin = Integer.parseInt(off[1]);
		int stoptime = (stopHour*100) + stopMin;
		
		System.out.println(wifion+"-"+wifioff);
		
		Calendar c = Calendar.getInstance(); 
		int cHour = c.get(Calendar.HOUR_OF_DAY);
		int cMin = c.get(Calendar.MINUTE);
		int cTime = (cHour*100) + cMin;
		
		System.out.println(startTime +":"+stoptime+"-"+cTime);
		
		
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mData = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		
		if ((cTime >= startTime) && (cTime <= stoptime) ) {
			System.out.println("office time");
			
			if (mData.isConnected()) {
				new MyMobileDataManager(getApplicationContext()).turnOffMobileData();
			}
			
			if (!mWifi.isConnected()) {
				new MyFIFImanager(getApplicationContext()).turnOnFIFI();
			}
			
			
		} else {
			
			/*if (mWifi.isAvailable()) {
				if (!mWifi.isConnected()) {
					new MyFIFImanager(getApplicationContext()).turnOnFIFI();
				}
				if (!mWifi.isConnected()) {
					new MyMobileDataManager(getApplicationContext()).turnOnMobileData();
				}
			}*/
			
			if (!mData.isConnected()) {
				new MyMobileDataManager(getApplicationContext()).turnOnMobileData();
			}
			
			if (mWifi.isConnected()) {
				new MyFIFImanager(getApplicationContext()).turnOffWIFI();
			}
		}
		
	}

}