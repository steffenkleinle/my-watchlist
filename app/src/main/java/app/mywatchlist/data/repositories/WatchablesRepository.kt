package app.mywatchlist.data.repositories

import app.mywatchlist.data.models.Provider
import app.mywatchlist.data.models.Providers
import app.mywatchlist.data.models.RawWatchable
import app.mywatchlist.data.models.Watchable
import app.mywatchlist.data.sources.TmdbRemoteDateSource

private const val REGION = "US"
private const val LANGUAGE = "en-$REGION"

class WatchablesRepository(
    private val tmdbRemoteDateSource: TmdbRemoteDateSource
) {
    suspend fun getTrending(language: String = LANGUAGE): List<Watchable> {
        val response = tmdbRemoteDateSource.getTrending(language)
        return response.body()?.results?.map { it.addProviders() }
            ?: throw Error(response.message())
    }

    suspend fun getDetails(id: Int, language: String = LANGUAGE): Watchable {
        val response = tmdbRemoteDateSource.getDetails(id, language)
        return response.body()?.addProviders()
            ?: throw Error(response.message())
    }

    suspend fun search(
        query: String,
        language: String = LANGUAGE,
        region: String = REGION
    ): List<Watchable> {
        val response = tmdbRemoteDateSource.search(query, language, region)
        return response.body()?.results?.map { it.addProviders() }
            ?: throw Error(response.message())
    }

    suspend fun getProviders(watchableId: Int): Map<String, Providers> {
        val response = tmdbRemoteDateSource.getProviders(watchableId)
        return response.body()?.results
            ?: throw Error(response.message())
    }

    suspend fun getProviders(
        language: String = LANGUAGE,
        region: String = REGION
    ): List<Provider> {
        val response = tmdbRemoteDateSource.getProviders(language, region)
        return response.body()?.results
            ?: throw Error(response.message())
    }

    private suspend fun RawWatchable.addProviders(): Watchable =
        Watchable(this, getProviders(this.id))
}
