package com.example.java_gps;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private LocationManager locationManager = null;	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(mButton1Listener);
    }
    
    private OnClickListener mButton1Listener = new OnClickListener() {
		public void onClick(View v) {
	        if (locationManager != null) {
	        	locationManager.removeUpdates(mLocationListener);
	        }
        	locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        	boolean networkFlg =  locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        	boolean gpsFlg = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 0,mLocationListener);
		}
    };
    
    
    private  LocationListener mLocationListener  = new LocationListener() {
        public void onStatusChanged(String provider, int status,Bundle extras) {
        }
        public void onProviderEnabled(String provider) {
        }
        public void onProviderDisabled(String provider) {
        }
        public void onLocationChanged(Location location) {
        	String latitude = Double.toString(location.getLatitude());
        	String longitude = Double.toString(location.getLongitude());
        	String message = "";
            message += ("Latitude"+latitude);
            message += "\n";
            message += ("Longitude"+longitude);
            message += "\n";
            message += ("Accuracy"+Float.toString(location.getAccuracy()));
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            locationManager.removeUpdates(mLocationListener);
            googleMap(latitude,longitude);
        }
    };	
    
    
    @Override
    protected void onPause() {
        if (locationManager != null) {
        	locationManager.removeUpdates(mLocationListener);
        }
        super.onPause();
    }
    
    
    private  void  googleMap(String latitude,String longitude) {
    	String urlStrng = "http://maps.google.co.jp/maps?f=q&hl=ja&geocode=&q=" + latitude + "," + longitude;
		urlStrng +="&ie=UTF8&ll=️" + latitude + "," + longitude;
    	Log.d("Main" , urlStrng);
		Intent intent = null;
        intent = new Intent(Intent.ACTION_VIEW,Uri.parse(urlStrng));
        startActivity(intent); 
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}