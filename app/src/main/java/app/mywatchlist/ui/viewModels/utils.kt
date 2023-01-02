package app.mywatchlist.ui.viewModels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

data class ResultUiState<T>(
    val loading: Boolean = false,
    val error: String? = null,
    val data: T? = null
)

fun <T> initialStateFlow(viewModelScope: CoroutineScope): StateFlow<ResultUiState<T>> =
    flow<ResultUiState<T>> { }
        .stateIn(
            scope = viewModelScope,
            initialValue = ResultUiState(loading = true, error = null, data = null),
            started = SharingStarted.WhileSubscribed(5000L)
        )


fun <T> Flow<T>.asStateFlow(viewModelScope: CoroutineScope): StateFlow<ResultUiState<T>> = this
    .catch { ResultUiState(loading = false, error = it.message ?: "Error", data = null) }
    .map { ResultUiState(loading = false, error = null, data = it) }
    .stateIn(
        scope = viewModelScope,
        initialValue = ResultUiState(loading = true, error = null, data = null),
        started = SharingStarted.WhileSubscribed(5000L)
    )