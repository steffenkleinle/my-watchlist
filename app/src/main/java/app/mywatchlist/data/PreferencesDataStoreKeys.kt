package app.mywatchlist.data

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

private const val LANGUAGE_KEY = "language"
private const val REGION_KEY = "region"
private const val PROVIDERS_KEY = "providers"
private const val FAVORITES_KEY = "favorites"
private const val WATCHED_KEY = "watched"

object PreferencesDataStoreKeys {
    val LANGUAGE = stringPreferencesKey(LANGUAGE_KEY)
    val REGION = stringPreferencesKey(REGION_KEY)
    val PROVIDERS = stringSetPreferencesKey(PROVIDERS_KEY)
    val FAVORITES = stringSetPreferencesKey(FAVORITES_KEY)
    val WATCHED = stringSetPreferencesKey(WATCHED_KEY)
}
