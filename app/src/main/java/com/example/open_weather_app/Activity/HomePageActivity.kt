package com.example.open_weather_app.Activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationRequest
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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
import com.example.open_weather_app.Constants.*
import com.example.open_weather_app.Interface.RetrofitInterface
import com.example.open_weather_app.Model.WeatherData
import com.example.open_weather_app.Object.RetrofitHelper
import com.example.open_weather_app.R
import com.example.open_weather_app.Receiver.MyReceiver
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
    lateinit var tvNoInternetConnection:TextView
    lateinit var tvCheckBackText:TextView
    lateinit var repository:ClassRepo
    lateinit var weatherService:RetrofitInterface
    lateinit var btnTryAgain:Button
    lateinit var tvVisiblity:TextView
    lateinit var tvLocation:TextView
    lateinit var progressbar:ProgressBar
    lateinit var layoutConstraint:ConstraintLayout
    lateinit var midLayout:ConstraintLayout
    lateinit var headerLayout:ConstraintLayout
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var ivCurrentWeather:ImageView
    lateinit var filter:IntentFilter
    lateinit var receiver: MyReceiver
    lateinit var it:WeatherData
    var wind:String=""
    var distance:Float=0.0f
    lateinit var btnMenu:ImageView
    lateinit var ivSearch:ImageView
    var cityName:String=""
    var units:String=""
    var pressure:Float=0.0f
    var stateName:String=""
    var countryName:String=""
    var time:String=""
    lateinit var tvDewPoint:TextView
    var latitude:Double=0.0;
    var longitude:Double=0.0;
    var checkInternetConnection:Int=0
    var address:String=""
    val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->

        if (result?.resultCode == Activity.RESULT_OK) {


        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences=getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE)
        it=intent.getSerializableExtra(OBJECT) as WeatherData
        units= sharedPreferences.getString(UNITS, METRIC).toString()
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
        hourlyData=findViewById(R.id.recyclerview)
        daysData=findViewById(R.id.recyclerview2)
        ivSearch=findViewById(R.id.ivSearch)
        btnMenu=findViewById(R.id.btnMenu)
        tvNoInternetConnection=findViewById(R.id.tvNoInternetConnection)
        tvCheckBackText=findViewById(R.id.tvCheckBackText)
        btnTryAgain=findViewById(R.id.btnTryAgain)
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout)

        address=intent.getStringExtra(ADDRESS).toString()
        horizontallayout= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        daysData.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        hourlyData.layoutManager=horizontallayout
        checkInternetConnection=getConnectionType(this)
        filter = IntentFilter()
        filter.addAction(BROAD_CAST_PACKAGE_NAME)
        receiver = MyReceiver(this)
        registerReceiver(receiver, filter)
        allData()
        weatherService= RetrofitHelper.getInstance().create(RetrofitInterface::class.java)
        repository=ClassRepo(weatherService)
        mainViewModel=ViewModelProvider(this,MainViewModelFactory(repository,31.5194,74.3228,units)).get(MainViewModel::class.java)
        if (checkInternetConnection==0){

            progressbar.visibility=View.INVISIBLE
            tvCheckBackText.visibility=View.VISIBLE
            tvNoInternetConnection.visibility=View.VISIBLE
            btnTryAgain.visibility=View.VISIBLE
        }
        else
        {
            reloadAPI()
        }
        btnMenu.setOnClickListener {

            val intent=Intent(this@HomePageActivity,MenuActivity::class.java)
            startActivity(intent)
        }
        btnTryAgain.setOnClickListener {
            checkInternetConnection=getConnectionType(this)
            if (checkInternetConnection> NO_CONNECTION){
                //progressbar.visibility=View.VISIBLE
                tvCheckBackText.visibility=View.INVISIBLE
                tvNoInternetConnection.visibility=View.INVISIBLE
                btnTryAgain.visibility=View.INVISIBLE
                mainViewModel.initData(units)
            }

        }
        val refreshListener = SwipeRefreshLayout.OnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            checkInternetConnection=getConnectionType(this)
            if (checkInternetConnection>0){

                mainViewModel.initData(units)
            }
            else
            {
                midLayout.visibility=View.INVISIBLE
                layoutConstraint.visibility=View.INVISIBLE
                tvFeelLike.visibility=View.INVISIBLE
                headerLayout.visibility=View.INVISIBLE
                tvCurrentTemperature.visibility=View.INVISIBLE
                tvPredictionForRain.visibility=View.INVISIBLE
                daysData.visibility=View.INVISIBLE
                hourlyData.visibility=View.INVISIBLE
                tvCheckBackText.visibility=View.VISIBLE
                tvNoInternetConnection.visibility=View.VISIBLE
                btnTryAgain.visibility=View.VISIBLE
                swipeRefreshLayout.isRefreshing=false
            }
        }
        swipeRefreshLayout.setOnRefreshListener(refreshListener);

        ivSearch.setOnClickListener {

            val intent= Intent(this@HomePageActivity,PlacesActivity::class.java)

            getContent.launch(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)

    }
    override fun onStart() {
        super.onStart()
    }
    private fun getConnectionType(context: Context): Int {
        var result = NO_CONNECTION
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = WIFI_ENABLE
                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = MOBILE_DATA_ENABLE
                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_VPN)){
                        result = VPN_ENABLE
                    }
                }
            }
        }
        return result
    }
    fun allData()
    {
        units= sharedPreferences.getString(UNITS, METRIC).toString()
        time= sharedPreferences.getString(TIME_FORMAT, "12").toString()
        progressbar.visibility=View.INVISIBLE
        midLayout.visibility=View.VISIBLE
        layoutConstraint.visibility=View.VISIBLE
        tvFeelLike.visibility=View.VISIBLE
        headerLayout.visibility=View.VISIBLE
        tvCurrentTemperature.visibility=View.VISIBLE
        tvPredictionForRain.visibility=View.VISIBLE
        tvCheckBackText.visibility=View.INVISIBLE
        tvNoInternetConnection.visibility=View.INVISIBLE
        btnTryAgain.visibility=View.INVISIBLE
        daysData.visibility=View.VISIBLE
        hourlyData.visibility=View.VISIBLE
        if (units == METRIC)
        {
            tvCurrentTemperature.text= getString(R.string.currentWeatherC,it.current.temp.toInt())
            tvDewPoint.text="Dew point: ${it.current.visibility.toInt()}°C"
        }
        else
        {
            tvCurrentTemperature.text= getString(R.string.currentWeatherF,it.current.temp.toInt())
            tvDewPoint.text="Dew point: ${it.current.visibility.toInt()}°F"
        }
        val URLForImages= BASE_URL_FOR_IMAGES+it.current.weather[0].icon+ URL_END_FOR_IMAGES
        Glide.with(this)
            .load(URLForImages)
            .into(ivCurrentWeather)
        swipeRefreshLayout.isRefreshing = false
        tvFeelLike.text=getString(R.string.feel_like,it.current.feels_like.toInt())
        tvLocation.text=address
        tvWind.text="Wind: ${it.current.wind_speed.toInt()}m/s"
        wind="${it.current.wind_speed.toInt()}"
        tvHumidity.text="Humidity: ${it.current.humidity.toInt()}%"
        tvUVindex.text="UV index: ${it.current.uvi}"
        tvWeatherMainDescription.text="${it.current.weather[0].description}"
        tvPressure.text="Pressure: ${it.current.pressure.toInt()}hPa"
        pressure=it.current.pressure.toString().toFloat()
        val visibility=it.current.visibility/1000
        tvVisiblity.text="Visiblity: ${visibility}km"
        distance=visibility.toFloat()
        tvWeatherMain.text="${it.current.weather[0].main}"
        hoursDisplay=HoursDisplay(this,it.hourly)
        dayDisplay=DayDisplay(this,it.daily)
        hourlyData.isHorizontalScrollBarEnabled=true
        daysData.adapter=dayDisplay
        hourlyData.adapter=hoursDisplay
    }
    private fun reloadAPI()
    {
        mainViewModel.weather.observe(this) {
            allData()
        }
    }
}