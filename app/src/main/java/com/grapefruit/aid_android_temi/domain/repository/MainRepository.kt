package com.grapefruit.aid_android_temi.domain.repository

import com.grapefruit.aid_android_temi.data.dto.CheckSeatDTO
import com.grapefruit.aid_android_temi.data.dto.PurchaseDTO
import com.grapefruit.aid_android_temi.data.dto.StoreDTO
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun loadStore(storeId: Long): Flow<StoreDTO>

    fun loadSeatList(storeId: Long): Flow<CheckSeatDTO>

    fun loadMenuList(seatId: Long): Flow<List<PurchaseDTO>>

    suspend fun moveStart(seatId: Long): Boolean

    suspend fun cancel(seatId: Long): Boolean

}