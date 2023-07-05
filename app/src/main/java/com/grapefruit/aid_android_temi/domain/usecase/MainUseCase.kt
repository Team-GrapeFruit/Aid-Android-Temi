package com.grapefruit.aid_android_temi.domain.usecase

import com.grapefruit.aid_android_temi.domain.repository.MainRepository
import javax.inject.Inject

class MainUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    fun loadStore(storeId: Long) = mainRepository.loadStore(storeId)

    fun loadSeatList(storeId: Long) = mainRepository.loadSeatList(storeId)

    fun loadMenuList(seatId: Long) = mainRepository.loadMenuList(seatId)

    suspend fun moveStart(seatId: Long) = mainRepository.moveStart(seatId)

    suspend fun cancel(seatId: Long) = mainRepository.cancel(seatId)
}