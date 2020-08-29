package com.example.coronahelp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coronahelp.model.Announcement
import com.example.coronahelp.model.AnnouncementResponse
import com.example.coronahelp.model.Category
import com.example.coronahelp.rest.RestCaller
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

class AnnouncementsRepository() {

    //private val restCaller = RestCaller()

    init {

    }

    /**
     * use restCaller to call GET method and return list of announcements
     */

    fun getAnnouncementsFromREST(): MutableList<Announcement> {
        val ann = mutableListOf<Announcement>()

        RestCaller.getAnnouncements().forEach { announcement ->
            ann.add(getAnnouncementFromResponse(announcement)) //TODO mówiłem Ci pało żebyś zrobił rzutowanie gdzie indziej
        }

        return ann
    }

    fun createAnnouncement(
        title: String,
        description: String,
        category: Category,
        reward: Double,
        location: LatLng,
        time: LocalDateTime
    ): Boolean {
        val announcement = Announcement(0, title, description, category, reward, location, time)
        return RestCaller.postAnnouncement(getResponseFromAnnouncement(announcement))
    }

    fun deleteAnnouncement(id: Int): Boolean {
        return RestCaller.deleteAnnouncements(id)
    }

    fun updateAnnouncement(
        id: Int,
        title: String,
        description: String,
        category: Category,
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
            announcement.time.toString()
        )
    }
}