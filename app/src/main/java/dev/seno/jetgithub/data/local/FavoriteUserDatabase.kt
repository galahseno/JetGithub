package dev.seno.jetgithub.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.seno.jetgithub.data.model.FavoriteUser

@Database(version = 1, entities = [FavoriteUser::class], exportSchema = false)
abstract class FavoriteUserDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao
}