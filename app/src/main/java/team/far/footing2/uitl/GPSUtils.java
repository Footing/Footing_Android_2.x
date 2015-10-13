package team.far.footing2.uitl;

import android.content.Context;
import android.location.LocationManager;

import team.far.footing2.app.APP;

/**
 *
 * Created by moi on 2015/8/25.
 */
public class GPSUtils {

    public static boolean isGpsEnable() {
        LocationManager locationManager = ((LocationManager) APP.getContext().getSystemService(Context.LOCATION_SERVICE));
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
