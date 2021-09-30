package dev.seno.jetgithub.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.seno.jetgithub.data.local.FavoriteUserDao
import dev.seno.jetgithub.data.local.FavoriteUserDatabase
import dev.seno.jetgithub.utils.Constants.FAVORITE_DB_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FavoriteUserDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FavoriteUserDatabase::class.java,
            FAVORITE_DB_NAME
        ).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(favoriteUserDatabase: FavoriteUserDatabase): FavoriteUserDao {
        return favoriteUserDatabase.favoriteUserDao()
    }
}