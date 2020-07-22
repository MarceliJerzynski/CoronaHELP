package com.example.coronahelp.rest

import androidx.lifecycle.MutableLiveData
import com.example.coronahelp.model.Announcement
import com.example.coronahelp.model.ResponseAnnouncementsModel
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject

class RestCaller {

    private val restUrl: String = "https://kornel.com"

    /**
     * This method is calling GET method to server and returns MutableLiveData of announcements
     */
    fun getAnnouncements():  MutableLiveData<ResponseAnnouncementsModel> {
        val announcements = MutableLiveData<ResponseAnnouncementsModel>()
        val error = MutableLiveData<Throwable>()
        Fuel.get(restUrl)
            .responseObject<ResponseAnnouncementsModel> { _, _, result ->
                result.fold({
                    announcements.value = it
                }, {
                    error.value = it.exception
                })
            }
        return announcements
    }

}