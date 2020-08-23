package com.example.coronahelp.repository

import com.example.coronahelp.model.Announcement
import com.example.coronahelp.model.AnnouncementResponse
import com.example.coronahelp.model.Category
import com.example.coronahelp.rest.RestCaller
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDateTime

class AnnouncementsRepository() {

    //private val restCaller = RestCaller()
    var announcements: MutableList<Announcement>

    init {
        announcements = mutableListOf()
        getAnnouncementsFromREST().forEach{ announcement ->
            announcements.add(getAnnouncementFromResponse(announcement))
        }
    }
    /**
     * use restCaller to call GET method and return list of announcements
     */

    fun getAnnouncementsFromREST(): List<AnnouncementResponse> {
        return RestCaller.getAnnouncements()?.announcements ?: listOf<AnnouncementResponse>()
    }

    fun createAnnouncement(title: String, description: String, category: Category, location: LatLng, time: LocalDateTime): Boolean {
        val announcement = Announcement(title, description, category, location, time)
        return RestCaller.postAnnouncement(announcement)
    }

    fun deleteAnnouncement(id: Int): Boolean {
        return RestCaller.deleteAnnouncements(id)
    }

    fun updateAnnouncement(id: Int, title: String, description: String, category: Category, location: LatLng, time: LocalDateTime): Boolean {
        val announcement = Announcement(title, description, category, location, time)
        return RestCaller.patchAnnouncements(id, announcement)
    }

    private fun getAnnouncementFromResponse(response: AnnouncementResponse): Announcement {
        return Announcement(
            response.title,
            response.description,
            response.category,
            LatLng(response.lat.toDouble(), response.long.toDouble()),
            response.time
        )
    }
}