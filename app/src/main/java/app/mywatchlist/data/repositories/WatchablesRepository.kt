package app.mywatchlist.data.repositories

import app.mywatchlist.data.models.Provider
import app.mywatchlist.data.models.Providers
import app.mywatchlist.data.models.RawWatchable
import app.mywatchlist.data.models.Watchable
import app.mywatchlist.data.sources.TmdbRemoteDateSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

private const val REGION = "US"
private const val LANGUAGE = "en-$REGION"

class WatchablesRepository @Inject constructor(
    private val tmdbRemoteDateSource: TmdbRemoteDateSource,
    private val favoritesRepository: FavoritesRepository,
    private val watchedRepository: WatchedRepository
) {
    val favorites = favoritesRepository
    val watched = watchedRepository

    fun trendingFlow(language: String = LANGUAGE): Flow<List<Watchable>> =
        combine(favoritesRepository.flow, watchedRepository.flow) { favorites, watched ->
            getTrending(language).map {
                Watchable(
                    it,
                    getProviders(it.id),
                    favorites.contains(it.id),
                    watched.contains(it.id)
                )
            }
        }

    fun favoritesFlow(language: String = LANGUAGE): Flow<List<Watchable>> =
        combine(favoritesRepository.flow, watchedRepository.flow) { favorites, watched ->
            favorites.map {
                Watchable(
                    getDetails(it, language),
                    getProviders(it),
                    favorites.contains(it),
                    watched.contains(it)
                )
            }
        }

    fun detailFlow(id: Int, language: String = LANGUAGE): Flow<Watchable> =
        combine(favoritesRepository.flow, watchedRepository.flow) { favorites, watched ->
            Watchable(
                getDetails(id, language),
                getProviders(id),
                favorites.contains(id),
                watched.contains(id)
            )
        }

    fun searchFlow(
        query: String,
        language: String = LANGUAGE,
        region: String = REGION
    ): Flow<List<Watchable>> =
        combine(favoritesRepository.flow, watchedRepository.flow) { favorites, watched ->
            search(query, language, region).map {
                Watchable(
                    it,
                    getProviders(it.id),
                    favorites.contains(it.id),
                    watched.contains(it.id)
                )
            }
        }

    private suspend fun getTrending(language: String = LANGUAGE): List<RawWatchable> {
        val response = tmdbRemoteDateSource.getTrending(language)
        return response.body()?.results
            ?: throw Error(response.message())
    }

    private suspend fun getDetails(id: Int, language: String = LANGUAGE): RawWatchable {
        val response = tmdbRemoteDateSource.getDetails(id, language)
        return response.body() ?: throw Error(response.message())
    }

    private suspend fun search(
        query: String,
        language: String = LANGUAGE,
        region: String = REGION
    ): List<RawWatchable> {
        val response = tmdbRemoteDateSource.search(query, language, region)
        return response.body()?.results
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
}
