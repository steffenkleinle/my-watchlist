package app.mywatchlist.data.repositories

import app.mywatchlist.data.models.Watchable
import app.mywatchlist.data.sources.FavoritesLocalDataSource
import app.mywatchlist.data.sources.TmdbRemoteDateSource
import app.mywatchlist.data.sources.TmdbRemotePagingDataSource
import app.mywatchlist.data.sources.WatchedLocalDataSource
import javax.inject.Inject

class WatchablesRepository @Inject constructor(
    val favorites: FavoritesLocalDataSource,
    val watched: WatchedLocalDataSource,
    private val tmdbRemoteDataSource: TmdbRemoteDateSource
) {
    fun watchablesPagingSource(query: String?) =
        TmdbRemotePagingDataSource(tmdbRemoteDataSource, query)

    suspend fun getFavorites(): List<Watchable> = tmdbRemoteDataSource.getFavorites()
    suspend fun getDetails(id: Int): Watchable = tmdbRemoteDataSource.getDetail(id)
}
