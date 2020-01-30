package com.robertoestivill.tier.repository.local

import com.robertoestivill.tier.model.VehicleModel
import com.robertoestivill.tier.repository.VehicleRepositoryResult
import com.robertoestivill.tier.repository.local.internal.VehicleDao
import com.robertoestivill.tier.repository.local.internal.toEntity
import com.robertoestivill.tier.repository.local.internal.toModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class VehicleLocalDataSourceImpl(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val vehicleDao: VehicleDao
) : VehicleLocalDataSource {

    override suspend fun retrieveVehiclesToPickup() = withContext(coroutineDispatcher) {
        val entities = vehicleDao.retrieveInState(state = VehicleModel.State.TO_PICKUP.name)
        if (entities.isEmpty()) {
            VehicleRepositoryResult.Unavailable
        } else {
            VehicleRepositoryResult.Success(entities.map { it.toModel() })
        }
    }

    override suspend fun saveAll(vehicles: List<VehicleModel>) = withContext(coroutineDispatcher) {
        vehicleDao.insertAll(vehicles.map { it.toEntity() })
    }
}
