package dev.seno.jetgithub.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seno.jetgithub.data.MainRepository
import dev.seno.jetgithub.data.model.FavoriteUser
import dev.seno.jetgithub.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _favoriteListUser = MutableStateFlow<Resource<List<FavoriteUser>>>(Resource.Loading)
    val favoriteListUser: StateFlow<Resource<List<FavoriteUser>>>
        get() = _favoriteListUser

    fun insertFavorite(favoriteUser: FavoriteUser) {
        viewModelScope.launch(Dispatchers.IO) {
            val newFavorite = FavoriteUser(
                name = favoriteUser.name,
                userName = favoriteUser.userName,
                userImg = favoriteUser.userImg,
                location = favoriteUser.location,
                company = favoriteUser.company,
            )
            mainRepository.insertFavoriteUser(newFavorite)
        }
    }

    fun getFavoriteListUser() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.getFavoriteList().collect {
                _favoriteListUser.value = Resource.Success(it)
            }
        }
    }

    fun deleteFavorite(id: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id != null) {
                delay(300)
                mainRepository.deleteFavoriteById(id)
            }
        }
    }

    fun deleteAllFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.deleteAllFavorite()
        }
    }
}