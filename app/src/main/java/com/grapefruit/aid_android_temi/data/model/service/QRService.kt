package com.grapefruit.aid_android_temi.data.model.service

import com.grapefruit.aid_android_temi.data.model.dto.StoreDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface QRService {
    @GET("store/{storeId}")
    suspend fun searchStore(@Path("storeId") storeId: Long): Response<StoreDTO>
}