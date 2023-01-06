package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.models.Watchable
import app.mywatchlist.data.repositories.FavoritesRepository
import app.mywatchlist.data.repositories.WatchablesRepository
import app.mywatchlist.data.repositories.WatchedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchableDetailViewModel @Inject constructor(
    private val repository: WatchablesRepository,
    private val favoritesRepository: FavoritesRepository,
    private val watchedRepository: WatchedRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ResultUiState<Watchable>(loading = true))
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null
    private var id: Int? = null

    fun setId(id: Int) {
        if (this.id != id) {
            this.id = id
            fetch(id)
        }
    }

    fun fetch(id: Int) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(loading = true, data = null, error = null)
            }
            try {
                val rawWatchable = repository.getDetails(id)
                val watchable = Watchable(
                    rawWatchable,
                    favoritesRepository.get().contains(id),
                    watchedRepository.get().contains(id)
                )
                _uiState.update {
                    it.copy(data = watchable, loading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message ?: "Unknown Error", loading = false)
                }
            }
        }
    }

    fun addFavorite(id: Int) = viewModelScope.launch {
        _uiState.update { it.copy(data = it.data?.copy(favorite = true)) }
        favoritesRepository.add(id)
    }

    fun removeFavorite(id: Int) = viewModelScope.launch {
        _uiState.update { it.copy(data = it.data?.copy(favorite = false)) }
        favoritesRepository.remove(id)
    }

    fun addWatched(id: Int) = viewModelScope.launch {
        _uiState.update { it.copy(data = it.data?.copy(watched = true)) }
        watchedRepository.add(id)
    }

    fun removeWatched(id: Int) = viewModelScope.launch {
        _uiState.update { it.copy(data = it.data?.copy(watched = false)) }
        watchedRepository.remove(id)
    }
}