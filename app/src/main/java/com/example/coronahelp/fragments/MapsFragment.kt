package com.example.coronahelp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.colorgreen.swiper.OnSwipeTouchListener
import com.colorgreen.swiper.SwipeAction
import com.colorgreen.swiper.SwipeActionListener
import com.example.coronahelp.MainActivity
import com.example.coronahelp.R
import com.example.coronahelp.adapters.AnnouncementRecyclerAdapter
import com.example.coronahelp.model.Announcement
import com.example.coronahelp.model.Category
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
    private var isShowingCardHeaderShadow: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fab_add.setOnClickListener{
            view.findNavController().navigate(R.id.action_mapsFragment_to_createAnnouncement2)
        }

        button_logout.setOnClickListener{
            view.findNavController().navigate(R.id.action_mapsFragment_to_login)
            MainActivity.preferences?.edit()?.apply {
                putString("Token", null)
            }?.apply()
        }

        if (!isUserLogged()!!) {
            view.findNavController().navigate(R.id.action_mapsFragment_to_login)
        } else {


            //TODO if no internet access, show dialog

            val layoutManager = LinearLayoutManager(activity)
            recycler_view.layoutManager = layoutManager
            recycler_view.adapter = AnnouncementRecyclerAdapter(mutableListOf())

            val model: MapsFragmentViewModel by viewModels()
            model.announcements.observe(viewLifecycleOwner, Observer {
                (recycler_view.adapter as AnnouncementRecyclerAdapter).submitList(it)
            })

            val listener = OnSwipeTouchListener()

            view.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this);

                    val targetHeight = view.height.toFloat()
                    bottom_panel.y = targetHeight - 500

                    recycler_view_ll.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        (targetHeight - 200).toInt()
                    )

                    val swipeAction = SwipeAction()
                    swipeAction.direction = SwipeAction.DragDirection.Up
                    swipeAction.setSteps(floatArrayOf(bottom_panel.y, 300f))
                    swipeAction.swipeActionListener = object : SwipeActionListener {
                        override fun onDragStart(value: Float, totalFriction: Float) {}
                        override fun onDragEnd(p0: Float, p1: Float) {}

                        override fun onDrag(value: Float, friction: Float) {
                            bottom_panel.setY(value)
                        }
                    }

                    listener.addAction(swipeAction)
                }
            })

            listener.attachToView(bottom_panel)
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
                    Category.TOILET_PAPER,
                    30.0,
                    LatLng(52.0, 52.09),
                    LocalDateTime.now()
                )
            )
        }

        return announcementList
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        googleMap.addMarker(MarkerOptions().position(poznan).title("Poznań").snippet("Poznań - miasto"))

        val cameraPosition =
            CameraPosition.Builder().target(poznan).zoom(12f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

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
}