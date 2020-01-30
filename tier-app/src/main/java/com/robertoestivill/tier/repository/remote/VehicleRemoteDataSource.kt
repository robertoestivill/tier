package com.robertoestivill.tier.repository.remote

import com.robertoestivill.tier.repository.VehicleRepositoryResult

interface VehicleRemoteDataSource {

    suspend fun retrieveVehicles(
        latitude: Double,
        longitude: Double
    ): VehicleRepositoryResult
}
