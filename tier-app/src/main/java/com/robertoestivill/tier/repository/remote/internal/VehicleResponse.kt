package com.robertoestivill.tier.repository.remote.internal

import com.google.gson.annotations.SerializedName

data class VehicleResponse(

    @SerializedName("data")
    val current: VehicleCollection
)
