package com.example.coronahelp.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Announcement(
    val id: Int,
    val title: String,
    val description: String,
    val category: Category,
    val reward: Double,
    val location: LatLng,
    val time: LocalDateTime
) : Parcelable