package com.example.open_weather_app.Receiver

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.open_weather_app.Activity.HomePageActivity
import com.example.open_weather_app.Constants.*

class MyReceiver(val homePageActivity: HomePageActivity):BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {


        val checkForTemperature: Boolean? =p1?.getBooleanExtra(CHECK_FOR_TEMPERATURE,false)
        val checkForWindSpeed:Boolean?=p1?.getBooleanExtra(CHECK_FOR_WIND,false)
        val checkForPressure:Boolean?=p1?.getBooleanExtra(CHECK_FOR_PRESSURE,false)
        val checkForPrecipitation:Boolean?=p1?.getBooleanExtra(CHECK_FOR_PRECIPITATION,false)
        val checkForDistance:Boolean?=p1?.getBooleanExtra(CHECK_FOR_DISTANCE,false)
        val checkForTimeFormat:Boolean?=p1?.getBooleanExtra(CHECK_FOR_TIME_FORMAT,false)




        if (checkForTemperature!!)
        {
            if (p1?.getBooleanExtra(CHECK_FOR_CELIUS,false))
            {
                homePageActivity.mainViewModel.initData(METRIC)
            }
            else
            {
                homePageActivity.mainViewModel.initData(IMPERIAL)
            }
        }
        else if(checkForWindSpeed!!)
        {
            val wind=homePageActivity.wind
            if (p1?.getBooleanExtra(CHECK_FOR_METER_PER_SECOND,false))
            {
                homePageActivity.tvWind.text="Wind: ${wind}m/s"
            }
            else if(p1?.getBooleanExtra(CHECK_FOR_KILO_METER_PER_HOUR,false))
            {
                homePageActivity.tvWind.text="Wind: ${wind}km/h"
            }
            else if(p1?.getBooleanExtra(CHECK_FOR_METER_PER_HOUR,false))
            {
                homePageActivity.tvWind.text="Wind: ${wind}mph"
            }
        }
        else if(checkForPressure!!)
        {
            var pressure=homePageActivity.pressure
            if (p1?.getBooleanExtra(CHECK_FOR_HPA,false))
            {
                pressure *= 33.86f
                homePageActivity.tvPressure.text="Pressure: ${pressure}hPa"
            }
            else
            {
                pressure /= 33.86f
                homePageActivity.tvPressure.text="Pressure: ${pressure}inHg"
            }
        }
        else if(checkForPrecipitation!!)
        {

        }
        else if(checkForDistance!!)
        {

            var distance=homePageActivity.distance
            if (p1?.getBooleanExtra(CHECK_FOR_KILO_METER,false))
            {
                distance/=0.6214f
                homePageActivity.tvVisiblity.text="Visiblity: ${distance}km"
            }
            else
            {
                distance*=0.6214f
                homePageActivity.tvVisiblity.text="Visiblity: ${distance}mi"

            }
        }else if(checkForTimeFormat!!){
            if (p1?.getStringExtra(CHECK_FOR_TIME_FORMAT)=="24")
            {
                homePageActivity.mainViewModel.initData(METRIC)
            }
            else
            {
                homePageActivity.mainViewModel.initData(METRIC)
            }
        }
        else
        {
        }


    }
}