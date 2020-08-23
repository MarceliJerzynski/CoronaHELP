package com.example.coronahelp.rest

import android.content.SharedPreferences
import com.example.coronahelp.MainActivity
import com.example.coronahelp.model.Announcement
import com.example.coronahelp.model.LoginParams
import com.example.coronahelp.model.RegisterParams
import com.example.coronahelp.model.ResponseAnnouncementsModel
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.gson.responseObject
import com.google.gson.Gson
import com.google.gson.JsonParser

object RestCaller {

    private const val mainUrl: String = "http://covid.gorskiszym.usermd.net"
    private const val restAnnouncementUrl: String = "/api/tasks"
    private const val restRegisterUrl: String = "/register"
    private const val restLoginUrl: String = "/login/token"

    private lateinit var token: String

    init {
        loadToken()
    }

    /**
     * This method is calling GET method to server and returns MutableLiveData of announcements
     */
    fun getAnnouncements(): ResponseAnnouncementsModel? {

        val (request, response, result) =
            Fuel
                .post(mainUrl + restAnnouncementUrl)
                .header(
                    "Accept" to "application/json",
                    "Content-type" to "application/json",
                    "Authorization" to "Bearer: $token"
                )
                .response()

        val json = JsonParser().parse(String(response.data)).asJsonObject["announcements"].toString()
        val gson = Gson()
        val announcements = gson.fromJson(json, ResponseAnnouncementsModel::class.java)
        return announcements
    }

    fun postAnnouncement(announcement: Announcement): Boolean {
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
        token = token.drop(1).dropLast(1)
        saveToken()
        return token
    }

    private fun saveToken() {
        MainActivity.preferences?.edit()?.apply {
            putString("Token", token)
        }?.apply()
    }

    private fun loadToken() {
        token = MainActivity.preferences?.getString("Token", null).toString()
    }

}