package com.ind.locadr;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

class DefaultLocationListener implements LocationListener {
    private final Context context;

    public DefaultLocationListener(Context context) {
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("com.ind.locadr", "Received a location update: " + location.toString());
        final ConnectivityManager connections = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo activeNetwork = connections.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            new PostLocationUpdate(context).execute(location);
        } else {
            ConnectivityReceiver.updateLastLocation(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}