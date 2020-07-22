package com.example.coronahelp.repository

import com.example.coronahelp.model.Announcement
import com.example.coronahelp.rest.RestCaller

class AnnouncementsRepository {

    val restCaller = RestCaller()
    var announcements: List<Announcement>

    constructor() {
        announcements = getAnnouncementsFromREST()
    }
    /**
     * use restCaller to call GET method and return list of announcements
     */

    fun getAnnouncementsFromREST(): List<Announcement> {
        return restCaller.getAnnouncements().value?.announcements ?: listOf<Announcement>()
    }
}