package com.example.coronahelp.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

class AnnouncementResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("category")
    val category: Category,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("long")
    val long: String,
    @SerializedName("deadline")
    val time: String
)