package app.mywatchlist.data.models

import com.squareup.moshi.Json
import java.time.LocalDate

data class Genre(
    val id: Int,
    val name: String
)

data class RawWatchable(
    @Json val id: Int,
    @Json val title: String,
    @Json val overview: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "release_date") val releaseDate: LocalDate?,
    @Json(name = "vote_average") val voteAverage: Float,
    @Json(name = "vote_count") val voteCount: Int,
    @Json val runtime: Int?,
    @Json val tagline: String?,
    @Json val genres: List<Genre>?,
    val providers: Map<String, Providers>?
)

data class Watchable(
    val id: Int,
    val title: String,
    val overview: String,
    val originalTitle: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: LocalDate?,
    val voteAverage: Float,
    val voteCount: Int,
    val runtime: Int?,
    val tagline: String?,
    val genres: List<Genre>?,
    val providers: Map<String, Providers>,
    val favorite: Boolean,
    val watched: Boolean
) {
    constructor(
        rawWatchable: RawWatchable,
        favorite: Boolean,
        watched: Boolean
    ) : this(
        id = rawWatchable.id,
        title = rawWatchable.title,
        overview = rawWatchable.overview,
        originalTitle = rawWatchable.originalTitle,
        posterPath = rawWatchable.posterPath,
        backdropPath = rawWatchable.backdropPath,
        releaseDate = rawWatchable.releaseDate,
        voteAverage = rawWatchable.voteAverage,
        voteCount = rawWatchable.voteCount,
        runtime = rawWatchable.runtime,
        tagline = rawWatchable.tagline,
        genres = rawWatchable.genres,
        providers = rawWatchable.providers!!,
        favorite = favorite,
        watched = watched
    )
}
