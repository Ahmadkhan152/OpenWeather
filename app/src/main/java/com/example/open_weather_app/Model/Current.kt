package com.example.open_weather_app.Model

data class Current(val dt:Long,val temp:Double,val feels_like:Double,val wind_speed:Double,val visibility:Double
,val uvi:Double,val humidity:Double,val pressure:Double,val dew_point:Double,val weather: ArrayList<Weather>)