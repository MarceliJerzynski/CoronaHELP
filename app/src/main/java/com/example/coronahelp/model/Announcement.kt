package com.example.coronahelp.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDateTime

data class Announcement(
    val id: Int,
    val title: String,
    val description: String,
    val category: Category,
    val reward: Double,
    val location: LatLng,
    val time: LocalDateTime
)