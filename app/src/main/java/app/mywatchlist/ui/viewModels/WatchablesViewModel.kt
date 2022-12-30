package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.models.Watchable
import app.mywatchlist.data.repositories.WatchablesRepository
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
    private val _uiState = MutableStateFlow(ResultUiState<List<Watchable>>())
    val uiState: StateFlow<ResultUiState<List<Watchable>>> = _uiState.asStateFlow()

    private var fetchJob: Job? = null
    private var query: String? = null

    init {
        fetch()
    }

    fun updateQuery(query: String? = null) {
        _uiState.update {
            it.copy(queryString = query)
        }
        fetch(query)
    }

    private fun fetch(query: String? = null) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(loading = true, data = null, error = null)
            }
            try {
                val watchables =
                    if (query != null) repository.search(query) else repository.getTrending()

                _uiState.update {
                    it.copy(data = watchables, loading = false)
                }
            } catch (ioe: IOException) {
                _uiState.update {
                    it.copy(error = ioe.message ?: "Something went wrong!", loading = false)
                }
            }
        }
    }
}