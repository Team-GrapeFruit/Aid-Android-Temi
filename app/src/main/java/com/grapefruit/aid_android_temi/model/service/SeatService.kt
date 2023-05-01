package com.grapefruit.aid_android_temi.model.service

import com.grapefruit.aid_android_temi.model.dto.PurchaseDTO
import com.grapefruit.aid_android_temi.model.dto.SeatDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SeatService {
    @GET("/seat/{storeId}")
    fun seatList(
        @Path("storeId") storeId: Long
    ): Call<List<SeatDTO>>

    @GET("/purchase/{seatId}")
    fun menuList(
        @Path("seatId") seatId: Long
    ): Call<List<PurchaseDTO>>
}