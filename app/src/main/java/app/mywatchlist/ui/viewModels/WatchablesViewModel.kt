package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.repositories.WatchablesRepository
import app.mywatchlist.ui.uiStates.WatchablesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WatchablesViewModel @Inject constructor(private val repository: WatchablesRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(WatchablesUiState())
    val uiState: StateFlow<WatchablesUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null
    private var query: String? = null

    init {
        fetch()
    }

    fun updateQuery(query: String? = null) {
        this.query = query
        fetch(query)
    }

    private fun fetch(query: String? = null) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(loading = true, watchables = listOf(), error = null)
            }
            try {
                val watchables =
                    if (query != null) repository.search(query) else repository.getTrending()

                _uiState.update {
                    it.copy(watchables = watchables, loading = false)
                }
            } catch (ioe: IOException) {
                _uiState.update {
                    it.copy(error = ioe.message ?: "Something went wrong!", loading = false)
                }
            }
        }
    }
}