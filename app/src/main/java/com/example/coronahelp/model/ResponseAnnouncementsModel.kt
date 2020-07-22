package com.example.coronahelp.model

import com.google.gson.annotations.SerializedName

class ResponseAnnouncementsModel(
    @SerializedName("announcement")
    var announcements: List<Announcement>
)