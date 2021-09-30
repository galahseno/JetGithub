package dev.seno.jetgithub.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dev.seno.jetgithub.utils.Constants.SETTING_KEY_PREF
import dev.seno.jetgithub.utils.Constants.SETTING_PREF
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTING_PREF)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    private object PreferenceKeys {
        val darkModeKey = booleanPreferencesKey(name = SETTING_KEY_PREF)
    }

    private val dataStore = context.dataStore

    suspend fun persistDarkModeState(value: Boolean) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.darkModeKey] = value
        }
    }

    val readState: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val darkModeState = preferences[PreferenceKeys.darkModeKey] ?: false
            darkModeState
        }
}