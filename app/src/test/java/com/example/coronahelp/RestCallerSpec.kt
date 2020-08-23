package com.example.coronahelp

import com.example.coronahelp.model.LoginParams
import com.example.coronahelp.model.RegisterParams
import com.example.coronahelp.rest.RestCaller
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.util.logging.Logger.getLogger

class RestCallerSpec {

    var logger = getLogger("RestCallerSpec")
    /**
     * Each test has its own instance of restCaller names "rest"
     */
    lateinit var rest: RestCaller
    @Before
    fun init() {
        rest = RestCaller()
    }

    /**
     *  this test is not mocked, so every run will create database record
     *  also because of this every time you run it you have to change email variable.
     */
    @Test
    fun shouldRegister() {
        var name = "test"
        var email = "admin3@admin.com"
        var password = "1234567890";
        var registerParams = RegisterParams(name, email, password, password)
        var response = rest.postRegister(registerParams)
        assert(response)
    }

    @Test
    fun testLoginRestApi() {
        var email = "admin@admin.com"
        var password = "1234567890";
        var loginParams = LoginParams(email, password, "MODEL")
        var response = rest.postLogin(loginParams)
        println("Token is: $response")
        assertNotNull(response)
    }
}