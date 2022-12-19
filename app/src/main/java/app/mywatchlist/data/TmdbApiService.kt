package app.mywatchlist.data

import app.mywatchlist.BuildConfig
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY_QUERY_PARAM = "?api_key=${BuildConfig.TMDB_API_KEY}"
private const val REGION = "US"
private const val LANGUAGE = "en-$REGION"

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
    suspend fun getTrending(@Query("language") language: String = LANGUAGE): Response<Results<List<Watchable>>>

    @GET("movie/{id}$API_KEY_QUERY_PARAM")
    suspend fun getDetails(
        @Path("id") id: Int,
        @Query("language") language: String = LANGUAGE
    ): Response<Watchable>

    @GET("search/movie$API_KEY_QUERY_PARAM")
    suspend fun search(
        @Query("query") query: String,
        @Query("language") language: String = LANGUAGE,
        @Query("region") region: String = REGION
    ): Response<Results<List<Watchable>>>

    @GET("movie/{id}/watch/providers$API_KEY_QUERY_PARAM")
    suspend fun getProviders(@Path("id") id: Int): Response<Results<Map<String, Providers>>>

    @GET("watch/providers/movie$API_KEY_QUERY_PARAM")
    suspend fun getProviders(
        @Query("language") language: String = LANGUAGE,
        @Query("watch_region") region: String = REGION
    ): Response<Results<List<Provider>>>
}

object TmdbApi {
    val service: TmdbApiService by lazy {
        retrofit.create(TmdbApiService::class.java)
    }
}
