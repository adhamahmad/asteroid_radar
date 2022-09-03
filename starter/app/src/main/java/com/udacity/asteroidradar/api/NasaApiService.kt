package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit



private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.BASE_URL)
    .build()


interface NasaApiService{
@GET("neo/rest/v1/feed/")
 suspend fun getAsteriods( // get Asteroids for the next 7 days starting from today
    @Query("start_date") startDate: String = Constants.today,
    @Query("end_date") endDate: String = Constants.endDate,
    @Query("api_key") apiKey: String = Constants.API_KEY
): String

@GET("planetary/apod/")
 fun getPictureOfTheDay(
    @Query("api_key") api_key: String = Constants.API_KEY
): Deferred<PictureOfDay>

}
object NasaApi{
    val retrofitService: NasaApiService by lazy{
        retrofit.create(NasaApiService::class.java)
    }
}