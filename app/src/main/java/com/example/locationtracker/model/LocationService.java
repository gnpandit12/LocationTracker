package com.example.locationtracker.model;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.locationtracker.MainActivity;
import com.google.android.gms.location.LocationResult;

public class LocationService extends BroadcastReceiver {

    public static final String ACTION_PROCESS_UPDATE = "com.example.locationtracker.UPDATE_LOCATION";
    private static final String TAG = "location_service";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null){
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATE.equals(action)){
                LocationResult locationResult = LocationResult.extractResult(intent);
                if (locationResult != null){
                    final Location location = locationResult.getLastLocation();
                    if (location != null){
                        try {
                            MainActivity.getInstance()
                                    .runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            String  locationString = new StringBuilder(" "+location.getLatitude())
                                                    .append("/")
                                                    .append(location.getLongitude())
                                                    .toString();
                                            Toast.makeText(MainActivity.getInstance(), "Current Location: "+locationString, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }catch (Exception e){
                            Log.d(TAG, "Exception: "+e.toString());
                        }
                    }else {
                        Log.d(TAG, "Location Null!");
                    }
                }else {
                    Log.d(TAG, "LocationResult Null!");
                }
            }

        }else {
            Log.d(TAG, "Intent Null!");
        }

    }


}
