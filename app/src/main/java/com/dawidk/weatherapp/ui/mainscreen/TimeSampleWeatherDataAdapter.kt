package com.dawidk.weatherapp.ui.mainscreen

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dawidk.utils.addSymbol
import com.dawidk.utils.toIcon
import com.dawidk.weatherapp.databinding.DailyWeatherItemBinding
import com.dawidk.weatherapp.databinding.HourlyWeatherItemBinding
import com.dawidk.weatherapp.repository.domain.model.SampleType
import com.dawidk.weatherapp.repository.domain.model.WeatherItem
import com.dawidk.weatherapp.repository.domain.model.WeatherItem.CurrentWeatherItem
import java.text.SimpleDateFormat
import java.util.*

private const val SAMPLE_HOURLY = 1
private const val SAMPLE_DAILY = 2

class TimeSampleWeatherDataAdapter:
    ListAdapter<CurrentWeatherItem, RecyclerView.ViewHolder>(ItemDiffCallback())
{
    private class ItemDiffCallback : DiffUtil.ItemCallback<CurrentWeatherItem>() {

        override fun areItemsTheSame(oldItem: CurrentWeatherItem, newItem: CurrentWeatherItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CurrentWeatherItem, newItem: CurrentWeatherItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            SAMPLE_HOURLY -> {
                HourlyWeatherSampleViewHolder(
                    HourlyWeatherItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    parent.context.resources
                )
            }
            else -> {
                DailyWeatherSampleViewHolder(
                    DailyWeatherItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    parent.context.resources
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (item.sampleType) {
            SampleType.HOURLY -> (holder as HourlyWeatherSampleViewHolder).applyItem(item)
            else -> (holder as DailyWeatherSampleViewHolder).applyItem(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).sampleType) {
            SampleType.HOURLY -> SAMPLE_HOURLY
            SampleType.DAILY -> SAMPLE_DAILY
            else -> throw IllegalArgumentException("Invalid type of data")
        }
    }

    inner class HourlyWeatherSampleViewHolder(
        private val binding: HourlyWeatherItemBinding,
        private val resources: Resources
    ): RecyclerView.ViewHolder(binding.root) {
        fun applyItem(currentWeatherItem: CurrentWeatherItem){
            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val hour = currentWeatherItem.time?.let {
                SimpleDateFormat("HH").format(Date(it * 1000))
            }?: ""
            binding.timeTv.text = if(hour.toInt() == currentHour) "Now" else hour
            binding.iconIv.setImageDrawable(currentWeatherItem.icon?.toIcon(resources))

            binding.currentTempTv.text =
                (currentWeatherItem.temperature)?.toInt().toString().addSymbol(Typography.degree)
        }
    }

    inner class DailyWeatherSampleViewHolder(
        private val binding: DailyWeatherItemBinding,
        private val resources: Resources
    ): RecyclerView.ViewHolder(binding.root) {
        fun applyItem(currentWeatherItem: CurrentWeatherItem){
            binding.iconIv.setImageDrawable(currentWeatherItem.icon?.toIcon(resources))

            binding.nameTv.text = "${(currentWeatherItem.apparentTemperature)?.toInt()} ${Typography.degree}"
        }
    }
}