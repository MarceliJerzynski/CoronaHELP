package com.example.coronahelp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coronahelp.model.Announcement
import com.example.coronahelp.repository.AnnouncementsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnnouncementViewModel : ViewModel() {

    val announcement: MutableLiveData<Announcement> = MutableLiveData()

    val repository = AnnouncementsRepository()

    fun getAnnouncement(id: Int) = viewModelScope.launch (Dispatchers.IO) {
        announcement.postValue(repository.getAnnouncement(id) )
    }

}