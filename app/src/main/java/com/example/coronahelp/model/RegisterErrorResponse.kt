package com.example.coronahelp.model

import com.google.gson.annotations.SerializedName

class RegisterErrorResponse(
    @SerializedName("message")
    var message: String,
    @SerializedName("errors")
    var errors: ErrorResponse
)