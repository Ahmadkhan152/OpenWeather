package com.example.open_weather_app.Model

data class Daily(val dt:Int,val temp:DailyTemperatureData,val weather:ArrayList<Weather>)