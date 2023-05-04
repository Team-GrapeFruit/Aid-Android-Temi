package com.grapefruit.aid_android_temi.data.model.service

import com.grapefruit.aid_android_temi.data.model.dto.CheckSeatDTO
import com.grapefruit.aid_android_temi.data.model.dto.PurchaseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SeatService {
    @GET("/seat/{storeId}")
    fun seatList(
        @Path("storeId") storeId: Long
    ): Call<CheckSeatDTO>

    @GET("/purchase/{seatId}")
    fun menuList(
        @Path("seatId") seatId: Long
    ): Call<List<PurchaseDTO>>
}