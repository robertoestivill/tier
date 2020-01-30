package com.robertoestivill.tier.repository

import com.robertoestivill.tier.model.VehicleModel
import com.robertoestivill.tier.repository.local.VehicleLocalDataSource
import com.robertoestivill.tier.repository.remote.VehicleRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VehicleRepositoryImplTest {

    private lateinit var remoteDataSource: VehicleRemoteDataSource
    private lateinit var localDataSource: VehicleLocalDataSource

    private lateinit var vehicleRepository: VehicleRepository

    @Before
    fun initMocks() {
        remoteDataSource = mockk()
        localDataSource = mockk()

        vehicleRepository = VehicleRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    }

    @InternalCoroutinesApi
    @Test
    fun `Happy path, both data sources emit results`() = runBlocking {
        coEvery { localDataSource.retrieveVehiclesToPickup() }
            .returns(VehicleRepositoryResult.Success(createVehicles(4)))
        coEvery { localDataSource.saveAll(any()) }
            .returns(Unit)

        coEvery { remoteDataSource.retrieveVehicles(any(), any()) }
            .returns(VehicleRepositoryResult.Success(createVehicles(3)))

        val flow = vehicleRepository.retrieveVehiclesToPickUp(1.0, 1.0)

        val results = mutableListOf<VehicleRepositoryResult>()
        flow.collect { results.add(it) }

        coVerify(exactly = 1) { localDataSource.retrieveVehiclesToPickup() }
        coVerify(exactly = 1) { remoteDataSource.retrieveVehicles(any(), any()) }
        assertEquals(2, results.size)

        val result0 = results[0] as VehicleRepositoryResult.Success
        assertEquals(4, result0.vehicles.size)
        val result1 = results[1] as VehicleRepositoryResult.Success
        assertEquals(3, result1.vehicles.size)
    }

    @InternalCoroutinesApi
    @Test
    fun `Flow doesn't emit if local datasouce doesn't have results`() = runBlocking {
        coEvery { localDataSource.retrieveVehiclesToPickup() }
            .returns(VehicleRepositoryResult.Success(listOf()))

        coEvery { remoteDataSource.retrieveVehicles(any(), any()) }
            .returns(VehicleRepositoryResult.UnknownError)

        val flow = vehicleRepository.retrieveVehiclesToPickUp(1.0, 1.0)

        val results = mutableListOf<VehicleRepositoryResult>()
        flow.collect { results.add(it) }

        coVerify(exactly = 1) { remoteDataSource.retrieveVehicles(any(), any()) }
        coVerify(exactly = 1) { localDataSource.retrieveVehiclesToPickup() }
        assertEquals(1, results.size)
    }

    companion object {

        fun createVehicles(size: Int) = (1..size).map { createVehicle() }.toList()

        private fun createVehicle(state: VehicleModel.State = VehicleModel.State.TO_PICKUP) =
            VehicleModel(
                id = UUID.randomUUID().toString(),
                vehicleId = UUID.randomUUID().toString(),
                hardwareId = UUID.randomUUID().toString(),
                zoneId = UUID.randomUUID().toString(),
                resolution = null,
                resolvedBy = null,
                resolvedAt = null,
                battery = 0,
                state = state.name,
                model = "something",
                fleetBirdId = 1234L,
                latitude = 12.345,
                longitude = 67.890
            )
    }
}
