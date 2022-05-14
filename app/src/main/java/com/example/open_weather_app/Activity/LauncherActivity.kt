package com.example.open_weather_app.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.open_weather_app.R
import kotlinx.coroutines.delay
import java.sql.Time
import java.util.*
import kotlin.concurrent.schedule

class LauncherActivity : AppCompatActivity() {

    lateinit var ivOpenWeather:ImageView
    lateinit var tvLoading:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)


        ivOpenWeather=findViewById(R.id.ivOpenWeather)
        tvLoading=findViewById(R.id.tvLoading)
        Timer().schedule(2000){
            startActivity(Intent(this@LauncherActivity,HomePageActivity::class.java))
            finish()
        }
    }
}