package com.udacity.asteroidradar.network

//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val KEY = "bmk718QJL4hmeUnIxU81itxaN2rBReJYp9qZ9qdU"
private const val START_DATE = "2022-01-01"
private const val END_DATE = "2022-01-05"

interface NASAService {
    @GET("feed?start_date=${START_DATE}&end_date=${END_DATE}&api_key=${KEY}")
    fun getAsteroidsListAsync(): NetworkAsteroidContainer
    // Deferred<NetworkAsteroidContainer>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object Network {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/neo/rest/v1/")
        .addConverterFactory(ScalarsConverterFactory.create())
//        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val nasa: NASAService = retrofit.create(NASAService::class.java)
}