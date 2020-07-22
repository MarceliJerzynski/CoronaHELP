package com.example.coronahelp.model

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

class Announcement(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("location")
    val location: LatLng,
    @SerializedName("time")
    val time: LocalDateTime
    //val person: Person
)