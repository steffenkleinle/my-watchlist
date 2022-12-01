package app.mywatchlist.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.TmdbApi
import app.mywatchlist.data.Watchables
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(Watchables(listOf()))
    val uiState: StateFlow<Watchables> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun fetchTrending() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val watchables = TmdbApi.service.getTrending()
                _uiState.update {
                    it.copy(watchables = watchables)
                }
            } catch (ioe: IOException) {
                // TODO Add error handling
            }
        }
    }
}