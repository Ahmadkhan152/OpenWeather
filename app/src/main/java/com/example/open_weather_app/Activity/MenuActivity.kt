package com.example.open_weather_app.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.open_weather_app.Constants.SETTINGS
import com.example.open_weather_app.CustomizeUnitActivity
import com.example.open_weather_app.R


class MenuActivity : AppCompatActivity() {
    lateinit var layoutDifferentWeather:ConstraintLayout
    lateinit var layoutCustomerUnits:ConstraintLayout
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        val toolbar:androidx.appcompat.widget.Toolbar=findViewById(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        val actionbar=supportActionBar
        actionbar?.title=SETTINGS
        actionbar?.setDisplayHomeAsUpEnabled(true)


        layoutDifferentWeather=findViewById(R.id.layoutDifferentWeather)
        layoutCustomerUnits=findViewById(R.id.layoutCustomizeUnits)

        layoutDifferentWeather.setOnClickListener {

            startActivity(Intent(this@MenuActivity,DifferentWeatherActivity::class.java))
        }
        layoutCustomerUnits.setOnClickListener {

            startActivity(Intent(this@MenuActivity,CustomizeUnitActivity::class.java))
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}