package com.example.coronahelp.model

import com.google.gson.annotations.SerializedName

class ErrorResponse (
    @SerializedName("name")
    var name: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String
)