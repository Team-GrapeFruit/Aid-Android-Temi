package com.grapefruit.aid_android_temi.di.module

import com.grapefruit.aid_android_temi.data.remote.QRApi
import com.grapefruit.aid_android_temi.data.remote.SeatApi
import com.grapefruit.aid_android_temi.data.repository.MainRepositoryImpl
import com.grapefruit.aid_android_temi.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun providesContentRepository(
        qrApi: QRApi,
        seatApi: SeatApi
    ): MainRepository = MainRepositoryImpl(qrApi, seatApi)
}