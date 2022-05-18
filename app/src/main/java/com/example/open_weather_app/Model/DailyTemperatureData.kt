package com.example.open_weather_app.Model

import java.io.Serializable

data class DailyTemperatureData(val day:Double,val min:Double,val max:Double,val night:Double,val eve:Double,val morn:Double):Serializable
