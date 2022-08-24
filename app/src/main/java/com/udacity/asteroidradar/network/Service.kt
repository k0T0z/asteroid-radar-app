package com.udacity.asteroidradar.network

//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit


private const val START_DATE = "2022-08-24"
private const val END_DATE = "2022-08-24"

private const val BASE_URL = "https://api.nasa.gov/"

enum class NASAApiFilter(val value: String) { SHOW_WEEK("week"), SHOW_DAY("day"), SHOW_ALL("all") }

interface NASAService {
    @GET("neo/rest/v1/feed?api_key=${Constants.KEY}")
    suspend fun getResponse(
        @Query("start_date") startDate: String, @Query("end_date") endDate: String
    ): String

    @GET("planetary/apod?api_key=${Constants.KEY}")
    suspend fun getPictureOfDay(): PictureOfDay

}

//private fun getClient(): OkHttpClient {
//    val httpClient = OkHttpClient.Builder()
//
//    httpClient.readTimeout(60, TimeUnit.SECONDS)
//    httpClient.connectTimeout(60, TimeUnit.SECONDS)
//    if (BuildConfig.DEBUG) {
//        val logging = HttpLoggingInterceptor()
//        logging.level = HttpLoggingInterceptor.Level.BODY
//        httpClient.addInterceptor(logging)
//    }
//    return httpClient.build()
//}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object Network {
    private val retrofit = Retrofit.Builder()
//        .client(getClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    private val retrofitPictureOfDay = Retrofit.Builder()
//        .client(getClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val nasa: NASAService = retrofit.create(NASAService::class.java)
    val nasaPictureOfDay: NASAService = retrofitPictureOfDay.create(NASAService::class.java)
}