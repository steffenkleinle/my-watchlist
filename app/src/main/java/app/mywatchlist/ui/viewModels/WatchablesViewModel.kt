package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.TmdbApi
import app.mywatchlist.ui.uiStates.WatchablesUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class WatchablesViewModel(var query: String? = null) : ViewModel() {
    private val _uiState = MutableStateFlow(WatchablesUiState())
    val uiState: StateFlow<WatchablesUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        fetchTrending()
    }

    fun updateQuery(query: String? = null) {
        this.query = query
        fetchTrending(query)
    }

    private fun fetchTrending(query: String? = null) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(loading = true, watchables = listOf(), error = null)
            }
            try {
                val response =
                    if (query != null) TmdbApi.service.search(query) else TmdbApi.service.getTrending()
                val watchables = response.body()?.results?.map {
                    val providerResponse = TmdbApi.service.getProviders(it.id)
                    val providers = providerResponse.body()?.results
                    it.copy(providers = providers ?: mapOf())
                }
                _uiState.update {
                    if (watchables != null) it.copy(watchables = watchables, loading = false)
                    else it.copy(error = "Data missing: ${response.message()}", loading = false)
                }
            } catch (ioe: IOException) {
                _uiState.update {
                    it.copy(error = ioe.message ?: "Something went wrong!", loading = false)
                }
            }
        }
    }
}