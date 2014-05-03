package com.ind.locadr;

import java.util.concurrent.atomic.AtomicReference;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectivityReceiver extends BroadcastReceiver {
    private static final AtomicReference<Location> location = new AtomicReference<Location>();
    
    public static void updateLastLocation(Location latestLocation) {
        location.set(latestLocation);
        Log.i("com.ind.locadr", "no network, so i've been told the latest location is: " + location + "!");
    }
    
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("com.ind.locadr", "the network state changed!");
		Log.i("com.ind.locadr", "the latest location I know is: " + location.get() + "!");
		
		final Location theLocation = location.get();
		if (theLocation != null) {
    		final ConnectivityManager connections = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo activeNetwork = connections.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {
                new PostLocationUpdate(context).execute(theLocation);
                location.set(null);
            }
		}
	}
}
