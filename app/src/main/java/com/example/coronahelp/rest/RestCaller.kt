package com.example.coronahelp.rest

import com.example.coronahelp.MainActivity
import com.example.coronahelp.model.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.isSuccessful
import com.google.gson.Gson
import com.google.gson.JsonParser

object RestCaller {

    private const val mainUrl: String = "http://covid.gorskiszym.usermd.net"
    private const val restAnnouncementUrl: String = "/api/tasks"
    private const val restRegisterUrl: String = "/register"
    private const val restLoginUrl: String = "/login/token"

    lateinit var token: String
    lateinit var email: String
    lateinit var name: String

    init {
        loadData()
    }

    /**
     * This method is calling GET method to server and returns MutableLiveData of announcements
     */
    fun getAnnouncements(): List<AnnouncementResponse> {

        val (request, response, result) =
            Fuel
                .get(mainUrl + restAnnouncementUrl)
                .header(
                    "Accept" to "application/json",
                    "Content-type" to "application/json",
                    "Authorization" to "Bearer $token"
                )
                .response()

        val json = JsonParser().parse(String(response.data)).asJsonObject["data"].toString()
        val gson = Gson()
        val announcements = gson.fromJson(json, Array<AnnouncementResponse>::class.java).toList()
        return announcements
    }


    fun getAnnoucement(id: Int): Announcement? {
        val (request, response, result) =
            Fuel
                .get(mainUrl + restAnnouncementUrl + "/" + id)
                .header(
                    "Accept" to "application/json",
                    "Content-type" to "application/json",
                    "Authorization" to "Bearer $token"
                )
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
                .header(
                    "Accept" to "application/json",
                    "Content-type" to "application/json",
                    "Authorization" to "Bearer $token"
                )
                .body(
                    body.toString()
                )
                .response();
        return response.statusCode == 201
    }

    fun deleteAnnouncements(id: Int): Boolean {
        val (request, response, result) =
            Fuel.delete(mainUrl + restAnnouncementUrl + "/" + id)
                .header(
                    "Accept" to "application/json",
                    "Content-type" to "application/json",
                    "Authorization" to "Bearer $token"
                )
                .response()
        return response.statusCode == 200
    }

    fun patchAnnouncements(announcement: AnnouncementResponse): Boolean {
        var success = false
        val body = Gson().toJson(announcement)

        val (request, response, result) =
            Fuel.patch(mainUrl + restAnnouncementUrl + "/" + announcement.id)
                .header(
                    "Accept" to "application/json",
                    "Content-type" to "application/json",
                    "Authorization" to "Bearer $token"
                )
                .body(
                    body.toString()
                )
                .response()
        return response.statusCode == 204;
    }

    fun postRegister(params: RegisterParams): Boolean {
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

    fun postLogin(params: LoginParams): Boolean {
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

        result.fold({
            token = JsonParser().parse(String(response.data)).asJsonObject["token"].toString()
            email = JsonParser().parse(String(response.data)).asJsonObject["email"].toString()
            name = JsonParser().parse(String(response.data)).asJsonObject["name"].toString()
            token = token.drop(1).dropLast(1)
            saveData()
            return true
        }, {
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