package com.example.open_weather_app.Model

import java.io.Serializable

data class DailyWeather(val dt:Int,val temp:DailyTemperatureData):Serializable
