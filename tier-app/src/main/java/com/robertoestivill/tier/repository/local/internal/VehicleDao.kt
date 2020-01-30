package com.robertoestivill.tier.repository.local.internal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VehicleDao {

    @Query("SELECT * FROM VehicleEntity WHERE state LIKE '%' || :state || '%'")
    suspend fun retrieveInState(state: String): List<VehicleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vehicles: List<VehicleEntity>)
}
