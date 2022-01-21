package com.dawidk.weatherapp.ui.mainscreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import com.google.android.gms.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dawidk.utils.removeSmallerFromBigger
import com.dawidk.weatherapp.databinding.ActivityMainBinding
import com.dawidk.weatherapp.repository.domain.model.WeatherData
import com.dawidk.weatherapp.repository.network.model.WeatherDataDto
import com.dawidk.weatherapp.repository.util.Resource
import com.dawidk.weatherapp.ui.mainscreen.state.MainAction
import com.dawidk.weatherapp.ui.mainscreen.state.MainState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity(), LocationListener {

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = 30
        fastestInterval = 10
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        maxWaitTime = 60
    }
    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                onLocationChanged(locationList.last())
            }
        }
    }
    private var mLastLocation: Location? = null // TODO: Do I need that?
    private var adapter: WeatherItemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = WeatherItemsAdapter()
        binding.weatherRv.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(
                this@MainActivity,
            )
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        registerStateListener()
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter = null
    }

    private fun registerStateListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is MainState.Loading -> showLoading()
                        is MainState.DataLoaded -> {
                            hideLoading()
                            with(it.weatherDataResponse) {
                                when (this) {
                                    is Resource.Success -> {
                                        adapter?.submitList(data)
                                    }
                                    is Resource.Error -> {
                                        showError(message)
                                    }
                                    is Resource.Loading -> {}
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun displayWeather(weatherData: WeatherData) {

    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

    private fun hideLoading() {
        binding.progressBar.isVisible = false
    }

    private fun showError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        if (isPermissionsGranted()) {
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private fun stopLocationUpdates() {
        if (isPermissionsGranted()) {
            fusedLocationClient?.removeLocationUpdates(locationCallback)
        }
    }

    private fun isPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onLocationChanged(location: Location) {
        updateCurrentPosition(location)
    }

    private fun updateCurrentPosition(location: Location) {
        if(mLastLocation != null) {
            mLastLocation?.let {
                if (it.latitude.removeSmallerFromBigger(location.latitude) > 0.5 &&
                    it.longitude.removeSmallerFromBigger(location.longitude) > 0.5
                ) {
                    viewModel.onAction(MainAction.LoadLocationWeather(it.latitude, it.longitude))
                }
            }
        } else {
            viewModel.onAction(MainAction.LoadLocationWeather(location.latitude, location.longitude))
        }
        mLastLocation = location
    }
}