package app.mywatchlist.ui.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import app.mywatchlist.data.models.Watchable
import app.mywatchlist.data.repositories.FavoritesRepository
import app.mywatchlist.data.repositories.WatchablesRepository
import app.mywatchlist.data.repositories.WatchedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private const val PAGE_SIZE = 20
private const val QUERY_KEY = "query"

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class WatchablesViewModel @Inject constructor(
    private val repository: WatchablesRepository,
    private val favoritesRepository: FavoritesRepository,
    private val watchedRepository: WatchedRepository,
    private val state: SavedStateHandle
) : ViewModel() {
    private val _queryUiState = MutableStateFlow<String?>(state[QUERY_KEY])
    val queryUiState = _queryUiState.asStateFlow()

    val items = _queryUiState.flatMapLatest { query ->
        Pager(
            // WARNING: pageSize is not used
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { repository.watchablesPagingSource(query.normalize()) }
        )
            .flow.cachedIn(viewModelScope)
            .combine(favoritesRepository.flow) { items, favorites ->
                items.map { Watchable(it, favorites.contains(it.id), false) }
            }.combine(watchedRepository.flow) { items, watched ->
                items.map { it.copy(watched = watched.contains(it.id)) }
            }
    }.cachedIn(viewModelScope)

    fun update(query: String?) {
        state[QUERY_KEY] = query
        val previous = this.queryUiState.value

        if (query != previous) {
            _queryUiState.update { query }
        }
    }

    private fun String?.normalize() = this?.trim()?.ifEmpty { null }
}