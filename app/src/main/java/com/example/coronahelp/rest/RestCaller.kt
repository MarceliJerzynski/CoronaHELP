package com.example.coronahelp.rest

import androidx.lifecycle.MutableLiveData
import com.example.coronahelp.model.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.gson.responseObject
import com.google.gson.Gson
import com.google.gson.JsonParser

class RestCaller {

    private val mainUrl: String = "http://covid.gorskiszym.usermd.net"
    private val restAnnouncementUrl: String = "/api/tasks"
    private val restRegisterUrl: String = "/register"
    private val restLoginUrl: String = "/login/token"

    private lateinit var token: String //TODO



    /**
     * This method is calling GET method to server and returns MutableLiveData of announcements
     */
    fun getAnnouncements():  MutableLiveData<ResponseAnnouncementsModel> {
        val announcements = MutableLiveData<ResponseAnnouncementsModel>()
        val error = MutableLiveData<Throwable>()

        Fuel.get(mainUrl + restAnnouncementUrl)
            .header(
                "Content-type" to "application/json",
                "Authorization" to "Bearer: $token"
            )
            .responseObject<ResponseAnnouncementsModel> { _, _, result ->
                result.fold({
                    announcements.value = it
                }, {
                    error.value = it.exception
                })
            }
        return announcements
    }

    fun postAnnouncements(announcement: Announcement): Boolean {
        var success = false
        val body = Gson().toJson(announcement)

        Fuel.post(mainUrl + restAnnouncementUrl)
            .header(
                "Content-type" to "application/json",
                "Authorization" to "Bearer: $token"
            )
            .body(
                body.toString()
            )
            .responseObject<ResponseAnnouncementsModel> { _, _, result ->
                result.fold({
                    success = true
                }, {
                    success = false
                })
            }
        return success
    }

    fun deleteAnnouncements(id: Int): Boolean {
        var success = false

        Fuel.post(mainUrl + restAnnouncementUrl + id)
            .header(
                "Content-type" to "application/json",
                "Authorization" to "Bearer: $token"
            )
            .responseObject<ResponseAnnouncementsModel> { _, _, result ->
                result.fold({
                    success = true
                }, {
                    success = false
                })
            }
        return success
    }

    fun patchAnnouncements(id: Int, announcement: Announcement): Boolean {
        var success = false
        val body = Gson().toJson(announcement)

        Fuel.post(mainUrl + restAnnouncementUrl + id)
            .header(
                "Content-type" to "application/json",
                "Authorization" to "Bearer: $token"
            )
            .body(
                body.toString()
            )
            .responseObject<ResponseAnnouncementsModel> { _, _, result ->
                result.fold({
                    success = true
                }, {
                    success = false
                })
            }
        return success
    }

    fun postRegister(params: RegisterParams): Boolean{
        val paramsJson = Gson().toJson(params)
        var success = false

        val (request, response, result) =
            Fuel
                .post(mainUrl + restRegisterUrl)
                .header(
                    "Accept" to "application/json",
                    "Content-type" to "application/json"
                )
                .body(paramsJson.toString())
                .response()
        return response.isSuccessful
    }

    fun postLogin(params: LoginParams): String? {
        var token: String? = null
        var paramsJson = Gson().toJson(params)
        val (request, response, result) =
        Fuel
            .post(mainUrl + restLoginUrl)
            .header(
                "Accept" to "application/json",
                "Content-type" to "application/json"
            )
            .body(paramsJson.toString())
            .response()
        token = JsonParser().parse(String(response.data)).asJsonObject["token"].toString()
        return token
    }

}