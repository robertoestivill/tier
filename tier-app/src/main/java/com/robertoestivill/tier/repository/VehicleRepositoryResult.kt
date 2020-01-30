package com.robertoestivill.tier.repository

import com.robertoestivill.tier.model.VehicleModel

sealed class VehicleRepositoryResult {

    data class Success(val vehicles: List<VehicleModel>) : VehicleRepositoryResult()

    object IOError : VehicleRepositoryResult()

    object UnknownError : VehicleRepositoryResult()

    object Unavailable : VehicleRepositoryResult()
}
