package app.mywatchlist.data.sources

import android.content.Context
import app.mywatchlist.BuildConfig
import app.mywatchlist.data.models.Provider
import app.mywatchlist.data.models.Providers
import app.mywatchlist.data.models.RawWatchable
import app.mywatchlist.data.models.Watchable
import app.mywatchlist.utils.hasNetwork
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Cache
import okhttp3.OkHttpClient
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
private const val REGION = "US"
private const val LANGUAGE = "en-$REGION"

data class Results<T>(
    @Json val results: T
)

private const val cacheSize = (5 * 1024 * 1024).toLong()
private fun setupOkHttpClient(applicationContext: Context) = OkHttpClient.Builder()
    .cache(Cache(applicationContext.cacheDir, cacheSize))
    .addInterceptor { chain ->
        var request = chain.request()
        request = if (hasNetwork(applicationContext))
            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
        else
            request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                .build()
        chain.proceed(request)
    }
    .build()

private class DateJsonAdapter {
    @FromJson
    fun dateFromJson(dateJson: String): LocalDate? =
        if (dateJson.isNotEmpty()) LocalDate.parse(
            dateJson,
            DateTimeFormatter.ISO_DATE
        ) else null

    @ToJson
    fun dateToJson(date: LocalDate?): String? = date?.format(DateTimeFormatter.ISO_DATE)
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(DateJsonAdapter())
    .build()

private fun setupRetrofit(applicationContext: Context) = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(setupOkHttpClient(applicationContext))
    .build()


interface TmdbApiService {
    @GET("trending/movie/week$API_KEY_QUERY_PARAM")
    suspend fun getTrending(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Response<Results<List<RawWatchable>>>

    @GET("movie/{id}$API_KEY_QUERY_PARAM")
    suspend fun getDetails(
        @Path("id") id: Int,
        @Query("language") language: String
    ): Response<RawWatchable>

    @GET("search/movie$API_KEY_QUERY_PARAM")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int,
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

class TmdbRemoteDateSource @Inject constructor(
    private val favoritesLocalDataSource: FavoritesLocalDataSource,
    private val watchedLocalDataSource: WatchedLocalDataSource,
    @ApplicationContext applicationContext: Context
) {
    private val tmdbApiService: TmdbApiService by lazy {
        setupRetrofit(applicationContext).create(TmdbApiService::class.java)
    }

    suspend fun getTrending(page: Int, language: String = LANGUAGE): List<Watchable> {
        val favorites = favoritesLocalDataSource.get()
        val watched = watchedLocalDataSource.get()
        return rawTrending(page, language).map {
            Watchable(
                it,
                getProviders(it.id),
                favorites.contains(it.id),
                watched.contains(it.id)
            )
        }
    }

    suspend fun favorites(language: String = LANGUAGE): List<Watchable> {
        val favorites = favoritesLocalDataSource.get()
        val watched = watchedLocalDataSource.get()
        return favorites.map {
            Watchable(
                rawDetails(it, language),
                getProviders(it),
                favorites.contains(it),
                watched.contains(it)
            )
        }
    }

    suspend fun detail(id: Int, language: String = LANGUAGE): Watchable {
        val favorites = favoritesLocalDataSource.get()
        val watched = watchedLocalDataSource.get()
        return Watchable(
            rawDetails(id, language),
            getProviders(id),
            favorites.contains(id),
            watched.contains(id)
        )
    }

    suspend fun search(
        query: String,
        page: Int,
        language: String = LANGUAGE,
        region: String = REGION
    ): List<Watchable> {
        val favorites = favoritesLocalDataSource.get()
        val watched = watchedLocalDataSource.get()
        return rawSearch(query, page, language, region).map {
            Watchable(
                it,
                getProviders(it.id),
                favorites.contains(it.id),
                watched.contains(it.id)
            )
        }
    }

    private suspend fun rawTrending(page: Int, language: String = LANGUAGE): List<RawWatchable> =
        withContext(Dispatchers.IO) {
            val response = tmdbApiService.getTrending(page, language)
            response.body()?.results
                ?: throw Error(response.message())
        }

    private suspend fun rawDetails(id: Int, language: String = LANGUAGE): RawWatchable =
        withContext(Dispatchers.IO) {
            val response = tmdbApiService.getDetails(id, language)
            response.body() ?: throw Error(response.message())
        }

    private suspend fun rawSearch(
        query: String,
        page: Int,
        language: String = LANGUAGE,
        region: String = REGION
    ): List<RawWatchable> =
        withContext(Dispatchers.IO) {
            val response = tmdbApiService.search(query, page, language, region)
            response.body()?.results
                ?: throw Error(response.message())
        }

    suspend fun getProviders(watchableId: Int): Map<String, Providers> =
        withContext(Dispatchers.IO) {
            val response = tmdbApiService.getProviders(watchableId)
            response.body()?.results
                ?: throw Error(response.message())
        }

    suspend fun getProviders(
        language: String = LANGUAGE,
        region: String = REGION
    ): List<Provider> =
        withContext(Dispatchers.IO) {
            val response = tmdbApiService.getProviders(language, region)
            response.body()?.results
                ?: throw Error(response.message())
        }
}
