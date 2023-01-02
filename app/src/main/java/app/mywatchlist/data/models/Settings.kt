package app.mywatchlist.data.models

data class Settings(
    val language: String,
    val region: String,
    val providers: List<Int>
)