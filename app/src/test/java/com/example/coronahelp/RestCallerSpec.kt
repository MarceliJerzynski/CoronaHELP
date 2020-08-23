package com.example.coronahelp

import com.example.coronahelp.model.LoginParams
import com.example.coronahelp.model.RegisterParams
import com.example.coronahelp.rest.RestCaller
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
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
    fun shouldGetAnnouncement() {
        val announcements = RestCaller.getAnnouncements();
        assertNotNull(announcements)
    }

}