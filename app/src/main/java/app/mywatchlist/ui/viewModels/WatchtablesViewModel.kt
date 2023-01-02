package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.repositories.WatchablesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchablesViewModel @Inject constructor(
    private val repository: WatchablesRepository
) : ViewModel() {
    var uiState = repository.trendingFlow().asStateFlow(viewModelScope)
    private var query: String? = null

    fun update(query: String?) {
        val normalized = query.normalize()
        if (this.query != normalized) {
            uiState = (
                    if (normalized == null) repository.trendingFlow()
                    else repository.searchFlow(normalized)
                    ).asStateFlow(viewModelScope)

        }

        this.query = normalized
    }

    private fun String?.normalize() = this?.trim()?.ifEmpty { null }
}