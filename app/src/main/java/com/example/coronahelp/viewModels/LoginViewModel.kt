package com.example.coronahelp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coronahelp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val success: MutableLiveData<Boolean> = MutableLiveData(false)

    val repository = UserRepository()

    fun login(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        success.postValue(repository.login(email, password))
    }
}
