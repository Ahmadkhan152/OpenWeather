package com.example.open_weather_app.Model

data class WeatherData(val current: Current,val hourly:ArrayList<Current>,val daily:ArrayList<Daily>)