package com.robertoestivill.tier.di

import androidx.room.Room
import com.robertoestivill.tier.repository.VehicleRepository
import com.robertoestivill.tier.repository.VehicleRepositoryImpl
import com.robertoestivill.tier.repository.local.VehicleLocalDataSource
import com.robertoestivill.tier.repository.local.VehicleLocalDataSourceImpl
import com.robertoestivill.tier.repository.local.internal.VehicleDatabase
import com.robertoestivill.tier.repository.remote.VehicleRemoteDataSource
import com.robertoestivill.tier.repository.remote.VehicleRemoteDataSourceImpl
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val repositoryModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            VehicleDatabase::class.java,
            "tier-vehicle-db"
        ).build()
    }

    single<VehicleRemoteDataSource> {
        VehicleRemoteDataSourceImpl(
            coroutineDispatcher = Dispatchers.IO,
            okHttpClient = get()
        )
    }

    single<VehicleLocalDataSource> {
        VehicleLocalDataSourceImpl(
            coroutineDispatcher = Dispatchers.Default,
            vehicleDao = get<VehicleDatabase>().vehicleDao()
        )
    }

    single<VehicleRepository> {
        VehicleRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
}
