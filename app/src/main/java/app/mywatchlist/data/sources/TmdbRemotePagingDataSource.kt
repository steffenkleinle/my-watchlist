package app.mywatchlist.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.mywatchlist.data.models.Watchable
import javax.inject.Inject

private const val FIRST_PAGE = 1

class TmdbRemotePagingDataSource @Inject constructor(
    private val tmdbRemoteDateSource: TmdbRemoteDateSource,
    private val query: String?
) :
    PagingSource<Int, Watchable>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Watchable> {
        return try {
            val page = params.key ?: FIRST_PAGE
            return LoadResult.Page(
                data = if (query.isNullOrEmpty()) tmdbRemoteDateSource.getTrending(page) else tmdbRemoteDateSource.search(
                    query,
                    page
                ),
                prevKey = if (page == FIRST_PAGE) null else page - 1,
                nextKey = page + 1
            )
        } catch (e: java.lang.Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Watchable>): Int = FIRST_PAGE
}
