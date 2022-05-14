package com.example.open_weather_app.Interface

import com.example.open_weather_app.Model.WeatherData
import retrofit2.Response
import retrofit2.http.*

interface RetrofitInterface {

    @Headers("Content-Type: application/json")
    @GET("/data/2.5/onecall")
    suspend fun getData(@Query("lat") lat:Double, @Query("lon") lon:Double, @Query("units") units:String, @Query("appid") appId : String): Response<WeatherData>
}