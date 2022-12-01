package app.mywatchlist.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val TMDB_API_KEY_PLACEHOLDER = "TMDB_API_KEY"
private const val BASE_URL =
    "https://api.themoviedb.org/3?api_key=$TMDB_API_KEY_PLACEHOLDER"

interface TmdbApiService {
    @GET("trending/movie/week")
    suspend fun getTrending() : List<Watchable>
}

object TmdbApi {
    lateinit var tmdbApiKey: String

    val service: TmdbApiService by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL.replace(TMDB_API_KEY_PLACEHOLDER, tmdbApiKey))
            .build()

        retrofit.create(TmdbApiService::class.java)
    }
}
