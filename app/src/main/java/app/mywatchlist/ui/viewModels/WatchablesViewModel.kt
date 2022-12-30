package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.repositories.WatchablesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchablesViewModel @Inject constructor(repository: WatchablesRepository) : ViewModel() {
    val uiState = repository.trendingFlow().asStateFlow(viewModelScope)
}