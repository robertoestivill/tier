package com.robertoestivill.tier.repository.local.internal

import com.robertoestivill.tier.model.VehicleModel

internal fun VehicleEntity.toModel() = VehicleModel(
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

internal fun VehicleModel.toEntity() = VehicleEntity(
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
