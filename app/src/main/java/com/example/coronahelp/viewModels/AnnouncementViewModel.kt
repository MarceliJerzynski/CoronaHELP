package com.example.coronahelp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coronahelp.model.Announcement
import com.example.coronahelp.repository.AnnouncementsRepository
import com.example.coronahelp.rest.RestCaller
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnnouncementViewModel : ViewModel() {

    val announcement: MutableLiveData<Announcement> = MutableLiveData()

    val success: MutableLiveData<Boolean> = MutableLiveData()

    val repository = AnnouncementsRepository()

    fun getAnnouncement(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        announcement.postValue(repository.getAnnouncement(id))
    }

    fun takeTask(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        success.postValue(RestCaller.registerForAnnouncement(id))
    }

    fun deleteTask(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        success.postValue(RestCaller.deleteAnnouncements(id))
    }

    fun confirmAnnouncement(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        success.postValue(RestCaller.confirmAnnouncement(id))
    }

}