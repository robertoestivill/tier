package com.robertoestivill.tier.repository.local.internal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VehicleEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "vehicleId")
    val vehicleId: String,

    @ColumnInfo(name = "hardwareId")
    val hardwareId: String,

    @ColumnInfo(name = "zoneId")
    val zoneId: String,

    @ColumnInfo(name = "resolution")
    val resolution: String?,

    @ColumnInfo(name = "resolvedBy")
    val resolvedBy: String?,

    @ColumnInfo(name = "resolvedAt")
    val resolvedAt: String?,

    @ColumnInfo(name = "battery")
    val battery: Int,

    @ColumnInfo(name = "state")
    val state: String,

    @ColumnInfo(name = "model")
    val model: String,

    @ColumnInfo(name = "fleetbirdId")
    val fleetBirdId: Long,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double
)
