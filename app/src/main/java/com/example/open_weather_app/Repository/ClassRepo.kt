package com.example.open_weather_app.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.open_weather_app.Interface.RetrofitInterface
import com.example.open_weather_app.Model.WeatherData
import retrofit2.Response

class ClassRepo(private val retrofitInterface: RetrofitInterface) {

    private val weatherLiveData= MutableLiveData<WeatherData>()

    val weather:LiveData<WeatherData>
    get()=weatherLiveData
    suspend fun getWeather(lat:Double,long:Double,units:String): Response<WeatherData> {
        return retrofitInterface.getData(lat,long,units,"d2100ba93eabfca395ceb489d2206a93")
    }
}