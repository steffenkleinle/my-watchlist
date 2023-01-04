package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.repositories.WatchablesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WatchablesViewModel @Inject constructor(
    private val repository: WatchablesRepository
) : ViewModel() {
    var uiState = repository.trendingFlow().asStateFlow(viewModelScope)
    private val _queryUiState = MutableStateFlow<String?>(null)
    val queryUiState = _queryUiState.asStateFlow()

    fun update(query: String?) {
        val normalized = query.normalize()
        val previous = this.queryUiState.value
        val previousNormalized = previous.normalize()

        if (query != previous) {
            _queryUiState.update { query }
        }

        if (normalized != previousNormalized) {
            uiState = (
                    if (normalized == null) repository.trendingFlow()
                    else repository.searchFlow(normalized)
                    ).asStateFlow(viewModelScope)
        }
    }

    private fun String?.normalize() = this?.trim()?.ifEmpty { null }
}