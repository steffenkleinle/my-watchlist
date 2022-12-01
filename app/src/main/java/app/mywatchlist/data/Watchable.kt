package app.mywatchlist.data

import com.squareup.moshi.Json
import java.util.Date

data class Watchable(
    @Json val id: Int,
    @Json val title: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json val overview: String,
    @Json val type: String,
    @Json(name = "poster_path") val poster: String,
    @Json(name = "release_date") val releaseDate: Date,
    @Json(name = "vote_average") val voteAverage: Float,
    @Json(name = "vote_count") val voteCount: Int
)
