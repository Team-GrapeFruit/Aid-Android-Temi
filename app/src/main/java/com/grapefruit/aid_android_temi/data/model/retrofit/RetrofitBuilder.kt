package com.grapefruit.aid_android_temi.data.model.retrofit

import com.grapefruit.aid_android_temi.data.model.dto.StoreDTO
import com.grapefruit.aid_android_temi.data.model.service.QRService
import com.grapefruit.aid_android_temi.data.model.service.SeatService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://port-0-aid-backend-user-nx562oley4rk2t.sel3.cloudtype.app/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val qrService: QRService by lazy {
        retrofit.create(QRService::class.java)
    }

    val seatService: SeatService by lazy {
        retrofit.create(SeatService::class.java)
    }

    suspend fun searchStore(storeId: Long): Response<StoreDTO> {
        return qrService.searchStore(storeId)
    }
}