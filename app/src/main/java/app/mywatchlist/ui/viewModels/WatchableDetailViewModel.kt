package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.models.Watchable
import app.mywatchlist.data.repositories.WatchablesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WatchableDetailViewModel @Inject constructor(
    private val repository: WatchablesRepository
) : ViewModel() {
    private var id: Int? = null
    var uiState: StateFlow<ResultUiState<Watchable>> = initialStateFlow(viewModelScope)

    fun setId(id: Int) {
        if (this.id != id) {
            this.id = id
            uiState = repository.detailFlow(id).asStateFlow(viewModelScope)
        }
    }
}