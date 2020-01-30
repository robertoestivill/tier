package com.robertoestivill.tier.repository.remote.internal

import com.robertoestivill.tier.model.VehicleModel

internal fun VehicleInfo.toModel() = VehicleModel(
    id = id,
    vehicleId = vehicleId,
    hardwareId = hardwareId,
    zoneId = zoneId,
    resolution = resolution,
    resolvedBy = resolvedBy,
    resolvedAt = resolvedAt,
    battery = battery,
    state = state,
    model = model,
    fleetBirdId = fleetBirdId,
    latitude = latitude,
    longitude = longitude
)
