package com.example.open_weather_app.Activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.open_weather_app.Constants.BASE_URL_FOR_IMAGES
import com.example.open_weather_app.Constants.LOCATION_FAILED
import com.example.open_weather_app.Constants.PERMISSION_DENIE
import com.example.open_weather_app.Constants.URL_END_FOR_IMAGES
import com.example.open_weather_app.Interface.RetrofitInterface
import com.example.open_weather_app.Object.RetrofitHelper
import com.example.open_weather_app.R
import com.example.open_weather_app.Recycler_Adapter.DayDisplay
import com.example.open_weather_app.Recycler_Adapter.HoursDisplay
import com.example.open_weather_app.Repository.ClassRepo
import com.example.open_weather_app.ViewModels.MainViewModel
import com.example.open_weather_app.ViewModels.MainViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import java.util.*

class HomePageActivity : AppCompatActivity() {

    lateinit var hourlyData:RecyclerView
    lateinit var horizontallayout:LinearLayoutManager
    var hoursDisplay:HoursDisplay?=null
    lateinit var daysData:RecyclerView
    var dayDisplay:DayDisplay?=null
    lateinit var mainViewModel: MainViewModel
    lateinit var tvCurrentTemperature:TextView
    lateinit var tvWeatherMain:TextView
    lateinit var tvWeatherMainDescription:TextView
    lateinit var tvFeelLike:TextView
    lateinit var tvPredictionForRain:TextView
    lateinit var tvWind:TextView
    lateinit var tvHumidity:TextView
    lateinit var tvUVindex:TextView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var tvPressure:TextView
    lateinit var tvVisiblity:TextView
    lateinit var tvLocation:TextView
    lateinit var progressbar:ProgressBar
    lateinit var layoutConstraint:ConstraintLayout
    lateinit var midLayout:ConstraintLayout
    lateinit var headerLayout:ConstraintLayout
    lateinit var ivCurrentWeather:ImageView
    lateinit var ivSearch:ImageView
    var cityName:String=""
    var stateName:String=""
    var countryName:String=""
    lateinit var tvDewPoint:TextView
    var latitude:Double=0.0;
    var longitude:Double=0.0;
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->

        if (result?.resultCode == Activity.RESULT_OK) {


        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvCurrentTemperature=findViewById(R.id.tvCurrentTemperature)
        tvFeelLike=findViewById(R.id.tvFeelsLike)
        tvWeatherMain=findViewById(R.id.tvWeatherMain)
        tvWeatherMainDescription=findViewById(R.id.tvWeatherMainDescription)
        tvPredictionForRain=findViewById(R.id.tvPredictionForRain)
        tvWind=findViewById(R.id.tvWind)
        tvUVindex=findViewById(R.id.tvUVindex)
        tvPressure=findViewById(R.id.tvPressure)
        tvVisiblity=findViewById(R.id.tvVisibility)
        tvDewPoint=findViewById(R.id.tvDewpoint)
        tvHumidity=findViewById(R.id.tvHumidity)
        ivCurrentWeather=findViewById(R.id.ivCurrentTemperature)
        layoutConstraint=findViewById(R.id.constraintlayout2)
        midLayout=findViewById(R.id.midlayout)
        headerLayout=findViewById(R.id.layoutheader)
        progressbar=findViewById(R.id.progressbar)
        tvLocation=findViewById(R.id.tvLocation)
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this)
        hourlyData=findViewById(R.id.recyclerview)
        daysData=findViewById(R.id.recyclerview2)
        ivSearch=findViewById(R.id.ivSearch)
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout)





        horizontallayout= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        daysData.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        hourlyData.layoutManager=horizontallayout
        reloadAPI()


        val refreshListener = SwipeRefreshLayout.OnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            reloadAPI()
        }
        swipeRefreshLayout.setOnRefreshListener(refreshListener);

        ivSearch.setOnClickListener {

            val intent= Intent(this@HomePageActivity,PlacesActivity::class.java)

            getContent.launch(intent)
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
    private fun getLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 569)
        }
        var locationTask: Task<Location> = fusedLocationProviderClient.lastLocation
        locationTask.addOnSuccessListener { location: Location? ->
            if (location != null)
            {
                latitude=location.latitude
                longitude=location.longitude
                getAddress(latitude,longitude)
            }
        }
        locationTask.addOnFailureListener{
            Toast.makeText(this, LOCATION_FAILED, Toast.LENGTH_SHORT).show();
        }
    }
    private fun reloadAPI()
    {
        val weatherService= RetrofitHelper.getInstance().create(RetrofitInterface::class.java)
        val repository=ClassRepo(weatherService)
        mainViewModel=ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)
        mainViewModel.weather.observe(this) {
            progressbar.visibility=View.INVISIBLE
            midLayout.visibility=View.VISIBLE
            layoutConstraint.visibility=View.VISIBLE
            tvFeelLike.visibility=View.VISIBLE
            headerLayout.visibility=View.VISIBLE
            tvCurrentTemperature.visibility=View.VISIBLE
            tvPredictionForRain.visibility=View.VISIBLE
            tvCurrentTemperature.text= getString(R.string.currentWeather,it.current.temp.toInt())
            val URLForImages= BASE_URL_FOR_IMAGES+it.current.weather[0].icon+ URL_END_FOR_IMAGES
            Glide.with(this)
                .load(URLForImages)
                .into(ivCurrentWeather)
            swipeRefreshLayout.isRefreshing = false
            tvFeelLike.text=getString(R.string.feel_like,it.current.feels_like.toInt())
            tvLocation.text=cityName+","+countryName
            tvWind.text="Wind: ${it.current.wind_speed.toInt()}m/s"
            tvHumidity.text="Humidity: ${it.current.humidity.toInt()}%"
            tvUVindex.text="UV index: ${it.current.uvi}"
            tvWeatherMainDescription.text="${it.current.weather[0].description}"
            tvPressure.text="Pressure: ${it.current.pressure.toInt()}hPa"
            val visibility=it.current.visibility/1000
            tvVisiblity.text="Visiblity: ${visibility}km"
            tvDewPoint.text="Dew point: ${it.current.visibility.toInt()}°C"
            tvWeatherMain.text="${it.current.weather[0].main}"
            hoursDisplay=HoursDisplay(this,it.hourly)
            dayDisplay=DayDisplay(this,it.daily)
            hourlyData.isHorizontalScrollBarEnabled=true
            daysData.adapter=dayDisplay
            hourlyData.adapter=hoursDisplay
        }
    }
    private fun askLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),568)
            }
            else
            {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),568)
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            getLocation();
        }
        else
            Toast.makeText(this, PERMISSION_DENIE, Toast.LENGTH_SHORT).show()
    }
    fun getAddress(lat:Double,lon:Double) {
        val geocoder=Geocoder(this, Locale.getDefault())
        val addresses:List<Address> =geocoder.getFromLocation(lat,lon,1)
        cityName=addresses[0].subLocality
        stateName=addresses[0].adminArea
        countryName=addresses[0].countryName
    }
}