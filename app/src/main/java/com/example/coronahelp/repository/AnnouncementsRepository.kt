package com.example.coronahelp.repository

import com.example.coronahelp.model.Announcement
import com.example.coronahelp.model.AnnouncementResponse
import com.example.coronahelp.rest.RestCaller
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDateTime

class AnnouncementsRepository {

    /**
     * use restCaller to call GET method and return list of announcements
     */

    fun getAnnouncementsFromREST(): MutableList<Announcement> {
        val ann = mutableListOf<Announcement>()

        RestCaller.getAnnouncements().forEach { ar ->
            ann.add(ar.toAnnouncement())
        }

        return ann
    }

    fun createAnnouncement(
        title: String,
        description: String,
        category: String,
        reward: Double,
        location: LatLng,
        time: LocalDateTime
    ): Boolean {
        val announcement = Announcement(0, title, description, category, reward, location, time)
        return RestCaller.postAnnouncement(getResponseFromAnnouncement(announcement))
    }

    fun createAnnouncement(announcement: Announcement): Boolean {
        return RestCaller.postAnnouncement(getResponseFromAnnouncement(announcement))
    }

    fun deleteAnnouncement(id: Int): Boolean {
        return RestCaller.deleteAnnouncements(id)
    }

    fun updateAnnouncement(
        id: Int,
        title: String,
        description: String,
        category: String,
        reward: Double,
        location: LatLng,
        time: LocalDateTime
    ): Boolean {
        val announcement = Announcement(id, title, description, category, reward, location, time)
        return RestCaller.patchAnnouncements(getResponseFromAnnouncement(announcement))
    }

    private fun getAnnouncementFromResponse(response: AnnouncementResponse): Announcement {
        return Announcement(
            response.id,
            response.title,
            response.description,
            response.category,
            response.reward,
            LatLng(response.lat.toDouble(), response.long.toDouble()),
            LocalDateTime.now() //TODO <- change THIS!!!!!!!
        )
    }

    private fun getResponseFromAnnouncement(announcement: Announcement): AnnouncementResponse {
        return AnnouncementResponse(
            announcement.id,
            announcement.title,
            announcement.description,
            announcement.category,
            announcement.reward,
            announcement.location.latitude.toString(),
            announcement.location.longitude.toString(),
            announcement.time.toString(),
            announcement.owner,
            announcement.performer
        )
    }

    fun getAnnouncement(id: Int): Announcement? {
        return RestCaller.getAnnoucement(id)
    }
}