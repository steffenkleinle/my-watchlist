package app.mywatchlist.data

import app.mywatchlist.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.themoviedb.org/3?api_key=${BuildConfig.TMDB_API_KEY}"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TmdbApiService {
    @GET("trending/movie/week")
    suspend fun getTrending() : List<Watchable>
}

object TmdbApi {
    val service: TmdbApiService by lazy {
        retrofit.create(TmdbApiService::class.java)
    }
}
