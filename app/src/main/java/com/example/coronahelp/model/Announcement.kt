package com.example.coronahelp.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDateTime

data class Announcement(
    var id: Int,
    var title: String,
    var description: String,
    var category: String,
    var reward: Double,
    var location: LatLng,
    var time: LocalDateTime,
    var owner: User? = null,
    var performer: User? = null
)