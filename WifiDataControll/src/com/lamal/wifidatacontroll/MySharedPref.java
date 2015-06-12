package com.lamal.wifidatacontroll;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MySharedPref {
	
	private Context context;
	private SharedPreferences pref;

	public MySharedPref(Context context) {
		this.context = context;
		pref = context.getSharedPreferences("WifiDataControll", Context.MODE_PRIVATE);
	}
	
	public void writeToSharedPref(String[] str){
		
		Editor  editor = pref.edit();
		editor.putString("wifion", str[0]);
		editor.putString("wifioff", str[1]);
		editor.commit();
	}
	
	public String[] readFromSharedPref(){
		
		String[] temp = new String[2];
		temp[0] = pref.getString("wifion", "0:0");
		temp[1] = pref.getString("wifioff", "0:0");
		return temp;
	}
}
