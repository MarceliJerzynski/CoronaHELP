package com.example.coronahelp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coronahelp.model.Announcement
import com.example.coronahelp.repository.AnnouncementsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsFragmentViewModel : ViewModel() {

    var repo = AnnouncementsRepository()

    var announcements: MutableLiveData<List<Announcement>> = MutableLiveData()

    init {
        refreshAnnouncements()
    }

    fun refreshAnnouncements() = viewModelScope.launch(Dispatchers.IO) {
        announcements.postValue(repo.getAnnouncementsFromREST())
    }
}