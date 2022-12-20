package app.mywatchlist.ui.uiStates

import app.mywatchlist.data.models.Watchable

data class WatchableUiState(
    val watchable: Watchable? = null,
    val loading: Boolean = false,
    val errors: String? = null
)