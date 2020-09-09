package com.example.coronahelp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coronahelp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    val success: MutableLiveData<Boolean> = MutableLiveData(false)

    val repository = UserRepository()

    fun register(name: String, email: String, password: String, passwordConfirmation: String) =
        viewModelScope.launch(Dispatchers.IO) {
            success.postValue(repository.register(name, email, password, passwordConfirmation))
        }

}
