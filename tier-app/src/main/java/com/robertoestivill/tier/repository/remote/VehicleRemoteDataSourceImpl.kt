package com.robertoestivill.tier.repository.remote

import com.google.gson.Gson
import com.robertoestivill.tier.repository.VehicleRepositoryResult
import com.robertoestivill.tier.repository.remote.internal.VehicleResponse
import com.robertoestivill.tier.repository.remote.internal.toModel
import java.io.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

class VehicleRemoteDataSourceImpl(
    private val okHttpClient: OkHttpClient,
    private val coroutineDispatcher: CoroutineDispatcher
) : VehicleRemoteDataSource {

    private interface TierApi {

        @GET("/bins/k2srm")
        @Headers("Accept: application/json")
        suspend fun retrieveVehicles(
            @Query("lat") latitude: Double,
            @Query("lng") longitude: Double
        ): VehicleResponse
    }

    private val tierApi by lazy {
        val gsonConverter = GsonConverterFactory.create(Gson())
        val okHttpClient = okHttpClient
            .newBuilder()
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverter)
            .build()
            .create(TierApi::class.java)
    }

    override suspend fun retrieveVehicles(
        latitude: Double,
        longitude: Double
    ) = withContext(coroutineDispatcher) {

        try {
            val response = withRetries { tierApi.retrieveVehicles(latitude, longitude) }
            val models = response.current.locations.map { it.toModel() }
            VehicleRepositoryResult.Success(models)
        } catch (io: IOException) {
            VehicleRepositoryResult.IOError
        } catch (ex: Exception) {
            VehicleRepositoryResult.UnknownError
        }
    }

    private suspend fun <T> withRetries(
        retries: Int = 3,
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = 0L
        repeat(retries - 1) {
            try {
                return block()
            } catch (e: IOException) {
                // Log non-fatal ?
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong()
        }
        return block()
    }

    companion object {
        const val BASE_URL = "https://api.myjson.com"
    }
}
