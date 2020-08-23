package com.example.coronahelp.repository

import com.example.coronahelp.model.Announcement
import com.example.coronahelp.model.Category
import com.example.coronahelp.rest.RestCaller
import java.time.LocalDateTime

class AnnouncementsRepository() {

    val restCaller = RestCaller()
    var announcements: List<Announcement>

    init {
        announcements = getAnnouncementsFromREST()
    }
    /**
     * use restCaller to call GET method and return list of announcements
     */

    fun getAnnouncementsFromREST(): List<Announcement> {
        return restCaller.getAnnouncements().value?.announcements ?: listOf<Announcement>()
    }

    fun createAnnouncement(title: String, description: String, category: Category, lat: String, long: String, time: LocalDateTime): Boolean {
        val announcement = Announcement(title, description, category, lat, long, time)
        return restCaller.postAnnouncements(announcement)
    }

    fun deleteAnnouncement(id: Int): Boolean {
        return restCaller.deleteAnnouncements(id)
    }

    fun updateAnnouncement(id: Int, title: String, description: String, category: Category, lat: String, long: String, time: LocalDateTime): Boolean {
        val announcement = Announcement(title, description, category, lat, long, time)
        return restCaller.patchAnnouncements(id, announcement)
    }
}