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
import com.example.open_weather_app.Constants.UNITS
import com.example.open_weather_app.Model.Daily
import com.example.open_weather_app.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DayDisplay (val context: Context,val daily:ArrayList<Daily>): RecyclerView.Adapter<DayDisplay.viewHolder>() {

    class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvDay: TextView =itemView.findViewById(R.id.tvDay)
        val ivPicture: ImageView =itemView.findViewById(R.id.ivDay)
        val tvTemperature: TextView =itemView.findViewById(R.id.tvTemperature)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var myView= LayoutInflater.from(context).inflate(R.layout.daydisplaylayout,parent,false)


        return viewHolder(myView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var url="http://openweathermap.org/img/wn/${daily[position].weather[0].icon}@2x.png"
        var hours=daily[position].dt*1000L
        val day=getTime(hours,"EEEE")
        val month=getTime(hours,"MMMM")
        val date=getTime(hours,"d")
        holder.tvDay.text="${day} ${month} ${date}"
        Glide.with(context)
            .load(url)
            .into(holder.ivPicture)
        val sharedPreferences:SharedPreferences=context.getSharedPreferences(MY_PREFERENCES,Context.MODE_PRIVATE)
        val unit=sharedPreferences.getString(UNITS, METRIC)
        if (unit== METRIC)
        {
            holder.tvTemperature.text="${daily[position].temp.max.toInt()} / ${daily[position].temp.min.toInt()} °C "
        }
        else
        {
            holder.tvTemperature.text="${daily[position].temp.max.toInt()} / ${daily[position].temp.min.toInt()} °F "

        }
    }
    private fun getTime(hours: Long, s: String): Any {
        val formatter = SimpleDateFormat(s)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = hours
        return formatter.format(calendar.time)
    }
    override fun getItemCount(): Int {
        return daily.size
    }
}