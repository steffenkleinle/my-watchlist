package app.mywatchlist.data.repositories

import app.mywatchlist.data.models.RawWatchable
import app.mywatchlist.data.sources.TmdbRemoteDateSource
import app.mywatchlist.data.sources.TmdbRemotePagingDataSource
import javax.inject.Inject

class WatchablesRepository @Inject constructor(
    private val tmdbRemoteDataSource: TmdbRemoteDateSource
) {
    fun watchablesPagingSource(query: String?) =
        TmdbRemotePagingDataSource(tmdbRemoteDataSource, query)

    suspend fun getDetails(id: Int): RawWatchable = tmdbRemoteDataSource.getDetail(id)
}
