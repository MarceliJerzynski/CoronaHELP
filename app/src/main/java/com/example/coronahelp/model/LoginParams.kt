package com.example.coronahelp.model

import com.google.gson.annotations.SerializedName

class LoginParams(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("device_name")
    val device_name: String
)