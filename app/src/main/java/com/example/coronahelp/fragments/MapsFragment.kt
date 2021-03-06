package com.example.coronahelp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.colorgreen.swiper.OnSwipeTouchListener
import com.colorgreen.swiper.SwipeAction
import com.colorgreen.swiper.SwipeActionListener
import com.example.coronahelp.MainActivity
import com.example.coronahelp.R
import com.example.coronahelp.adapters.AnnouncementRecyclerAdapter
import com.example.coronahelp.model.Announcement
import com.example.coronahelp.rest.RestCaller
import com.example.coronahelp.viewModels.MapsFragmentViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*
import java.time.LocalDateTime


class MapsFragment : Fragment(), OnMapReadyCallback {
    lateinit var mapView: MapView
    var map: GoogleMap? = null

    internal lateinit var announcementList: MutableList<Announcement>

    var isOpen = false

    val model: MapsFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        hello_username.text = "Hello " + RestCaller.name

        val fabOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(context, R.anim.fab_close)
        val fabRClockwise = AnimationUtils.loadAnimation(context, R.anim.rotate_clockwise)
        val fabRAntiClockwise = AnimationUtils.loadAnimation(context, R.anim.rotate_anticlockwise)

        refresh_button.setOnClickListener {
            model.refreshAnnouncements()
        }

        myAnnouncmentsButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_mapsFragment_to_myAnnouncementsFragment)
        }

        fab.setOnClickListener {
            if (isOpen) {
                fab_add.startAnimation(fabClose)
                fab_sign_out.startAnimation(fabClose)
                fab.startAnimation(fabRClockwise)

                isOpen = false
            } else {
                fab_add.startAnimation(fabOpen)
                fab_sign_out.startAnimation(fabOpen)
                fab.startAnimation(fabRAntiClockwise)

                fab_add.isClickable
                fab_sign_out.isClickable
                isOpen = true
            }
        }

        fab_add.setOnClickListener {
            val action = MapsFragmentDirections.actionMapsFragmentToCreateAnnouncement(map?.cameraPosition!!.target)
            view.findNavController().navigate(action)
        }

        fab_sign_out.setOnClickListener {
            view.findNavController().navigate(R.id.action_mapsFragment_to_login)
            MainActivity.preferences?.edit()?.apply {
                putString("Token", null)
                putString("Email", null)
                putString("Name", null)
            }?.apply()
        }


        if (!isUserLogged()!!) {
            view.findNavController().navigate(R.id.action_mapsFragment_to_login)
        } else {

            //TODO if no internet access, show dialog

            val layoutManager = LinearLayoutManager(activity)
            recycler_view.layoutManager = layoutManager
            recycler_view.adapter = AnnouncementRecyclerAdapter(mutableListOf())

            model.announcements.observe(viewLifecycleOwner, Observer {
                (recycler_view.adapter as AnnouncementRecyclerAdapter).submitList(it)
                for (task in it) {
                    map?.addMarker(MarkerOptions().position(task.location).title(task.title))
                }
            })

        }
    }


    private fun isUserLogged(): Boolean? {
        return MainActivity.preferences?.contains("Token")
    }

    private fun generateAnnouncementList(): MutableList<Announcement> {
        announcementList = ArrayList()

        for (i in 0..7) {
            announcementList.add(
                Announcement(
                    1,
                    "Potrzeba sanitarna",
                    "Potrzeba na szybko 10 rolek papieru toaletowego!!",
                    "Zakupy",
                    30.0,
                    LatLng(52.0, 52.09),
                    LocalDateTime.now()
                )
            )
        }

        return announcementList
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_maps, container, false)

        mapView = v.findViewById<MapView>(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()

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

        googleMap.isMyLocationEnabled = true

        val poznan = LatLng(52.40953, 16.93199)
        googleMap.addMarker(
            MarkerOptions().position(poznan).title("Poznań").snippet("Poznań - miasto")
        )

        val cameraPosition =
            CameraPosition.Builder().target(poznan).zoom(12f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }

    override fun onResume() {
//        mapView.onResume()
        model.refreshAnnouncements()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
//        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}