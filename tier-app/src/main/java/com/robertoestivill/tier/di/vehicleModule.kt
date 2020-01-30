package com.robertoestivill.tier.di

import com.robertoestivill.tier.presentation.VehicleViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vehicleModule = module {

    viewModel {
        VehicleViewModel(
            vehicleRepository = get()
        )
    }
}
