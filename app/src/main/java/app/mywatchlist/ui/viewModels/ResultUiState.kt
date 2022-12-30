package app.mywatchlist.ui.viewModels

data class ResultUiState<T>(
    val queryString: String? = "",
    val loading: Boolean = false,
    val error: String? = null,
    val data: T? = null
)