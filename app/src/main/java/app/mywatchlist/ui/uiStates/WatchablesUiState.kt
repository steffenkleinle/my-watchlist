package app.mywatchlist.ui.uiStates

import app.mywatchlist.data.models.Watchable

data class WatchablesUiState(
    val watchables: List<Watchable> = listOf(),
    val loading: Boolean = false,
    val error: String? = null
)