package app.mywatchlist.data.models

import com.squareup.moshi.Json
import java.time.LocalDate

enum class WatchableType {
    movie, tv
}

data class RawWatchable(
    @Json val id: Int,
    @Json val title: String,
    @Json val overview: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "media_type") val type: WatchableType = WatchableType.movie,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "release_date") val releaseDate: LocalDate,
    @Json(name = "vote_average") val voteAverage: Float,
    @Json(name = "vote_count") val voteCount: Int
)

data class Watchable(
    val id: Int,
    val title: String,
    val overview: String,
    val originalTitle: String,
    val type: WatchableType,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: LocalDate,
    val voteAverage: Float,
    val voteCount: Int,
    val providers: Map<String, Providers>
) {
    constructor(rawWatchable: RawWatchable, providers: Map<String, Providers>) : this(
        id = rawWatchable.id,
        title = rawWatchable.title,
        overview = rawWatchable.overview,
        originalTitle = rawWatchable.originalTitle,
        type = rawWatchable.type,
        posterPath = rawWatchable.posterPath,
        backdropPath = rawWatchable.backdropPath,
        releaseDate = rawWatchable.releaseDate,
        voteAverage = rawWatchable.voteAverage,
        voteCount = rawWatchable.voteCount,
        providers = providers
    )
}