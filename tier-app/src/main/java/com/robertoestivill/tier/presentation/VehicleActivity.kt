package com.robertoestivill.tier.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngBounds
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.robertoestivill.tier.di.vehicleModule
import com.robertoestivill.tier.model.VehicleModel
import com.robertoestivill.tier.R
import com.robertoestivill.tier.repository.VehicleRepositoryResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

@ExperimentalCoroutinesApi
class VehicleActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())
    private val vehicleViewModel: VehicleViewModel by viewModel()

    private val widgetUupdateMap by lazy { findViewById<Button>(R.id.vehicle_update_button) }
    private val widgetIsLoading by lazy { findViewById<View>(R.id.vehicle_loading) }
    private val widgetErrorMessage by lazy { findViewById<TextView>(R.id.vehicle_error) }

    private val mapMarkerList = mutableListOf<Marker>()
    private val mapMarkerBitmap by lazy { fromResource(R.drawable.map_marker) }
    private val mapFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.vehicle_map) as SupportMapFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(vehicleModule)
        setContentView(R.layout.activity_vehicles)

        widgetUupdateMap.setOnClickListener { requestUpdate() }

        vehicleViewModel.vehicles.observe(this, Observer { updateMapWithVehicles(it) })
        vehicleViewModel.isLoading.observe(this, Observer { updateLoading(it) })
        vehicleViewModel.error.observe(this, Observer { updateError(it) })

        requestUpdate()
    }

    private fun updateError(result: VehicleRepositoryResult) = coroutineScope.launch {
        widgetErrorMessage.visibility = View.VISIBLE
        widgetErrorMessage.text = when (result) {
            is VehicleRepositoryResult.Unavailable -> getString(R.string.error_unavailable)
            is VehicleRepositoryResult.IOError -> getString(R.string.error_io)
            is VehicleRepositoryResult.UnknownError -> getString(R.string.error_unknown)
            else -> getString(R.string.error_unknown)
        }
        delay(ERROR_DISPLAY_TIME_MS)
        widgetErrorMessage.visibility = View.GONE
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        unloadKoinModules(vehicleModule)
        super.onDestroy()
    }

    private fun requestUpdate() = afterMapReady { map ->
        val latitude = map.cameraPosition.target.latitude
        val longitude = map.cameraPosition.target.longitude
        vehicleViewModel.updateVehicleStatus(latitude, longitude)
    }

    private fun updateLoading(isLoading: Boolean) {
        widgetIsLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun updateMapWithVehicles(vehicles: List<VehicleModel>) = afterMapReady { map ->
        mapMarkerList.forEach { it.remove() }
        mapMarkerList.clear()

        val boundsBuilder = LatLngBounds.Builder()
        vehicles.forEach { vehicle ->

            val vehicleLatLng = LatLng(vehicle.latitude, vehicle.longitude)
                .also { boundsBuilder.include(it) }

            val newMarker = MarkerOptions().position(vehicleLatLng).icon(mapMarkerBitmap)
            mapMarkerList.add(map.addMarker(newMarker))
        }

        map.animateCamera(newLatLngBounds(boundsBuilder.build(), DEFAULT_MAP_PADDING))
    }

    private fun afterMapReady(block: (GoogleMap) -> Unit) = coroutineScope.launch {
        suspendCancellableCoroutine {
            mapFragment.getMapAsync { block(it) }
        }
    }

    companion object {
        private const val DEFAULT_MAP_PADDING = 150
        private const val ERROR_DISPLAY_TIME_MS = 4000L
    }
}
