package app.mywatchlist.data

import com.squareup.moshi.Json
import java.time.LocalDate

enum class WatchableType {
    movie, tv
}

data class Watchable(
    @Json val id: Int,
    @Json val title: String,
    @Json val overview: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "media_type") val type: WatchableType = WatchableType.movie,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "release_date") val releaseDate: LocalDate,
    @Json(name = "vote_average") val voteAverage: Float,
    @Json(name = "vote_count") val voteCount: Int,
    val providers: Map<String, Providers>? = null
)
