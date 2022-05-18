package com.example.open_weather_app.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.open_weather_app.Model.WeatherData
import com.example.open_weather_app.Repository.ClassRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(val classRepo: ClassRepo,val lat:Double,val long:Double,val units:String):ViewModel() {
    fun initData(units: String){
        viewModelScope.launch(Dispatchers.IO){
            val result = classRepo.getWeather(lat,long,units)
            if (result.body()!=null)
            {
                weather.postValue(result.body())
            }
        }
    }
    var weather:MutableLiveData<WeatherData> = MutableLiveData<WeatherData>()
}