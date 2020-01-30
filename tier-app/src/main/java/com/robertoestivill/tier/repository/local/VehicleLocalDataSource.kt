package com.robertoestivill.tier.repository.local

import com.robertoestivill.tier.model.VehicleModel
import com.robertoestivill.tier.repository.VehicleRepositoryResult

interface VehicleLocalDataSource {

    suspend fun retrieveVehiclesToPickup(): VehicleRepositoryResult

    suspend fun saveAll(vehicles: List<VehicleModel>)
}
