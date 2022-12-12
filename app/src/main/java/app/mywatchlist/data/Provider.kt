package app.mywatchlist.data

import com.squareup.moshi.Json

data class Provider(
    @Json(name = "provider_id") val id: Int,
    @Json(name = "provider_name") val name: String,
    @Json(name = "logo_path") val logo: String
)

data class Providers(
    @Json val buy: List<Provider>?,
    @Json val flatrate: List<Provider>?,
    @Json val rent: List<Provider>?
)

data class CountryProviders(
    @Json(name = "results") val providers: Map<String, Providers>
)