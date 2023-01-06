package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.models.Watchable
import app.mywatchlist.data.repositories.FavoritesRepository
import app.mywatchlist.data.repositories.WatchablesRepository
import app.mywatchlist.data.repositories.WatchedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repository: WatchablesRepository,
    private val favoritesRepository: FavoritesRepository,
    private val watchedRepository: WatchedRepository
) : ViewModel() {
    val uiState = favoritesRepository.flow.combine(watchedRepository.flow) { favorites, watched ->
        favorites.map { Watchable(repository.getDetails(it), true, watched.contains(it)) }
    }.asStateFlow(viewModelScope)
}