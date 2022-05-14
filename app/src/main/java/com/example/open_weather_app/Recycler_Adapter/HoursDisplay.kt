package com.example.open_weather_app.Recycler_Adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        holder.tvTemperature.text="${hourly[position].temp.toInt()}°C"


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