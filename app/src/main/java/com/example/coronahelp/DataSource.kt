package com.example.coronahelp

import com.example.coronahelp.model.Announcement
import com.example.coronahelp.model.Category
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
                    Category.TOILET_PAPER,
                    LatLng(52.0,52.09),
                    LocalDateTime.now()
                )
            )
            list.add(
                Announcement(
                    "Potrzeba sanitarna",
                    "Potrzebuję tabletek przeciwbólowych Nurofen",
                    Category.SHOPPING,
                    LatLng(52.0,52.09),
                    LocalDateTime.now()
                )
            )
            list.add(
                Announcement(
                    "Potrzeba żywieniowa",
                    "Hej, potrzebuję makaronu i ryżu",
                    Category.SHOPPING,
                    LatLng(52.0,52.09),
                    LocalDateTime.now()
                )
            )
            list.add(
                Announcement(
                    "Potrzeba psychiczna",
                    "Czuję się zaniepokojony obecną sytuacją, potrzebuję kontaktu z psychologiem",
                    Category.TOILET_PAPER,
                    LatLng(52.0,52.09),
                    LocalDateTime.now()
                )
            )
            return list
        }
    }
}