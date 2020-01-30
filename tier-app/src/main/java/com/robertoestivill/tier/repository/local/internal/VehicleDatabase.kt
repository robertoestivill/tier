package com.robertoestivill.tier.repository.local.internal

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VehicleEntity::class], version = 1)
abstract class VehicleDatabase : RoomDatabase() {

    abstract fun vehicleDao(): VehicleDao
}
