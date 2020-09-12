package com.example.coronahelp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coronahelp.model.Announcement
import com.example.coronahelp.repository.AnnouncementsRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class CreateAnnouncementViewModel : ViewModel() {

    val success: MutableLiveData<Boolean> = MutableLiveData()

    val repository = AnnouncementsRepository()

    val announcement by lazy {
        Announcement(-1, "", "", "", 5.0, LatLng(0.0, 0.0), LocalDateTime.now())
    }

    fun createAnnouncement() = viewModelScope.launch(Dispatchers.IO) {
        success.postValue(repository.createAnnouncement(announcement))
    }
}
