package com.example.coronahelp.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.coronahelp.MapsActivity
import com.google.android.gms.maps.GoogleMap

object MapUtils {

    fun getMap(googleMap: GoogleMap, mapsActivity: MapsActivity) : GoogleMap {
        var map = googleMap
        map.setOnMyLocationButtonClickListener(mapsActivity)
        map.setOnMyLocationClickListener(mapsActivity)
        return map
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    fun enableMyLocation(googleMap: GoogleMap, mapsActivity: MapsActivity) {
        var map = googleMap

        if (ContextCompat.checkSelfPermission(mapsActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(
                mapsActivity, Parameters.LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION, true
            )
        }
        // [END maps_check_location_permission]
    }
}