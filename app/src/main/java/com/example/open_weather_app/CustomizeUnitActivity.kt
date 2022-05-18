package com.example.open_weather_app

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.open_weather_app.Constants.*

class CustomizeUnitActivity : AppCompatActivity() {


    lateinit var radioGroupTemperature: RadioGroup
    lateinit var radioGroupWind: RadioGroup
    lateinit var radioGroupPressure: RadioGroup
    lateinit var radioGroupDistance: RadioGroup
    lateinit var radioGroupTimeFormat: RadioGroup
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var btnRadioButtonForhenhiet: RadioButton
    lateinit var btnRadioButtonCelius: RadioButton
    lateinit var btnRadioButtonms: RadioButton
    lateinit var btnRadioButtonhPa: RadioButton
    lateinit var btnRadioButtonFullDay: RadioButton
    lateinit var btnRadioButtonHalfDay: RadioButton
    lateinit var btnRadioButtonPrecipitation: RadioButton
    lateinit var btnRadioButtoninHg: RadioButton
    lateinit var btnRadioButtonkm: RadioButton
    lateinit var btnRadioButtonmi: RadioButton
    lateinit var btnRadioButtonkmh: RadioButton
    lateinit var btnRadioButtonmph: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customize_unit)

        val toolbar:androidx.appcompat.widget.Toolbar= findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar=supportActionBar
        actionbar?.title= UNITS_ACTIVITY
        actionbar?.setDisplayHomeAsUpEnabled(true)
        sharedPreferences=getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE)
        radioGroupTemperature=findViewById(R.id.radiogroupTemperature)
        radioGroupWind=findViewById(R.id.radiogroupWind)
        radioGroupDistance=findViewById(R.id.radiogroupDistance)
        radioGroupPressure=findViewById(R.id.radiogroupPressure)
        btnRadioButtonForhenhiet=findViewById(R.id.btnFerenhiet)
        btnRadioButtonCelius=findViewById(R.id.btnCelius)
        btnRadioButtonms=findViewById(R.id.btnms)
        btnRadioButtonkm=findViewById(R.id.btnkm)
        btnRadioButtonmi=findViewById(R.id.btnmi)
        btnRadioButtonhPa=findViewById(R.id.btnhPa)
        btnRadioButtoninHg=findViewById(R.id.btninHg)
        btnRadioButtonkmh=findViewById(R.id.btnkmh)
        btnRadioButtonmph=findViewById(R.id.btnmph)
        radioGroupTimeFormat=findViewById(R.id.radiogroupTimeFormat)
        btnRadioButtonHalfDay=findViewById(R.id.btn12)
        btnRadioButtonFullDay=findViewById(R.id.btn24)
        btnRadioButtonPrecipitation=findViewById(R.id.btnmm)
        editor=sharedPreferences.edit()
        val distance=sharedPreferences.getString(DISTANCE, "km").toString()
        val windSpeed=sharedPreferences.getString(WIND_SPEED, "km/h").toString()
        val pressure=sharedPreferences.getString(PRESSURE, "hPa").toString()
        val units=sharedPreferences.getString(UNITS, METRIC).toString()
        val time=sharedPreferences.getString(TIME_FORMAT, "12").toString()
        btnRadioButtonPrecipitation.isChecked=true
        if (units == IMPERIAL)
        {
            btnRadioButtonForhenhiet.isChecked=true
        }
        else
        {
            btnRadioButtonCelius.isChecked=true
        }
        if (distance=="mi")
        {
            btnRadioButtonmi.isChecked=true
        }
        else
        {
            btnRadioButtonkm.isChecked=true
        }
        if (windSpeed=="m/s")
        {
            btnRadioButtonms.isChecked=true
        }
        else if(windSpeed=="mph")
        {
            btnRadioButtonmph.isChecked=true
        }
        else
        {
            btnRadioButtonkmh.isChecked=true
        }
        if (pressure=="inHg")
        {
            btnRadioButtoninHg.isChecked=true
        }
        else
        {
            btnRadioButtonhPa.isChecked=true
        }
        if(time=="24")
        {
            btnRadioButtonFullDay.isChecked=true
        }
        else
        {
            btnRadioButtonHalfDay.isChecked=true
        }
        radioGroupDistance.setOnCheckedChangeListener { radioGroup, i ->
            if(i==R.id.btnkm)
            {
                val intent=Intent()
                intent.putExtra(CHECK_FOR_DISTANCE,true)
                intent.putExtra(CHECK_FOR_KILO_METER,true)
                intent.action=BROAD_CAST_PACKAGE_NAME
                editor.remove(DISTANCE)
                editor.putString(DISTANCE, "km").commit()
                sendBroadcast(intent)
            }
            else
            {
                val intent=Intent()
                intent.putExtra(CHECK_FOR_DISTANCE,true)
                intent.putExtra(CHECK_FOR_METER_I,true)
                intent.action=BROAD_CAST_PACKAGE_NAME
                editor.remove(DISTANCE)
                editor.putString(DISTANCE, "mi")
                editor.commit()
                sendBroadcast(intent)
            }
        }
        radioGroupWind.setOnCheckedChangeListener { radioGroup, i ->

            if (i==R.id.btnms)
            {
                val intent=Intent()
                intent.putExtra(CHECK_FOR_WIND,true)
                intent.putExtra(CHECK_FOR_METER_PER_SECOND,true)
                intent.action=BROAD_CAST_PACKAGE_NAME
                editor.putString(WIND_SPEED, "m/s")
                editor.commit()
                sendBroadcast(intent)
            }
            else if(i==R.id.btnkmh){

                val intent=Intent()
                intent.putExtra(CHECK_FOR_WIND,true)
                intent.putExtra(CHECK_FOR_KILO_METER_PER_HOUR,true)
                intent.action=BROAD_CAST_PACKAGE_NAME
                editor.putString(WIND_SPEED, "km/h")
                editor.commit()
                sendBroadcast(intent)
            }
            else
            {
                val intent=Intent()
                intent.putExtra(CHECK_FOR_WIND,true)
                intent.putExtra(CHECK_FOR_METER_PER_HOUR,true)
                intent.action=BROAD_CAST_PACKAGE_NAME
                editor.putString(WIND_SPEED, "mph")
                editor.commit()
                sendBroadcast(intent)
            }

        }

        radioGroupPressure.setOnCheckedChangeListener { radioGroup, i ->
            if (i==R.id.btnhPa)
            {
                val intent=Intent()
                intent.putExtra(CHECK_FOR_PRESSURE,true)
                intent.putExtra(CHECK_FOR_HPA,true)
                intent.action=BROAD_CAST_PACKAGE_NAME
                editor.putString(PRESSURE, "hPa")
                editor.commit()
                sendBroadcast(intent)
            }
            else{
                val intent=Intent()
                intent.putExtra(CHECK_FOR_PRESSURE,true)
                intent.putExtra(CHECK_FOR_INHG,true)
                intent.action=BROAD_CAST_PACKAGE_NAME
                editor.putString(PRESSURE, "inHg")
                editor.commit()
                sendBroadcast(intent)
            }
        }
        radioGroupTemperature.setOnCheckedChangeListener { radioGroup, i ->
            if (i==R.id.btnFerenhiet)
            {
                val intent=Intent()
                intent.putExtra(CHECK_FOR_TEMPERATURE,true)
                intent.putExtra(CHECK_FOR_FORENHEIT,true)
                intent.action=BROAD_CAST_PACKAGE_NAME
                editor.putString(UNITS, IMPERIAL).commit()
                sendBroadcast(intent)
            }
            else
            {
                val intent=Intent()
                intent.putExtra(CHECK_FOR_TEMPERATURE,true)
                intent.putExtra(CHECK_FOR_CELIUS,true)
                intent.action = BROAD_CAST_PACKAGE_NAME
                editor.putString(UNITS, METRIC).commit()
                sendBroadcast(intent)
            }
        }
        radioGroupTimeFormat.setOnCheckedChangeListener { radioGroup, i ->
            if (i==R.id.btn24)
            {
                val intent=Intent()
                intent.putExtra(CHECK_FOR_TIME_FORMAT,true)
                intent.putExtra(CHECK_FOR_24_HOUR,true)
                intent.action = BROAD_CAST_PACKAGE_NAME
                editor.putString(TIME_FORMAT, "24").commit()
                sendBroadcast(intent)
            }
            else
            {
                val intent=Intent()
                intent.putExtra(CHECK_FOR_TIME_FORMAT,true)
                intent.putExtra(CHECK_FOR_12_HOUR,true)
                intent.action = BROAD_CAST_PACKAGE_NAME
                editor.putString(TIME_FORMAT, "12").commit()
                sendBroadcast(intent)
            }
        }
    }
}