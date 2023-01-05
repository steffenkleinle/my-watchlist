package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.mywatchlist.data.models.Watchable
import app.mywatchlist.data.repositories.WatchablesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 20

@HiltViewModel
class WatchablesViewModel @Inject constructor(
    private val repository: WatchablesRepository
) : ViewModel() {
    val items: Flow<PagingData<Watchable>> = Pager(
        // WARNING: pageSize is not used
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = { repository.watchablesPagingSource() }
    )
        .flow
        .cachedIn(viewModelScope)
//
//    var uiState = repository.trendingFlow().asStateFlow(viewModelScope)
//    private val _queryUiState = MutableStateFlow<String?>(null)
//    val queryUiState = _queryUiState.asStateFlow()
//
//    fun update(query: String?) {
//        val normalized = query.normalize()
//        val previous = this.queryUiState.value
//        val previousNormalized = previous.normalize()
//
//        if (query != previous) {
//            _queryUiState.update { query }
//        }
//
//        if (normalized != previousNormalized) {
//            uiState = (
//                    if (normalized == null) repository.trendingFlow()
//                    else repository.searchFlow(normalized)
//                    ).asStateFlow(viewModelScope)
//        }
//    }
//
//    private fun String?.normalize() = this?.trim()?.ifEmpty { null }
}