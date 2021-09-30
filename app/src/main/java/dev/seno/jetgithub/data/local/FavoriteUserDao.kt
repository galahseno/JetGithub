package dev.seno.jetgithub.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.seno.jetgithub.data.model.FavoriteUser
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteUserDao {
    @Query("SELECT * from FAVORITE_USER")
    fun getFavoriteList(): Flow<List<FavoriteUser>>

    @Query("SELECT EXISTS(SELECT * from FAVORITE_USER WHERE userName =:userName)")
    fun checkFavorite(userName: String): Boolean

    @Query("DELETE from FAVORITE_USER WHERE id =:id")
    suspend fun deleteFavoriteById(id: Int)

    @Query("DELETE from FAVORITE_USER WHERE userName =:username")
    suspend fun deleteFavoriteByUsername(username: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(userFavorite: FavoriteUser)

    @Query("DELETE from FAVORITE_USER")
    suspend fun deleteAllFavorite()
}