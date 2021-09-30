package dev.seno.jetgithub.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seno.jetgithub.data.MainRepository
import dev.seno.jetgithub.data.model.DetailUser
import dev.seno.jetgithub.data.model.User
import dev.seno.jetgithub.utils.Resource
import dev.seno.jetgithub.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _detailUser = MutableStateFlow<Resource<DetailUser>>(Resource.Loading)
    val detailUser: StateFlow<Resource<DetailUser>>
        get() = _detailUser

    private val _listFollowingUser = MutableStateFlow<Resource<List<User>>>(Resource.Loading)
    val listFollowingUser: StateFlow<Resource<List<User>>>
        get() = _listFollowingUser

    private val _listFollowerUser = MutableStateFlow<Resource<List<User>>>(Resource.Loading)
    val listFollowerUser: StateFlow<Resource<List<User>>>
        get() = _listFollowerUser

    private val detailUserState = MutableStateFlow<List<DetailUser>>(listOf())

    fun checkFavoriteState(username: String): Boolean {
        return mainRepository.checkFavorite(username)
    }

    fun handleInsertOrDeleteFavorite(state: Boolean) {
        if (state) insertFavorite(detailUserState.value.first())
        else deleteFavorite(detailUserState.value.first().username)
    }

    private fun insertFavorite(detailUser: DetailUser) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.insertFavoriteUser(detailUser.toFavoriteUser())
        }
    }

    private fun deleteFavorite(username: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (username != null) {
                mainRepository.deleteFavoriteByUsername(username)
            }
        }
    }

    fun getDetailUser(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val detailUser = mainRepository.getDetailUser(username)) {
                is Response.Success -> {
                    _detailUser.value = Resource.Success(detailUser.data)
                    detailUserState.value = listOf(detailUser.data)
                }
                is Response.Error -> _detailUser.value = Resource.Error(detailUser.errorMessage)
            }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val followingUser = mainRepository.getFollowing(username)
            _listFollowingUser.value = when (followingUser) {
                is Response.Success -> Resource.Success(followingUser.data)
                is Response.Error -> Resource.Error(followingUser.errorMessage)
            }
        }
    }

    fun getFollower(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val followerUser = mainRepository.getFollower(username)
            _listFollowerUser.value = when (followerUser) {
                is Response.Success -> Resource.Success(followerUser.data)
                is Response.Error -> Resource.Error(followerUser.errorMessage)
            }
        }
    }
}