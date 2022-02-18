package com.dawidk.weatherapp.ui.mainscreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dawidk.mvi.MviFragment
import com.dawidk.utils.removeSmallerFromBigger
import com.dawidk.weatherapp.databinding.FragmentWeatherBinding
import com.dawidk.weatherapp.repository.util.Resource
import com.dawidk.weatherapp.ui.mainscreen.state.MainEvent
import com.dawidk.weatherapp.ui.mainscreen.state.MainIntent
import com.dawidk.weatherapp.ui.mainscreen.state.MainState
import com.google.android.gms.location.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : MviFragment<MainState, MainIntent, MainEvent, WeatherViewModel>(),
    LocationListener {

    override val viewModel by viewModel<WeatherViewModel>()
    private lateinit var binding: FragmentWeatherBinding
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
    private var mLastLocation: Location? = null
    private var adapter: WeatherItemsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = WeatherItemsAdapter()
        binding.weatherRv.apply {
            adapter = this@WeatherFragment.adapter
            layoutManager = LinearLayoutManager(
                requireContext(),
            )
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        registerOnClickListener()
        initializeBaseObservers()
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

    private fun registerOnClickListener() {
        binding.aboutBtn.setOnClickListener {
            postIntent(MainIntent.NavigateToAboutScreen)
        }
    }

    override fun registerStateListener(viewState: MainState) {
        when (viewState) {
            is MainState.Loading -> showLoading()
            is MainState.DataLoaded -> {
                hideLoading()
                with(viewState.weatherDataResponse) {
                    when (this) {
                        is Resource.Success -> {
                            adapter?.submitList(data)
                        }
                        is Resource.Error -> {
                            showError(message)
                        }
                    }
                }
            }
        }
    }

    override fun registerEventListener(viewEvent: MainEvent) {
        when (viewEvent) {
            is MainEvent.NavigateToAboutScreen -> navigateToAboutScreen()
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

    private fun hideLoading() {
        binding.progressBar.isVisible = false
    }

    private fun showError(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToAboutScreen() {
        findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToAboutFragment())
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
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onLocationChanged(location: Location) {
        updateCurrentPosition(location)
    }

    private fun updateCurrentPosition(location: Location) {
        if (mLastLocation != null) {
            mLastLocation?.let {
                if (it.latitude.removeSmallerFromBigger(location.latitude) > 0.5 &&
                    it.longitude.removeSmallerFromBigger(location.longitude) > 0.5
                ) {
                    postIntent(MainIntent.LoadLocationWeather(it.latitude, it.longitude))
                }
            }
        } else {
            postIntent(MainIntent.LoadLocationWeather(location.latitude, location.longitude))
        }
        mLastLocation = location
    }
}
