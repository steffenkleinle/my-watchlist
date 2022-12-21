package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.repositories.WatchablesRepository
import app.mywatchlist.ui.uiStates.WatchableUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WatchableViewModel @Inject constructor(private val repository: WatchablesRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(WatchableUiState())
    val uiState: StateFlow<WatchableUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null
    private var id: Int? = null

    fun set(id: Int) {
        this.id = id
        fetch(id)
    }

    private fun fetch(id: Int) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(loading = true, watchable = null, error = null)
            }
            try {
                val watchable = repository.getDetails(id)
                _uiState.update {
                    it.copy(watchable = watchable, loading = false)
                }
            } catch (ioe: IOException) {
                _uiState.update {
                    it.copy(error = ioe.message ?: "Something went wrong!", loading = false)
                }
            }
        }
    }
}