package dev.seno.jetgithub.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seno.jetgithub.data.DataStoreRepository
import dev.seno.jetgithub.data.MainRepository
import dev.seno.jetgithub.data.model.ListUser
import dev.seno.jetgithub.utils.Resource
import dev.seno.jetgithub.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _listUser = MutableStateFlow<Resource<ListUser>>(Resource.Idle)
    val listUser: StateFlow<Resource<ListUser>>
        get() = _listUser

    val darkModeState = dataStoreRepository.readState.asLiveData()

    fun onValueChanged(value: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistDarkModeState(value)
        }
    }

    fun searchUser(query: String) {
        _listUser.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            val listUser = mainRepository.searchUser(query)
            _listUser.value = when (listUser) {
                is Response.Success -> Resource.Success(listUser.data)
                is Response.Error -> Resource.Error(listUser.errorMessage)
            }
        }
    }

    fun backToIdle() {
        _listUser.value = Resource.Idle
    }
}