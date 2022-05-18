package com.example.open_weather_app.Activity

import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.open_weather_app.Constants.*
import com.example.open_weather_app.R

class DifferentWeatherActivity : AppCompatActivity(){

    lateinit var layoutClearSky:ConstraintLayout
    lateinit var layoutFewClouds:ConstraintLayout
    lateinit var layoutOvercastClouds:ConstraintLayout
    lateinit var layoutDrizzle:ConstraintLayout
    lateinit var layoutRain:ConstraintLayout
    lateinit var layoutShowerRain:ConstraintLayout
    lateinit var layoutThunderStorm:ConstraintLayout
    lateinit var layoutSnow:ConstraintLayout
    lateinit var layoutMist:ConstraintLayout
    lateinit var tvSkylike:TextView
    lateinit var ivClearsky:ImageView
    lateinit var ivFewclouds:ImageView
    lateinit var ivOvercastclouds:ImageView
    lateinit var ivDrizzle:ImageView
    lateinit var ivRain:ImageView
    lateinit var ivShowerrain:ImageView
    lateinit var ivThunderstorm:ImageView
    lateinit var ivSnow:ImageView
    lateinit var ivMist:ImageView
    lateinit var seekbarTemperature:SeekBar
    lateinit var seekbarWind:SeekBar
    lateinit var seekbarStatusViewTemperature:TextView
    lateinit var seekbarStatusViewWind:TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_different_weather)



        val toolbar:androidx.appcompat.widget.Toolbar= findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar=supportActionBar
        actionbar?.title= DIFFERENT_WEATHER
        actionbar?.setDisplayHomeAsUpEnabled(true)



        ivClearsky=findViewById(R.id.ivClearSky)
        ivFewclouds=findViewById(R.id.ivFewClouds)
        ivOvercastclouds=findViewById(R.id.ivOvercastClouds)
        ivDrizzle=findViewById(R.id.ivDrizzle)
        ivRain=findViewById(R.id.ivRain)
        ivShowerrain=findViewById(R.id.ivShowerRain)
        ivThunderstorm=findViewById(R.id.ivThunderStorm)
        ivSnow=findViewById(R.id.ivSnow)
        ivMist=findViewById(R.id.ivMist)
        layoutClearSky=findViewById(R.id.layoutClearSky)
        layoutFewClouds=findViewById(R.id.layoutFewClouds)
        layoutOvercastClouds=findViewById(R.id.layoutOvercastClouds)
        layoutDrizzle=findViewById(R.id.layoutDrizzle)
        layoutRain=findViewById(R.id.layoutRain)
        layoutShowerRain=findViewById(R.id.layoutShowerRain)
        layoutThunderStorm=findViewById(R.id.layoutThunderStorms)
        layoutSnow=findViewById(R.id.layoutSnow)
        layoutMist=findViewById(R.id.layoutMist)
        tvSkylike=findViewById(R.id.tvSkylike)
        seekbarTemperature=findViewById(R.id.seekbarTemperature)
        seekbarStatusViewTemperature=findViewById(R.id.tvShowTemperature)
        seekbarWind=findViewById(R.id.seekbarWind)
        seekbarStatusViewWind=findViewById(R.id.tvShowWind)
        seekbarTemperature.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                seekbarStatusViewTemperature.text=p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        seekbarWind.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                when (p1) {
                    1 -> seekbarStatusViewWind.text=LIGHT
                    2 -> seekbarStatusViewWind.text= MODERATE
                    3 -> seekbarStatusViewWind.text= STRONG
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })



        //Apply Image In the Image View
        var URLForImages= BASE_URL_FOR_IMAGES +"01d"+ URL_END_FOR_IMAGES
        Glide.with(this)
            .load(URLForImages)
            .into(ivClearsky)
        URLForImages= BASE_URL_FOR_IMAGES+"02d"+ URL_END_FOR_IMAGES
        Glide.with(this)
            .load(URLForImages)
            .into(ivFewclouds)

        URLForImages= BASE_URL_FOR_IMAGES+"04d"+ URL_END_FOR_IMAGES
        Glide.with(this)
            .load(URLForImages)
            .into(ivOvercastclouds)
        URLForImages= BASE_URL_FOR_IMAGES+"09d"+ URL_END_FOR_IMAGES
        Glide.with(this)
            .load(URLForImages)
            .into(ivDrizzle)
        URLForImages= BASE_URL_FOR_IMAGES+"10d"+ URL_END_FOR_IMAGES
        Glide.with(this)
            .load(URLForImages)
            .into(ivRain)
        URLForImages= BASE_URL_FOR_IMAGES+"09d"+ URL_END_FOR_IMAGES
        Glide.with(this)
            .load(URLForImages)
            .into(ivShowerrain)
        URLForImages= BASE_URL_FOR_IMAGES+"11d"+ URL_END_FOR_IMAGES
        Glide.with(this)
            .load(URLForImages)
            .into(ivThunderstorm)
        URLForImages= BASE_URL_FOR_IMAGES+"13d"+ URL_END_FOR_IMAGES
        Glide.with(this)
            .load(URLForImages)
            .into(ivSnow)
        URLForImages= BASE_URL_FOR_IMAGES+"50d"+ URL_END_FOR_IMAGES
        Glide.with(this)
            .load(URLForImages)
            .into(ivMist)
        layoutClearSky.setOnClickListener {
            tvSkylike.text="Clear sky"
        }
        layoutFewClouds.setOnClickListener {
            tvSkylike.text="Few clouds"
        }
        layoutOvercastClouds.setOnClickListener {
            tvSkylike.text="Overcast clouds"
        }
        layoutDrizzle.setOnClickListener {
            tvSkylike.text="Drizzle"

        }
        layoutRain.setOnClickListener {
            tvSkylike.text="Rain"

        }
        layoutShowerRain.setOnClickListener {
            tvSkylike.text="Shower rain"

        }
        layoutThunderStorm.setOnClickListener {
            tvSkylike.text="Thunderstorm"

        }
        layoutSnow.setOnClickListener {
            tvSkylike.text="Snow"

        }
        layoutMist.setOnClickListener {
            tvSkylike.text="Mist"

        }
    }
}