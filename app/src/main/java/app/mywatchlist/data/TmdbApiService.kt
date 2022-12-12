package app.mywatchlist.data

import app.mywatchlist.BuildConfig
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY_QUERY_PARAM = "?api_key=${BuildConfig.TMDB_API_KEY}"

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
    @GET("trending/{watchable_type}/week$API_KEY_QUERY_PARAM")
    suspend fun getTrending(@Path("watchable_type") watchableType: WatchableType = WatchableType.movie): Response<Watchables>

    @GET("{watchable_type}/{watchable_id}$API_KEY_QUERY_PARAM")
    suspend fun getDetails(
        @Path("watchable_type") watchableType: WatchableType = WatchableType.movie,
        @Path("watchable_id") watchableId: Int
    ): Response<Watchable>

    @GET("{watchable_type}/{watchable_id}/watch/providers$API_KEY_QUERY_PARAM")
    suspend fun getProviders(
        @Path("watchable_type") watchableType: WatchableType = WatchableType.movie,
        @Path("watchable_id") watchableId: Int
    ): Response<CountryProviders>
}

object TmdbApi {
    val service: TmdbApiService by lazy {
        retrofit.create(TmdbApiService::class.java)
    }
}
