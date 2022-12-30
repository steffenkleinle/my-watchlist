package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.models.Watchable
import app.mywatchlist.data.repositories.WatchablesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: WatchablesRepository
) : ViewModel() {
    private var query: String? = null
    var uiState = initialStateFlow<List<Watchable>>(viewModelScope)

    fun setQuery(query: String) {
        if (this.query != query) {
            this.query = query
            uiState = repository.searchFlow(query).asStateFlow(viewModelScope)
        }
    }
}