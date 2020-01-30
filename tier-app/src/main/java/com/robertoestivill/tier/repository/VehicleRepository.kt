package com.robertoestivill.tier.repository

import kotlinx.coroutines.flow.Flow

interface VehicleRepository {

    suspend fun retrieveVehiclesToPickUp(
        latitude: Double,
        longitude: Double
    ): Flow<VehicleRepositoryResult>
}
