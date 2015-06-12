package com.lamal.wifidatacontroll;

import com.lamal.wifidatacontroll.R.id;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;


public class MainActivity extends Activity {

	private TimePicker arr, dep;
	private Button bState;
	ServiceManager sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arr = (TimePicker) findViewById(id.timePicker_arr);
        dep = (TimePicker) findViewById(id.timePicker_dep);
        bState = (Button) findViewById(id.button_State);

        sm = new ServiceManager(getApplicationContext());
        
        if (sm.checkServiceState()) {
        	bState.setText("Active");
		} else {
			bState.setText("ZZzzz");
		}
        
        bState.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int arrH = arr.getCurrentHour();
				int arrM = arr.getCurrentMinute();
				
				int depH = dep.getCurrentHour();
				int depM = dep.getCurrentMinute();
				
				setNewTiming(arrH, arrM, depH, depM);
				
				sm.turnOnService();
				
				//alterState();
				bState.setText("Active");
			}
		});
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void setNewTiming(int arrH, int arrM, int depH, int depM){
        String[] data = new String[2];
        data [0] = arrH+":"+arrM;
        data [1] = depH+":"+depM;
        System.out.println(data[0]+data[1]);
        new MySharedPref(getApplicationContext()).writeToSharedPref(data);
    }
    
    public void setCal(){
    	
        SharedPreferences pref = getSharedPreferences("WifiDataControll", Context.MODE_PRIVATE);

    	String wifion = pref.getString("wifion", "0:0");
		String wifioff = pref.getString("wifioff", "0:0");
		String[] on = wifion.split(":");
		String[] off = wifioff.split(":");
		
		int startHour = Integer.parseInt(on[0]);
		int sartMin = Integer.parseInt(on[1]);
		arr.setCurrentHour(startHour);
		arr.setCurrentMinute(sartMin);
		
		int stopHour = Integer.parseInt(off[0]);
		int stopMin = Integer.parseInt(off[1]);
		dep.setCurrentHour(stopHour);
		dep.setCurrentMinute(stopMin);
    }
    
	private void alterState() {
		// TODO Auto-generated method stub
        if (sm.checkServiceState()) {
        	bState.setText("ZZzzz");
        	sm.turnOffService();
		} else {
			bState.setText("Active");
			sm.turnOnService();
		}
        
        ViewGroup vg = (ViewGroup) findViewById (R.id.mainLayout);
        vg.invalidate();
	}
}
