package app.mywatchlist.data.repositories

import app.mywatchlist.data.sources.TmdbRemoteDateSource
import app.mywatchlist.data.sources.TmdbRemotePagingDataSource
import javax.inject.Inject

class WatchablesRepository @Inject constructor(
    private val tmdbRemoteDataSource: TmdbRemoteDateSource
) {
    fun watchablesPagingSource() = TmdbRemotePagingDataSource(tmdbRemoteDataSource)
}
