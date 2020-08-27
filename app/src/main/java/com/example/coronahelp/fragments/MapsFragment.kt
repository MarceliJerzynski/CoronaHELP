package com.example.coronahelp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coronahelp.R
import com.example.coronahelp.adapters.AnnouncementRecyclerAdapter
import com.example.coronahelp.model.Announcement
import com.example.coronahelp.model.Category
import com.example.coronahelp.viewModels.MapsFragmentViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import edmt.dev.advancednestedscrollview.MaxHeightRecyclerView
import kotlinx.android.synthetic.main.fragment_maps.*
import java.time.LocalDateTime


class MapsFragment : Fragment(), OnMapReadyCallback {
    lateinit var mapView: MapView
    var map: GoogleMap? = null

    internal lateinit var announcementList:MutableList<Announcement>
    private var isShowingCardHeaderShadow: Boolean = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO if no internet access, show dialog

        //var adapter = AnnouncementRecyclerAdapter()

        //val model: MapsFragmentViewModel by viewModels()
        //model.announcements.observe(this, Observer {
        //    adapter.submitList(it)
        //})

        val announcements:List<Announcement> = generateAnnouncementList()

        val layoutManager = LinearLayoutManager(activity)
        card_recycler_view.layoutManager = layoutManager
        card_recycler_view.adapter = AnnouncementRecyclerAdapter(announcements)
        
        card_recycler_view.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val isRecyclerViewScrolledToTop = layoutManager.findFirstVisibleItemPosition() == 0
                        && layoutManager.findViewByPosition(0)!!.top == 0
                if(!isRecyclerViewScrolledToTop && !isShowingCardHeaderShadow)
                {
                    isShowingCardHeaderShadow = true
                    showOrHideView(card_header_shadow, true)
                }
                else if(isRecyclerViewScrolledToTop && isShowingCardHeaderShadow)
                {
                    isShowingCardHeaderShadow = false
                    showOrHideView(card_header_shadow, false)
                }
            }
        })
        nested_scroll_view.overScrollMode = View.OVER_SCROLL_NEVER
        nested_scroll_view.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener{
            nsv,scrollX, scrollY, oldScrollX, oldScrollY ->
            card_recycler_view.scrollToPosition(0)
            card_header_shadow.alpha = 0f
            isShowingCardHeaderShadow = false
        })
    }

    private fun showOrHideView(view: View?, isShow: Boolean) {
        requireView().animate().alpha(if(isShow) 1f else 0f).setDuration(100)
            .interpolator = DecelerateInterpolator()
    }

    private fun generateAnnouncementList(): MutableList<Announcement> {
        announcementList = ArrayList()
        announcementList.add(
            Announcement(
                    1,
            "Potrzeba sanitarna",
            "Potrzeba na szybko 10 rolek papieru toaletowego!!",
            Category.TOILET_PAPER,
            30.0,
            LatLng(52.0,52.09),
            LocalDateTime.now()
            )
        )

        announcementList.add(
            Announcement(
                1,
                "Potrzeba sanitarna",
                "Potrzeba na szybko 10 rolek papieru toaletowego!!",
                Category.TOILET_PAPER,
                30.0,
                LatLng(52.0,52.09),
                LocalDateTime.now()
            )
        )
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
        mapView!!.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }
}