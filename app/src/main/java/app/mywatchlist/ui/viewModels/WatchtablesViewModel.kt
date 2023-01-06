package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import app.mywatchlist.data.repositories.WatchablesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val PAGE_SIZE = 20

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class WatchablesViewModel @Inject constructor(
    private val repository: WatchablesRepository
) : ViewModel() {
    private val _queryUiState = MutableStateFlow<String?>(null)
    val queryUiState = _queryUiState.asStateFlow()
    val items = _queryUiState.flatMapLatest { query ->
        Pager(
            // WARNING: pageSize is not used
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { repository.watchablesPagingSource(query.normalize()) }
        ).flow
    }.cachedIn(viewModelScope)

    fun update(query: String?) {
        val previous = this.queryUiState.value

        if (query != previous) {
            _queryUiState.update { query }
        }
    }

    private fun String?.normalize() = this?.trim()?.ifEmpty { null }
}