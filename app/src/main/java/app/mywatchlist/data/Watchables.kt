package app.mywatchlist.data

import com.squareup.moshi.Json

data class Watchables(
    @Json(name = "results") val watchables: List<Watchable>
)
