package com.ind.locadr;

import static java.lang.String.format;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class PostLocationUpdate extends AsyncTask<Location, Void, Void> {
    private final Context context;

    public PostLocationUpdate(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Location... locations) {
        final Location location = locations[0];
        final ConnectivityManager connections = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = connections.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                final String url = 
                    "https://locadr.herokuapp.com/whereiam?point=lat-" + 
                        format("%.6f", location.getLatitude()) + ";long" + format("%.6f", location.getLongitude());
                Log.i("com.ind.locadr", "Posting a location update: " + url);
                new DefaultHttpClient().execute(new HttpGet(url));
            } catch (Exception ex) {
                Log.e("com.ind.locadr", "Error posting off url", ex);
            }
        }
        return null;
    }
}
