package com.example.coronahelp.model

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AnnouncementResponse(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val reward: Double,
    val lat: String,
    val long: String,
    val deadline: String,

    val owner: User?,
    val performer: User?
) {
    override fun toString(): String {
        return "\nid: $id\ntitle: $title\ndescription: $description\ncategory: $category\nreward: $reward\nlat: $lat\nlong: $long\ndeadline: $deadline\n"
    }

    fun toAnnouncement(): Announcement {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        return Announcement(
            id,
            title,
            description,
            category,
            reward,
            LatLng(lat.toDouble(), long.toDouble()),
            LocalDateTime.parse(deadline, formatter),
            owner,
            performer
        )
    }
}