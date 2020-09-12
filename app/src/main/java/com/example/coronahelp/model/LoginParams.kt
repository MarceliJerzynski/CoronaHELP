package com.example.coronahelp.model

import com.google.gson.annotations.SerializedName

class LoginParams(
    val email: String,
    val password: String,
    val device_name: String
)