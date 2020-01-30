package com.robertoestivill.tier.model

data class VehicleModel(
    val id: String,
    val vehicleId: String,
    val hardwareId: String,
    val zoneId: String,
    val resolution: String?,
    val resolvedBy: String?,
    val resolvedAt: String?,
    val battery: Int,
    val state: String,
    val model: String,
    val fleetBirdId: Long,
    val latitude: Double,
    val longitude: Double
) {
    enum class State {
        TO_PICKUP,
        OTHER_STATE
    }
}
