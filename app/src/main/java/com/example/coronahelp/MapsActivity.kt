package com.example.coronahelp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.core.content.ContextCompat
import com.example.coronahelp.service.AnnouncementsService
import com.example.coronahelp.utils.MapUtils
import com.example.coronahelp.utils.MapUtils.enableMyLocation
import com.example.coronahelp.utils.PermissionUtils
import com.example.coronahelp.utils.PermissionUtils.PermissionDeniedDialog.Companion.newInstance
import com.example.coronahelp.utils.PermissionUtils.isPermissionGranted
import com.example.coronahelp.utils.PermissionUtils.requestPermission
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapsActivity : AppCompatActivity(), OnMyLocationButtonClickListener,
        OnMyLocationClickListener, OnMapReadyCallback, OnRequestPermissionsResultCallback {

    private var permissionDenied = false
    private lateinit var map: GoogleMap

    val announcementsService = AnnouncementsService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = MapUtils.getMap(googleMap, this)
        enableMyLocation(map, this)

        announcementsService.createMarkers(map)
    }

    override fun onMyLocationButtonClick(): Boolean {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onMyLocationClick(location: Location) {}

    // [START maps_check_location_permission_result]
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) =
        if (PermissionUtils.getPermissionsResult(requestCode, permissions, grantResults)) {
            enableMyLocation(map, this)
        } else {
            permissionDenied = true
        }

    // [END maps_check_location_permission_result]
    override fun onResumeFragments() {
        super.onResumeFragments()
        permissionDenied = PermissionUtils.onResume(permissionDenied, supportFragmentManager)
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private fun showMissingPermissionError() {
        newInstance(true).show(supportFragmentManager, "dialog")
    }
}