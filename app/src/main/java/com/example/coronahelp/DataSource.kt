package com.example.coronahelp

import com.example.coronahelp.model.Announcement

class DataSource {
    companion object{
        fun createDataSet(): ArrayList<Announcement>{
            val list = ArrayList<Announcement>()
            list.add(
                Announcement(
                    "Potrzeba sanitarna",
                    "Potrzeba na szybko 10 rolek papieru toaletowego!!",
                    "Poznań, Mostowa 40",
                    "18:00, 17.08.2020"
                )
            )
            list.add(
                Announcement(
                    "Potrzeba sanitarna",
                    "Potrzebuję tabletek przeciwbólowych Nurofen",
                    "Poznań, Dominikańska 10",
                    "18:30, 17.08.2020"
                )
            )
            list.add(
                Announcement(
                    "Potrzeba żywieniowa",
                    "Hej, potrzebuję makaronu i ryżu",
                    "Poznań, Garbary 70",
                    "16:00, 17.08.2020"
                )
            )
            list.add(
                Announcement(
                    "Potrzeba psychiczna",
                    "Czuję się zaniepokojony obecną sytuacją, potrzebuję kontaktu z psychologiem",
                    "Poznań, Wierzbowa 5",
                    "12:22, 17.08.2020"
                )
            )
            return list
        }
    }
}