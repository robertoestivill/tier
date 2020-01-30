package com.robertoestivill.tier.di

import java.util.concurrent.TimeUnit
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Application wide dependencies.
 */
val applicationModule = module {

    val defaultTimeoutSeconds = 30L
    val diskCacheMaxSizeBytes = 50L * 1025L

    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val cache = Cache(androidContext().cacheDir, diskCacheMaxSizeBytes)

        OkHttpClient.Builder()
            .readTimeout(defaultTimeoutSeconds, TimeUnit.SECONDS)
            .connectTimeout(defaultTimeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(defaultTimeoutSeconds, TimeUnit.SECONDS)
            .addNetworkInterceptor(loggingInterceptor)
            .cache(cache)
            .build()
    }
}
