package com.example.coronahelp

import com.example.coronahelp.model.*
import com.example.coronahelp.rest.RestCaller
import com.google.android.gms.maps.model.LatLng
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import java.time.LocalDateTime
import java.util.logging.Logger.getLogger

class RestCallerSpec {

    /**
     *  this test is not mocked, so every run will create database record
     *  also because of this every time you run it you have to change email variable.
     */
//    @Test
//    fun shouldRegister() {
//        var name = "test"
//        var email = "admin4@admin.com"
//        var password = "1234567890";
//        var registerParams = RegisterParams(name, email, password, password)
//        var response = RestCaller.postRegister(registerParams)
//        assert(response)
//    }

    /**
     * It should be done before other tests, because RestCaller instance needs to have token
     */
    @Before
    @Test
    fun shouldLogin() {
        var email = "admin@admin.com"
        var password = "1234567890";
        var loginParams = LoginParams(email, password, "MODEL")
        var response = RestCaller.postLogin(loginParams)
        println("Token is: $response")
        assertNotNull(response)
    }

    @Test
    fun shouldAddAnnouncement() {
        val announcement = AnnouncementResponse(
        "Potrzeba sanitarna",
        "Potrzeba na szybko 10 rolek papieru toaletowego!!",
        Category.TOILET_PAPER,
        "52.00", "52.09",
        LocalDateTime.now().toString())

        val result = RestCaller.postAnnouncement(announcement)
        assert(result)
    }

    @Test
    fun shouldGetAnnouncement() {
        val announcements = RestCaller.getAnnouncements();
        assertNotNull(announcements)
        println("Our announcements: $announcements")
    }

    @Test
    fun shouldGiveMeGoodFuckingFormatOfDate() {
        val text = LocalDateTime.now().toString()
        println(text)
    }

}