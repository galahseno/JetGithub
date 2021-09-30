package dev.seno.jetgithub.data

import dagger.hilt.android.scopes.ViewModelScoped
import dev.seno.jetgithub.data.api.ApiService
import dev.seno.jetgithub.data.local.FavoriteUserDao
import dev.seno.jetgithub.data.model.DetailUser
import dev.seno.jetgithub.data.model.FavoriteUser
import dev.seno.jetgithub.data.model.ListUser
import dev.seno.jetgithub.data.model.User
import dev.seno.jetgithub.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao
) : MainRepositoryImpl {

    override suspend fun searchUser(query: String): Response<ListUser> {
        return try {
            val data = apiService.getListUsers(query)
            Response.Success(data)
        } catch (e: Exception) {
            Response.Error(e.message.toString())
        }
    }

    override suspend fun getDetailUser(username: String): Response<DetailUser> {
        return try {
            val data = apiService.getDetailUsers(username)
            Response.Success(data)
        } catch (e: Exception) {
            Response.Error(e.message.toString())
        }
    }

    override suspend fun getFollowing(username: String): Response<List<User>> {
        return try {
            val data = apiService.getListFollowing(username)
            Response.Success(data)
        } catch (e: Exception) {
            Response.Error(e.message.toString())
        }
    }

    override suspend fun getFollower(username: String): Response<List<User>> {
        return try {
            val data = apiService.getListFollower(username)
            Response.Success(data)
        } catch (e: Exception) {
            Response.Error(e.message.toString())
        }
    }

    override suspend fun getFavoriteList(): Flow<List<FavoriteUser>> {
        return favoriteUserDao.getFavoriteList()
    }

    override fun checkFavorite(username: String): Boolean {
        return favoriteUserDao.checkFavorite(username)

    }

    override suspend fun deleteFavoriteById(id: Int) {
        favoriteUserDao.deleteFavoriteById(id)
    }

    override suspend fun deleteFavoriteByUsername(username: String) {
        favoriteUserDao.deleteFavoriteByUsername(username)
    }

    override suspend fun insertFavoriteUser(userFavorite: FavoriteUser) {
        favoriteUserDao.insertFavoriteUser(userFavorite)
    }

    override suspend fun deleteAllFavorite() {
        favoriteUserDao.deleteAllFavorite()
    }
}