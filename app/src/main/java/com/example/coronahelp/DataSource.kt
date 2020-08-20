package com.example.coronahelp

import com.example.coronahelp.model.Announcement
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class DataSource {
    companion object{
        fun createDataSet(): ArrayList<Announcement>{
            val list = ArrayList<Announcement>()
            list.add(
                Announcement(
                    "Potrzeba sanitarna",
                    "Potrzeba na szybko 10 rolek papieru toaletowego!!",
                    LatLng(52.0,52.09),
                    LocalDateTime.now()
                )
            )
            list.add(
                Announcement(
                    "Potrzeba sanitarna",
                    "Potrzebuję tabletek przeciwbólowych Nurofen",
                    LatLng(52.0,52.09),
                    LocalDateTime.now()
                )
            )
            list.add(
                Announcement(
                    "Potrzeba żywieniowa",
                    "Hej, potrzebuję makaronu i ryżu",
                    LatLng(52.0,52.09),
                    LocalDateTime.now()
                )
            )
            list.add(
                Announcement(
                    "Potrzeba psychiczna",
                    "Czuję się zaniepokojony obecną sytuacją, potrzebuję kontaktu z psychologiem",
                    LatLng(52.0,52.09),
                    LocalDateTime.now()
                )
            )
            return list
        }
    }
}