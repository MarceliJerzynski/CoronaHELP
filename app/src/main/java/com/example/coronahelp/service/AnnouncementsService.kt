package com.example.coronahelp.service

import android.location.Location
import com.example.coronahelp.model.Announcement
import com.example.coronahelp.repository.AnnouncementsRepository
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.time.LocalDateTime

class AnnouncementsService {

    //val announcementsRepository = AnnouncementsRepository()

    fun createMarkers(map: GoogleMap) { //TODO ICONS
        //announcementsRepository.announcements.forEach { announcement ->
//        listOf<Announcement>(Announcement("SPRZEDAM OPLA", "OPIS", LatLng(0.0, 1.0), LocalDateTime.MAX)).forEach{ announcement ->
//            map.addMarker(MarkerOptions()
//                .position(announcement.location)
//                .title(announcement.title))
//        }
    }
}