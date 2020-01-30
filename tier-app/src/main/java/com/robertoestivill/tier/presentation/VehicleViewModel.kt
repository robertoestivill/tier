package com.robertoestivill.tier.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robertoestivill.tier.model.VehicleModel
import com.robertoestivill.tier.repository.VehicleRepository
import com.robertoestivill.tier.repository.VehicleRepositoryResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class VehicleViewModel(
    private val vehicleRepository: VehicleRepository
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    val vehicles = MutableLiveData<List<VehicleModel>>()
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<VehicleRepositoryResult>()

    @ExperimentalCoroutinesApi
    fun updateVehicleStatus(latitude: Double, longitude: Double) = coroutineScope.launch {
        val vehicleResultsFlow = vehicleRepository.retrieveVehiclesToPickUp(
            latitude = latitude,
            longitude = longitude
        )

        vehicleResultsFlow
            .onStart { isLoading.value = true }
            .onCompletion { isLoading.value = false }
            .collect {
                when (it) {
                    is VehicleRepositoryResult.Success -> vehicles.value = it.vehicles
                    else -> error.value = it
                }
            }
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }
}
