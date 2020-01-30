package com.robertoestivill.tier.repository.remote.internal

import com.google.gson.annotations.SerializedName

data class VehicleInfo(

    @SerializedName("id")
    val id: String,

    @SerializedName("vehicleId")
    val vehicleId: String,

    @SerializedName("hardwareId")
    val hardwareId: String,

    @SerializedName("zoneId")
    val zoneId: String,

    @SerializedName("resolution")
    val resolution: String?,

    @SerializedName("resolvedBy")
    val resolvedBy: String?,

    @SerializedName("resolvedAt")
    val resolvedAt: String?,

    @SerializedName("battery")
    val battery: Int,

    @SerializedName("state")
    val state: String,

    @SerializedName("model")
    val model: String,

    @SerializedName("fleetbirdId")
    val fleetBirdId: Long,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double
)
