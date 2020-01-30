package com.robertoestivill.tier

import android.app.Application
import com.robertoestivill.tier.di.applicationModule
import com.robertoestivill.tier.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TierApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TierApplication)
            modules(
                listOf(applicationModule, repositoryModule)
            )
        }
    }
}
