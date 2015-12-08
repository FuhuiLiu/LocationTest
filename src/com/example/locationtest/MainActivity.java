package com.example.locationtest;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView positionTextView;
	private LocationManager locationManager;
	private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取文本控件
        positionTextView = (TextView) findViewById(R.id.position_text_view);
        //获取位置管理器
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        if(providerList.contains(LocationManager.GPS_PROVIDER))
        {
        	provider = LocationManager.GPS_PROVIDER;
        }
        else if (providerList.contains(LocationManager.NETWORK_PROVIDER))
        {
        	provider = LocationManager.NETWORK_PROVIDER;
        }
        else
        {
        	//当没有可用的位置提供器时，弹出Toast提示用户
        	Toast.makeText(this, "No location provider to use", Toast.LENGTH_LONG).show();
        	return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if(location != null)
        	showLocation(location);
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
    }
    
	protected void onDestroy() {
		super.onDestroy();
		if(locationManager != null)
		{
			//关闭程序时将监听器移除
			locationManager.removeUpdates(locationListener);
		}
	}
    
    LocationListener locationListener = new LocationListener()
    {

		@Override
		public void onLocationChanged(Location arg0) {
			// TODO Auto-generated method stub
			//更新当前设备的位置信息
			showLocation(arg0);
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
	};

	private void showLocation(Location location)
    {
    	String currentPostion = "latitude is " + location.getLatitude() + "\n"
    			+ "longitude is " + location.getLongitude();
    	positionTextView.setText(currentPostion);
    }
}
