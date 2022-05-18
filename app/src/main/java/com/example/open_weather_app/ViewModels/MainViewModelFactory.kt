package com.example.open_weather_app.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.open_weather_app.Repository.ClassRepo

class MainViewModelFactory(private val repository:ClassRepo,val lat:Double,val long:Double,val units:String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val mainViewModel=MainViewModel(repository,lat,long,units)
        mainViewModel.initData(units)
        return mainViewModel as T
    }
}