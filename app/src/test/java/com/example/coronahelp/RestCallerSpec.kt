package com.example.coronahelp

import com.example.coronahelp.model.*
import com.example.coronahelp.rest.RestCaller
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class RestCallerSpec {

    /**
     *  this test is not mocked, so every run will create database record
     *  also because of this every time you run it you have to change email variable.
     *  -------IMPORTANT-------
     *  these tests should be runned only by someone who knows what it does, because its changing DB!!!!
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
            1,
        "Potrzeba sanitarna",
        "Potrzeba na szybko 10 rolek papieru toaletowego!!",
        Category.TOILET_PAPER,
            30.0,
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
    fun shouldPatchAnnounecement() {
        val announcement = AnnouncementResponse(
            1,
            "Potrzeba sanitarna",
            "Potrzeba na szybko 10 rolek srajtaśmy!!",
            Category.TOILET_PAPER,
            30.0,
            "52.00", "52.09",
            LocalDateTime.now().toString())

        val result = RestCaller.patchAnnouncements(announcement)
        assert(result)
    }

    @Test
    fun shouldDelete() {
        val id = 1;
        val result = RestCaller.deleteAnnouncements(id)
        assert(result)
    }

}