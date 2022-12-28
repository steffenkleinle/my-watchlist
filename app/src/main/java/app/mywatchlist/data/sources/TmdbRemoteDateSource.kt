package app.mywatchlist.data.sources

import app.mywatchlist.BuildConfig
import app.mywatchlist.data.models.Provider
import app.mywatchlist.data.models.Providers
import app.mywatchlist.data.models.RawWatchable
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY_QUERY_PARAM = "?api_key=${BuildConfig.TMDB_API_KEY}"

data class Results<T>(
    @Json val results: T
)

private class DateJsonAdapter {
    @FromJson
    fun dateFromJson(dateJson: String): LocalDate =
        LocalDate.parse(dateJson, DateTimeFormatter.ISO_DATE)

    @ToJson
    fun dateToJson(date: LocalDate): String = date.format(DateTimeFormatter.ISO_DATE)
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(DateJsonAdapter())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface TmdbApiService {
    @GET("trending/movie/week$API_KEY_QUERY_PARAM")
    suspend fun getTrending(@Query("language") language: String): Response<Results<List<RawWatchable>>>

    @GET("movie/{id}$API_KEY_QUERY_PARAM")
    suspend fun getDetails(
        @Path("id") id: Int,
        @Query("language") language: String
    ): Response<RawWatchable>

    @GET("search/movie$API_KEY_QUERY_PARAM")
    suspend fun search(
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Response<Results<List<RawWatchable>>>

    @GET("movie/{id}/watch/providers$API_KEY_QUERY_PARAM")
    suspend fun getProviders(@Path("id") id: Int): Response<Results<Map<String, Providers>>>

    @GET("watch/providers/movie$API_KEY_QUERY_PARAM")
    suspend fun getProviders(
        @Query("language") language: String,
        @Query("watch_region") region: String
    ): Response<Results<List<Provider>>>
}

class TmdbRemoteDateSource @Inject constructor() {
    private val tmdbApiService: TmdbApiService by lazy {
        retrofit.create(TmdbApiService::class.java)
    }

    suspend fun getTrending(language: String): Response<Results<List<RawWatchable>>> =
        withContext(Dispatchers.IO) {
            tmdbApiService.getTrending(language)
        }

    suspend fun getDetails(id: Int, language: String): Response<RawWatchable> =
        withContext(Dispatchers.IO) {
            tmdbApiService.getDetails(id, language)
        }

    suspend fun search(
        query: String,
        language: String,
        region: String
    ): Response<Results<List<RawWatchable>>> =
        withContext(Dispatchers.IO) {
            tmdbApiService.search(query, language, region)
        }

    suspend fun getProviders(watchableId: Int): Response<Results<Map<String, Providers>>> =
        withContext(Dispatchers.IO) {
            tmdbApiService.getProviders(watchableId)
        }

    suspend fun getProviders(
        language: String,
        region: String
    ): Response<Results<List<Provider>>> =
        withContext(Dispatchers.IO) {
            tmdbApiService.getProviders(language, region)
        }
}
