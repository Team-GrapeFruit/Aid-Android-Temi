package com.grapefruit.aid_android_temi.data.remote

import com.grapefruit.aid_android_temi.data.dto.StoreDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface QRApi {
    @GET("store/{storeId}")
    suspend fun searchStore(@Path("storeId") storeId: Long): Response<StoreDTO>
}