package app.mywatchlist.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import app.mywatchlist.data.models.Settings
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val DEFAULT_REGION = "US"
private const val DEFAULT_LANGUAGE = "en-$DEFAULT_REGION"

private const val LANGUAGE_KEY = "language"
private const val REGION_KEY = "region"
private const val PROVIDERS_KEY = "providers"

private object SettingsKeys {
    val LANGUAGE = stringPreferencesKey(LANGUAGE_KEY)
    val REGION = stringPreferencesKey(REGION_KEY)
    val PROVIDERS = stringSetPreferencesKey(PROVIDERS_KEY)
}

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
            val language = preferences[SettingsKeys.LANGUAGE] ?: DEFAULT_LANGUAGE
            val region = preferences[SettingsKeys.REGION] ?: DEFAULT_REGION
            val providers = preferences[SettingsKeys.PROVIDERS]?.map { it.toInt() } ?: listOf()
            Settings(language = language, region = region, providers = providers)
        }

    suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[SettingsKeys.LANGUAGE] = language
        }
    }

    suspend fun setRegion(region: String) {
        dataStore.edit { preferences ->
            preferences[SettingsKeys.REGION] = region
        }
    }

    suspend fun setProviders(providers: List<Int>) {
        dataStore.edit { preferences ->
            preferences[SettingsKeys.PROVIDERS] = providers.map { it.toString() }.toSet()
        }
    }
}
