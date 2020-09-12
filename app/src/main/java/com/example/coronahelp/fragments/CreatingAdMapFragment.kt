package com.example.coronahelp.fragments

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.coronahelp.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.creating_ad_map_fragment.*
import java.io.IOException

class CreatingAdMapFragment : Fragment(), OnMapReadyCallback {
    lateinit var mapView: MapView

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    var markerLocation = LatLng(52.40953, 16.93199)
    var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.creating_ad_map_fragment, container, false)

        mapView = v.findViewById<MapView>(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        try {
            MapsInitializer.initialize(requireActivity().applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mapView.getMapAsync(this)

        return v
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map!!.uiSettings.isZoomGesturesEnabled = true
        googleMap.isMyLocationEnabled = true
        val poznan = LatLng(52.40953, 16.93199)
        val cameraPosition = CameraPosition.Builder().target(poznan).zoom(12f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        map!!.setOnMapLongClickListener { latlng ->
            map!!.clear()
            map!!.animateCamera(CameraUpdateFactory.newLatLng(latlng))
            val getcoordinates = LatLng(latlng.latitude, latlng.longitude)

            val markerOptions = MarkerOptions().position(getcoordinates)
            val titlesr = getAddress(getcoordinates)
            markerOptions.title(titlesr)

            map!!.addMarker(
                MarkerOptions()
                    .position(getcoordinates)
                    .title(getAddress(getcoordinates))
            )
            markerLocation = getcoordinates
        }

        map!!.setOnMapClickListener {
            map!!.clear()
        }

        buttonSearchAddress.setOnClickListener {
            val searched_location =
                getLocationFromAddress(requireContext(), editTextAddress.text.toString())
            map!!.clear()
            map!!.addMarker(MarkerOptions().position(searched_location))
            map!!.animateCamera(CameraUpdateFactory.newLatLng(searched_location))
            markerLocation = searched_location
        }

        buttonAccept.setOnClickListener {
            val action =
                CreatingAdMapFragmentDirections.actionCreatingAdMapFragmentToCreateAnnouncement(
                    markerLocation
                )
            it.findNavController().navigate(action)
        }
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    private fun getAddress(lat: LatLng): String? {
        val geocoder = Geocoder(requireContext())
        val list = geocoder.getFromLocation(lat.latitude, lat.longitude, 1)
        return list[0].getAddressLine(0)
    }

    fun getLocationFromAddress(context: Context?, strAddress: String?): LatLng {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1 = LatLng(0.0, 0.0)
        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return LatLng(52.40953, 16.93199)
            }
            val location: Address = address[0]
            p1 = LatLng(location.getLatitude(), location.getLongitude())
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return p1
    }
}