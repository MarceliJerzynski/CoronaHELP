package com.example.coronahelp.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

object MapUtils {

//    fun getMap(googleMap: GoogleMap, mapsActivity: MapsActivity) : GoogleMap {
//        var map = googleMap
//        map.setOnMyLocationButtonClickListener(mapsActivity)
//        map.setOnMyLocationClickListener(mapsActivity)
//        return map
//    }
//
    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    fun enableMyLocation(googleMap: GoogleMap, mapsActivity: AppCompatActivity) {

        if (ContextCompat.checkSelfPermission(
                mapsActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(
                mapsActivity, Parameters.LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION, true
            )
        }
    }

    fun getAddress(context: Context, lat: LatLng): String? {
        val geocoder = Geocoder(context)
        val list = geocoder.getFromLocation(lat.latitude, lat.longitude, 1)
        return list[0].getAddressLine(0)
    }
}