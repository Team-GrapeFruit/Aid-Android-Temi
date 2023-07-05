package com.grapefruit.aid_android_temi.data.remote

import com.grapefruit.aid_android_temi.data.dto.CheckSeatDTO
import com.grapefruit.aid_android_temi.data.dto.PurchaseDTO
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SeatApi {
    @GET("/seat/{storeId}")
    suspend fun seatList(
        @Path("storeId") storeId: Long
    ): Response<CheckSeatDTO>

    @GET("/purchase/{seatId}")
    suspend fun menuList(
        @Path("seatId") seatId: Long
    ): Response<List<PurchaseDTO>>

    @DELETE("/purchase/{seatId}")
    suspend fun moveStart(
        @Path("seatId") seatId: Long
    ): Response<Void>

    @PATCH("/seat/cancel/{seatId}")
    suspend fun cancel(
        @Path("seatId") seatId: Long
    ): Response<Unit>
}