package app.mywatchlist.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.TmdbApi
import app.mywatchlist.data.Watchable
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(listOf<Watchable>())
    val uiState: StateFlow<List<Watchable>> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        fetchTrending()
    }

    private fun fetchTrending() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val response = TmdbApi.service.getTrending()
                val watchables = response.body()?.results?.map {
                    val providerResponse = TmdbApi.service.getProviders(it.id)
                    val providers = providerResponse.body()?.results
                    it.copy(providers = providers ?: mapOf())
                }
                _uiState.update {
                    watchables ?: listOf()
                }
            } catch (ioe: IOException) {
                // TODO Add error handling
            }
        }
    }
}