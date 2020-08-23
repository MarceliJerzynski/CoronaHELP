package com.example.coronahelp.model

import com.google.gson.annotations.SerializedName

class ResponseAnnouncementsModel(
    @SerializedName("announcements")
    var announcements: List<AnnouncementResponse>
)