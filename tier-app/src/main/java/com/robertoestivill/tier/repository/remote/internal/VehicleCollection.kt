package com.robertoestivill.tier.repository.remote.internal

import com.google.gson.annotations.SerializedName

data class VehicleCollection(

    @SerializedName("current")
    val locations: List<VehicleInfo>
)
