package app.mywatchlist.data.sources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import app.mywatchlist.data.PreferencesDataStoreKeys
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

open class IntListLocalDataSource(
    private val dataStore: DataStore<Preferences>,
    private val key: Preferences.Key<Set<String>>
) {
    val flow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[key]?.map { it.toInt() } ?: listOf()
        }

    suspend fun add(id: Int) {
        flow.collect {
            dataStore.edit { preferences ->
                preferences[key] =
                    (it + listOf(id)).map { it.toString() }.toSet()
            }
        }
    }

    suspend fun remove(id: Int) {
        flow.collect {
            dataStore.edit { preferences ->
                preferences[key] =
                    it.filter { it != id }.map { it.toString() }.toSet()
            }
        }
    }
}

class FavoritesLocalDataSource @Inject constructor(dataStore: DataStore<Preferences>) :
    IntListLocalDataSource(dataStore, PreferencesDataStoreKeys.FAVORITES)

class WatchedLocalDataSource @Inject constructor(dataStore: DataStore<Preferences>) :
    IntListLocalDataSource(dataStore, PreferencesDataStoreKeys.WATCHED)
