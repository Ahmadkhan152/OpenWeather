package com.example.open_weather_app.Recycler_Adapter

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.open_weather_app.Constants.METRIC
import com.example.open_weather_app.Constants.MY_PREFERENCES
import com.example.open_weather_app.Constants.TIME_FORMAT
import com.example.open_weather_app.Constants.UNITS
import com.example.open_weather_app.Model.Current
import com.example.open_weather_app.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HoursDisplay(val context: Context,val hourly:ArrayList<Current>): RecyclerView.Adapter<HoursDisplay.viewHolder>() {

    class viewHolder(itemView: View):RecyclerView.ViewHolder(itemView){


        val tvTime:TextView=itemView.findViewById(R.id.tvTime)
        val ivPicture:ImageView=itemView.findViewById(R.id.ivDay)
        val tvTemperature:TextView=itemView.findViewById(R.id.tvTemperature)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var myView= LayoutInflater.from(context).inflate(R.layout.hoursdisplaylayout,parent,false)


        return viewHolder(myView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        var url="http://openweathermap.org/img/wn/${hourly[position].weather[0].icon}@2x.png"
        var hours=hourly[position].dt*1000L
        val time=getTime(hours,"hh aa")
        val strPattern:String = "^0+"
        holder.tvTime.text="${time.toString().replaceFirst("^0+(?!$)", "")}"
        //if (time.toString().contains("pm"))
        Glide.with(context)
            .load(url)
            .into(holder.ivPicture)
        val sharedPreferences: SharedPreferences =context.getSharedPreferences(MY_PREFERENCES,Context.MODE_PRIVATE)
        val unit=sharedPreferences.getString(UNITS, METRIC)
        var timetemp=sharedPreferences.getString(TIME_FORMAT,"12")
        if (timetemp=="12")
        {
            var hours=hourly[position].dt*1000L
            val time=getTime(hours,"hh aa")
            val strPattern:String = "^0+"
            holder.tvTime.text="${time.toString().replaceFirst("^0+(?!$)", "")}"
        }
        else
        {
            var hours=hourly[position].dt*1000L
            val time=getTime(hours,"HH")
            holder.tvTime.text="${time.toString().replaceFirst("^0+(?!$)", "")}:00"
        }
        if (unit== METRIC)
        {
            holder.tvTemperature.text="${hourly[position].temp.toInt()}°C"
        }
        else
        {
            holder.tvTemperature.text="${hourly[position].temp.toInt()}°F"
        }

    }

    private fun getTime(hours: Long, s: String): Any {
        val formatter = SimpleDateFormat(s)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = hours
        return formatter.format(calendar.time)
    }

    override fun getItemCount(): Int {
        return hourly.size
    }
}