package app.mywatchlist.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val FAVORITES_KEY = "favorites"
private const val WATCHED_KEY = "watched"

private object StringListKeys {
    val FAVORITES = stringSetPreferencesKey(FAVORITES_KEY)
    val WATCHED = stringSetPreferencesKey(WATCHED_KEY)
}

open class IntListRepository(
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

class FavoritesRepository @Inject constructor(dataStore: DataStore<Preferences>) :
    IntListRepository(dataStore, StringListKeys.FAVORITES)

class WatchedRepository @Inject constructor(dataStore: DataStore<Preferences>) :
    IntListRepository(dataStore, StringListKeys.WATCHED)
