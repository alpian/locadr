package com.ind.locadr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("com.ind.locadr", "my alarm got fired, whoop whoop!");
		LocationManager locations = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		locations.requestSingleUpdate(LocationManager.GPS_PROVIDER, new DefaultLocationListener(context), null);
		locations.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new DefaultLocationListener(context), null);
	}
}
