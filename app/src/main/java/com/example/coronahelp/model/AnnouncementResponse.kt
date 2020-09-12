package com.example.coronahelp.model

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

class AnnouncementResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("category")
    val category: Category,
    @SerializedName("reward")
    val reward: Double,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("long")
    val long: String,
    @SerializedName("deadline")
    val time: String
) {
    override fun toString(): String {
        return "\nid: $id\ntitle: $title\ndescription: $description\ncategory: $category\nreward: $reward\nlat: $lat\nlong: $long\ntime: $time\n"
    }

    fun toAnnouncement(): Announcement {
        return Announcement(
            id,
            title,
            description,
            category,
            reward,
            LatLng(lat.toDouble(), long.toDouble()),
            LocalDateTime.now() //TODO <- change THIS!!!!!!!
        )
    }
}