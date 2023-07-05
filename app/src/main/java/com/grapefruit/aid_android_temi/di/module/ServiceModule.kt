package com.grapefruit.aid_android_temi.di.module

import com.grapefruit.aid_android_temi.data.remote.QRApi
import com.grapefruit.aid_android_temi.data.remote.SeatApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun providesQrService(retrofit: Retrofit): QRApi = retrofit.create(QRApi::class.java)

    @Singleton
    @Provides
    fun providesSeatService(retrofit: Retrofit): SeatApi = retrofit.create(SeatApi::class.java)
}