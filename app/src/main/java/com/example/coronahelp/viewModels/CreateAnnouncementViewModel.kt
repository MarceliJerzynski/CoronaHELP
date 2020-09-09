package com.example.coronahelp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coronahelp.model.Category
import com.example.coronahelp.repository.AnnouncementsRepository
import com.example.coronahelp.repository.UserRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class CreateAnnouncementViewModel : ViewModel() {

    val success: MutableLiveData<Boolean> = MutableLiveData(false)

    val repository = AnnouncementsRepository()

    fun createAnnouncement(title: String,
                           description: String,
                           category: Category,
                           reward: Double,
                           location: LatLng,
                           time: LocalDateTime
    ) = viewModelScope.launch(Dispatchers.IO) {
        success.postValue(repository.createAnnouncement(
            title,
            description,
            category,
            reward,
            location,
            time))
    }
}
