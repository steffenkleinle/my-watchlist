package app.mywatchlist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mywatchlist.data.models.Settings
import app.mywatchlist.data.repositories.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(repository: SettingsRepository) :
    ViewModel() {
    val uiState: StateFlow<ResultUiState<Settings>> =
        repository.settingsFlow
            .catch { ResultUiState(loading = false, error = it.message ?: "Error", data = null) }
            .map { ResultUiState(loading = false, error = null, data = it) }
            .stateIn(
                scope = viewModelScope,
                initialValue = ResultUiState(loading = true, error = null, data = null),
                started = SharingStarted.WhileSubscribed(5000L)
            )
}