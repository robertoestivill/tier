package com.robertoestivill.tier.repository

import com.robertoestivill.tier.repository.local.VehicleLocalDataSource
import com.robertoestivill.tier.repository.remote.VehicleRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class VehicleRepositoryImpl(
    private val remoteDataSource: VehicleRemoteDataSource,
    private val localDataSource: VehicleLocalDataSource
) : VehicleRepository {

    override suspend fun retrieveVehiclesToPickUp(
        latitude: Double,
        longitude: Double
    ): Flow<VehicleRepositoryResult> = flow {

        val localResult = localDataSource.retrieveVehiclesToPickup()
        if (localResult is VehicleRepositoryResult.Success && localResult.vehicles.isNotEmpty()) {
            emit(localResult)
        }

        val remoteResult = remoteDataSource.retrieveVehicles(
            latitude = latitude,
            longitude = longitude
        )
        emit(remoteResult)

        if (remoteResult is VehicleRepositoryResult.Success && remoteResult.vehicles.isNotEmpty()) {
            localDataSource.saveAll(remoteResult.vehicles)
        }
    }
}
