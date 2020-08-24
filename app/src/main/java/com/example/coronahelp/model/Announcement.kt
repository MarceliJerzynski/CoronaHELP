package com.example.coronahelp.model

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

class Announcement(
    val title: String,
    val description: String?,
    val category: Category,
    val location: LatLng,
    val time: LocalDateTime
)