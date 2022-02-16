package com.dawidk.weatherapp.ui.mainscreen

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dawidk.utils.addSymbol
import com.dawidk.utils.toIcon
import com.dawidk.weatherapp.R
import com.dawidk.weatherapp.databinding.CurrentWeatherItemBinding
import com.dawidk.weatherapp.databinding.TimeSampleWeatherItemBinding
import com.dawidk.weatherapp.repository.domain.model.SampleType
import com.dawidk.weatherapp.repository.domain.model.WeatherItem

private const val TYPE_CURRENT = 1
private const val TYPE_TIME_SAMPLE = 2

class WeatherItemsAdapter: ListAdapter<WeatherItem, RecyclerView.ViewHolder>(ItemDiffCallback()) {

    private var viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    private class ItemDiffCallback : DiffUtil.ItemCallback<WeatherItem>() {

        override fun areItemsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean {
            return when (oldItem) {
                is WeatherItem.CurrentWeatherItem -> oldItem == newItem
                is WeatherItem.TimeSamplesWeatherItem -> oldItem == newItem
            }
        }

        override fun areContentsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean {
            return when (oldItem) {
                is WeatherItem.CurrentWeatherItem -> oldItem == newItem
                is WeatherItem.TimeSamplesWeatherItem -> oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is WeatherItem.CurrentWeatherItem -> TYPE_CURRENT
            is WeatherItem.TimeSamplesWeatherItem -> TYPE_TIME_SAMPLE
            else -> throw IllegalArgumentException("Invalid type of data")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CURRENT -> {
                CurrentWeatherViewHolder(
                    CurrentWeatherItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    parent.context.resources
                )
            }
            else -> {
                TimeSampleWeatherDataViewHolder(
                    TimeSampleWeatherItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is WeatherItem.CurrentWeatherItem -> {
                (holder as CurrentWeatherViewHolder).applyItem(item)
            }
            is WeatherItem.TimeSamplesWeatherItem -> {
                (holder as TimeSampleWeatherDataViewHolder).applyItem(item)
            }
        }
    }

    inner class CurrentWeatherViewHolder(
        private val binding: CurrentWeatherItemBinding,
        private val resources: Resources
    ): RecyclerView.ViewHolder(binding.root){
        fun applyItem(currentWeatherItem: WeatherItem.CurrentWeatherItem){
            Log.d("WeatherAdapter", "CurrentWeather")
            binding.currentTempTv.text =
                (currentWeatherItem.temperature)?.toInt().toString().addSymbol(Typography.degree)
            binding.iconIv.setImageDrawable(currentWeatherItem.icon?.toIcon(resources))
            binding.summaryTv.text = currentWeatherItem.summary
        }
    }

    inner class TimeSampleWeatherDataViewHolder(
        private val binding: TimeSampleWeatherItemBinding
    ): RecyclerView.ViewHolder(binding.root){

        private val timeSampleAdapter = TimeSampleWeatherDataAdapter()

        fun applyItem(timeSamplesWeatherItem: WeatherItem.TimeSamplesWeatherItem){
            Log.d("WeatherAdapter", "TimeSampleWeather")
            binding.hourlyWeatherRv.apply {
                adapter = timeSampleAdapter
                layoutManager = if(timeSamplesWeatherItem.weatherDetails.first().sampleType == SampleType.HOURLY){
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                }else{
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
                setRecycledViewPool(viewPool)
            }
            timeSampleAdapter.submitList(timeSamplesWeatherItem.weatherDetails)
        }
    }
}