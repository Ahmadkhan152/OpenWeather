package com.example.open_weather_app.Activity

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.open_weather_app.Constants.*
import com.example.open_weather_app.Interface.RetrofitInterface
import com.example.open_weather_app.Model.WeatherData
import com.example.open_weather_app.Object.RetrofitHelper
import com.example.open_weather_app.R
import com.example.open_weather_app.Receiver.MyReceiver
import com.example.open_weather_app.Repository.ClassRepo
import com.example.open_weather_app.ViewModels.MainViewModel
import com.example.open_weather_app.ViewModels.MainViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import java.io.Serializable
import java.util.*

class LauncherActivity : AppCompatActivity() {

    lateinit var ivOpenWeather:ImageView
    lateinit var tvLoading:TextView
    lateinit var weatherService: RetrofitInterface
    lateinit var repository: ClassRepo
    lateinit var mainViewModel: MainViewModel
    lateinit var sharedPreferences: SharedPreferences
    var checkInternetConnection:Int=0
    var units:String=""
    var stateName:String=""
    var countryName:String=""
    var cityName:String=""
    var address:String=""
    lateinit var filter: IntentFilter
    lateinit var receiver: MyReceiver
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var latitude:Double=0.0;
    var longitude:Double=0.0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        //var intent:Intent
        sharedPreferences=getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE)
        units= sharedPreferences.getString(UNITS, METRIC).toString()
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this)
        ivOpenWeather=findViewById(R.id.ivOpenWeather)
        tvLoading=findViewById(R.id.tvLoading)

    }
    private fun getLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 569)
        }
        else
        {
            var locationTask: Task<Location> = fusedLocationProviderClient.lastLocation
        locationTask.addOnSuccessListener { location: Location? ->
            if (location != null)
            {
                latitude=location.latitude
                longitude=location.longitude
                Log.i("LatitudeL","$latitude And $longitude")
                getAddress(latitude,longitude)
            }
        }
        locationTask.addOnFailureListener{
            Toast.makeText(this, LOCATION_FAILED, Toast.LENGTH_SHORT).show();
        }
        }
    }
    override fun onStart() {
        super.onStart()
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            getLocation();
        }
        else
        {
            askLocationPermission()
        }
    }
    private fun askLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),568)
                getLocation()
            }
            else
            {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),568)
                getLocation()
            }
        }
    }
    fun getAddress(lat:Double,lon:Double) {
        val aLocale: Locale = Locale.Builder().setLocale(Locale.getDefault())
            .setLanguage("en").build()
        val geocoder= Geocoder(this, aLocale)
        val addresses:ArrayList<Address> =geocoder.getFromLocation(31.5193,74.3228,2) as ArrayList<Address>
        cityName=addresses[1].subLocality
        stateName=addresses[0].adminArea
        countryName=addresses[0].countryName
        val intent=Intent(this@LauncherActivity,HomePageActivity::class.java)
        address="${cityName}, ${countryName}"
        weatherService= RetrofitHelper.getInstance().create(RetrofitInterface::class.java)
        repository=ClassRepo(weatherService)
        mainViewModel= ViewModelProvider(this, MainViewModelFactory(repository,31.5194,74.3228,units)).get(MainViewModel::class.java)
        mainViewModel.weather.observe(this) {
            val intent= Intent(this@LauncherActivity,HomePageActivity::class.java)
            intent.putExtra(OBJECT,it)
            intent.putExtra(ADDRESS,address)
            startActivity(intent)
            finish()
        }
        //intent.putExtra(ADDRESS,address)
//        startActivity(intent)
//        finish()
    }
}