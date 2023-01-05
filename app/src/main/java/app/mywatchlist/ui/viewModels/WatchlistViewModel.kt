package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.models.Watchable
import app.mywatchlist.data.repositories.WatchablesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repository: WatchablesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ResultUiState<List<Watchable>>(loading = true))
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        fetch()
    }

    fun fetch() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(loading = true, data = null, error = null)
            }
            try {
                val watchables = repository.getFavorites()

                _uiState.update {
                    it.copy(data = watchables, loading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message ?: "Unknown Error", loading = false)
                }
            }
        }
    }

    fun addFavorite(id: Int) = viewModelScope.launch {
        repository.favorites.add(id)
    }

    fun removeFavorite(id: Int) = viewModelScope.launch {
        repository.favorites.remove(id)
    }

    fun addWatched(id: Int) = viewModelScope.launch {
        repository.watched.add(id)
    }

    fun removeWatched(id: Int) = viewModelScope.launch {
        repository.watched.remove(id)
    }
}