package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.repositories.WatchablesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repository: WatchablesRepository,
) : ViewModel() {
    val uiState = repository.favoritesFlow().asStateFlow(viewModelScope)

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