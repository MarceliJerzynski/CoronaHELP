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
                    1,
                    "Potrzeba sanitarna",
                    "Potrzeba na szybko 10 rolek papieru toaletowego!!",
                    Category.TOILET_PAPER,
                    30.0,
                    LatLng(52.0,52.09),
                    LocalDateTime.now()
                )
            )
            list.add(
                Announcement(
                    2,
                    "Potrzeba sanitarna",
                    "Potrzebuję tabletek przeciwbólowych Nurofen",
                    Category.SHOPPING,
                    30.0,
                    LatLng(52.0,52.09),
                    LocalDateTime.now()
                )
            )
            list.add(
                Announcement(
                    3,
                    "Potrzeba żywieniowa",
                    "Hej, potrzebuję makaronu i ryżu",
                    Category.SHOPPING,
                    30.0,
                    LatLng(52.0,52.09),
                    LocalDateTime.now()
                )
            )
            list.add(
                Announcement(
                    4,
                    "Potrzeba psychiczna",
                    "Czuję się zaniepokojony obecną sytuacją, potrzebuję kontaktu z psychologiem",
                    Category.TOILET_PAPER,
                    30.0,
                    LatLng(52.0,52.09),
                    LocalDateTime.now()
                )
            )
            return list
        }
    }
}