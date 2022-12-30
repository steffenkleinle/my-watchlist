package app.mywatchlist.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import app.mywatchlist.data.PreferencesDataStoreKeys
import app.mywatchlist.data.models.Settings
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val DEFAULT_REGION = "US"
private const val DEFAULT_LANGUAGE = "en-$DEFAULT_REGION"

class SettingsRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {
    val settingsFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val language = preferences[PreferencesDataStoreKeys.LANGUAGE] ?: DEFAULT_LANGUAGE
            val region = preferences[PreferencesDataStoreKeys.REGION] ?: DEFAULT_REGION
            val providers = preferences[PreferencesDataStoreKeys.PROVIDERS]?.map { it.toInt() }
                ?: listOf()
            Settings(language = language, region = region, providers = providers)
        }

    suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesDataStoreKeys.LANGUAGE] = language
        }
    }

    suspend fun setRegion(region: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesDataStoreKeys.REGION] = region
        }
    }

    suspend fun setProviders(providers: List<Int>) {
        dataStore.edit { preferences ->
            preferences[PreferencesDataStoreKeys.PROVIDERS] =
                providers.map { it.toString() }.toSet()
        }
    }
}
