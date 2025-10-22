package com.shmz.core_data.di

import com.shmz.core_data.repositories.CurrencyRepository
import com.shmz.core_data.repositories.ICurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DataAndroidModule {
    @Binds
    fun currencyRepository(currencyRepository: CurrencyRepository): ICurrencyRepository
}