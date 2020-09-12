package com.example.coronahelp.rest

import com.example.coronahelp.MainActivity
import com.example.coronahelp.model.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.isSuccessful
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

object RestCaller {

    private const val mainUrl: String = "http://covid.gorskiszym.usermd.net"
    private const val restAnnouncementUrl: String = "/api/tasks"
    private const val restRegisterUrl: String = "/register"
    private const val restLoginUrl: String = "/login/token"
    private const val restRegisterToAnnouncementUrl: String = "/api/tasks/register/"
    private const val restConfirmAnnouncementUrl: String = "/api/tasks/confirm/"

    lateinit var token: String
    lateinit var email: String
    lateinit var name: String

    lateinit var lastError: String

    init {
        loadData()

        FuelManager.instance.baseHeaders = mapOf(
            "Accept" to "application/json",
            "Content-type" to "application/json",
            "Authorization" to "Bearer $token"
        )
    }

    /**
     * This method is calling GET method to server and returns MutableLiveData of announcements
     */
    fun getAnnouncements(): List<AnnouncementResponse> {

        val (request, response, result) =
            Fuel
                .get(mainUrl + restAnnouncementUrl)
                .response()

        val json = JsonParser().parse(String(response.data)).asJsonObject["data"].toString()
        val gson = Gson()
        return gson.fromJson(json, Array<AnnouncementResponse>::class.java).toList()
    }


    fun getAnnoucement(id: Int): Announcement? {
        val (request, response, result) =
            Fuel
                .get(mainUrl + restAnnouncementUrl + "/" + id)
                .response()

        val json = JsonParser().parse(String(response.data)).asJsonObject["data"].toString()
        val gson = Gson()
        val announcement = gson.fromJson(json, AnnouncementResponse::class.java)
        return announcement.toAnnouncement()
    }

    fun postAnnouncement(announcement: AnnouncementResponse): Boolean {
        val body = Gson().toJson(announcement)

        val (request, response, result) =
            Fuel
                .post(mainUrl + restAnnouncementUrl)
                .body(
                    body.toString()
                )
                .response();
//        return response.statusCode == 201

        result.fold({
            return true
        }, {
            val json = JsonParser().parse(String(response.data)).asJsonObject["errors"]
            lastError = json.asJsonObject[(json as JsonObject).keySet().iterator().next()].asString
            return false
        })
    }

    fun deleteAnnouncements(id: Int): Boolean {
        val (request, response, result) =
            Fuel.delete(mainUrl + restAnnouncementUrl + "/" + id)
                .response()

        return response.statusCode == 200
    }

    fun patchAnnouncements(announcement: AnnouncementResponse): Boolean {
        var success = false
        val body = Gson().toJson(announcement)

        val (request, response, result) =
            Fuel.patch(mainUrl + restAnnouncementUrl + "/" + announcement.id)
                .body(
                    body.toString()
                )
                .response()

//        return response.statusCode == 204

        result.fold({
            return true
        }, {
            val json = JsonParser().parse(String(response.data)).asJsonObject["errors"]
            return false
        })
    }

    fun postRegister(params: RegisterParams): Boolean {
        val paramsJson = Gson().toJson(params)
        var success = false

        val (request, response, result) =
            Fuel
                .post(mainUrl + restRegisterUrl)
                .body(paramsJson.toString())
                .response()
        return response.isSuccessful
    }

    fun postLogin(params: LoginParams): Boolean {
        var paramsJson = Gson().toJson(params)
        val (request, response, result) =
            Fuel
                .post(mainUrl + restLoginUrl)
                .body(paramsJson.toString())
                .response()

        result.fold({
            token = JsonParser().parse(String(response.data)).asJsonObject["token"].asString
            email = JsonParser().parse(String(response.data)).asJsonObject["email"].asString
            name = JsonParser().parse(String(response.data)).asJsonObject["name"].asString
            saveData()
            return true
        }, {
            return false
        })
    }

    fun registerForAnnouncement(id: Int): Boolean {
        val (request, response, result) =
            Fuel
                .post(mainUrl + restRegisterToAnnouncementUrl + id)
                .response()

        result.fold({
            return true
        }, {
            lastError = JsonParser().parse(String(response.data)).asJsonObject["message"].asString
            return false
        })
    }

    private fun saveData() {
        MainActivity.preferences?.edit()?.apply {
            putString("Token", token)
            putString("Email", email)
            putString("Name", name)
        }?.apply()
    }

    private fun loadData() {
        token = MainActivity.preferences?.getString("Token", null).toString()
        email = MainActivity.preferences?.getString("Email", null).toString()
        name = MainActivity.preferences?.getString("Name", null).toString()
    }

}
