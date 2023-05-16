package com.grapefruit.aid_android_temi.di

import com.grapefruit.aid_android_temi.data.dto.StoreDTO
import com.grapefruit.aid_android_temi.data.api.QRApi
import com.grapefruit.aid_android_temi.data.api.SeatApi
import com.grapefruit.aid_android_temi.data.dto.CheckSeatDTO
import com.grapefruit.aid_android_temi.data.dto.PurchaseDTO
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private const val BASE_URL = "https://port-0-aid-backend-user-nx562oley4rk2t.sel3.cloudtype.app/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val qrApi: QRApi = retrofit.create(QRApi::class.java)

    val seatApi: SeatApi = retrofit.create(SeatApi::class.java)

    suspend fun searchStore(storeId: Long): Response<StoreDTO> {
        return qrApi.searchStore(storeId)
    }

    suspend fun seatList(storeId: Long): Response<CheckSeatDTO> {
        return seatApi.seatList(storeId)
    }

    suspend fun menuList(seatId: Long): Response<List<PurchaseDTO>> {
        return seatApi.menuList(seatId)
    }

    suspend fun moveStart(seatId: Long): Response<Void> {
        return seatApi.moveStart(seatId)
    }

    suspend fun cancel(seatId: Long): Response<Unit> {
        return seatApi.cancel(seatId)
    }
}