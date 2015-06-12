package com.lamal.wifidatacontroll;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class ServiceManager {
	
	private Context context;

	public ServiceManager(Context context) {
		this.context = context;
	}
	
	public void turnOnService(){
		
		SharedPreferences pref = context.getSharedPreferences("WifiDataControll", Context.MODE_PRIVATE);
		
		Intent intent = new Intent(context, MyService.class);
		intent.putExtra("wifion", pref.getString("wifion", "0:0"));
		intent.putExtra("wifioff", pref.getString("wifioff", "0:0"));
		
		PendingIntent pendingIntent = PendingIntent.getService(context, 0, 
		                                      intent, PendingIntent.FLAG_UPDATE_CURRENT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.MINUTE, 1);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 30, pendingIntent);
	}
	
	public void turnOffService(){
		Intent intent = new Intent(context, MyService.class);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0, 
                intent, PendingIntent.FLAG_NO_CREATE);

		if (pendingIntent != null)
		{
			AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		    alarmManager.cancel(pendingIntent);
		}
	}
	
	public boolean checkServiceState(){
		
		Intent intent = new Intent(context, MyService.class);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0, 
                intent, PendingIntent.FLAG_NO_CREATE);

		if (pendingIntent != null)
		{
			return true;
		}
		return false;
	}

}
