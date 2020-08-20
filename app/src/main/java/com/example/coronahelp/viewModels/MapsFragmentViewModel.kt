package com.example.coronahelp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coronahelp.model.Announcement
import com.example.coronahelp.repository.AnnouncementsRepository

class MapsFragmentViewModel : ViewModel() {

    var repo = AnnouncementsRepository()

    var announcements : MutableLiveData<List<Announcement>> = MutableLiveData()

    init{
        announcements.value = repo.getAnnouncementsFromREST()
    }
}