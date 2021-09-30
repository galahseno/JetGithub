package dev.seno.jetgithub.data

import dev.seno.jetgithub.data.model.DetailUser
import dev.seno.jetgithub.data.model.FavoriteUser
import dev.seno.jetgithub.data.model.ListUser
import dev.seno.jetgithub.data.model.User
import dev.seno.jetgithub.utils.Response
import kotlinx.coroutines.flow.Flow

interface MainRepositoryImpl {

    suspend fun searchUser(query: String): Response<ListUser>
    suspend fun getDetailUser(username: String): Response<DetailUser>
    suspend fun getFollowing(username: String): Response<List<User>>
    suspend fun getFollower(username: String): Response<List<User>>

    suspend fun getFavoriteList() : Flow<List<FavoriteUser>>
    fun checkFavorite(username: String): Boolean
    suspend fun deleteFavoriteById(id: Int)
    suspend fun deleteFavoriteByUsername(username: String)
    suspend fun insertFavoriteUser(userFavorite: FavoriteUser)
    suspend fun deleteAllFavorite()
}