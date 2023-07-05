package com.grapefruit.aid_android_temi.data.repository

import com.grapefruit.aid_android_temi.data.dto.CheckSeatDTO
import com.grapefruit.aid_android_temi.data.dto.PurchaseDTO
import com.grapefruit.aid_android_temi.data.dto.StoreDTO
import com.grapefruit.aid_android_temi.data.remote.QRApi
import com.grapefruit.aid_android_temi.data.remote.SeatApi
import com.grapefruit.aid_android_temi.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val qrApi: QRApi,
    private val seatApi: SeatApi
) : MainRepository {

    override fun loadStore(storeId: Long): Flow<StoreDTO> {
        return flow {
            try {
                val response = qrApi.searchStore(storeId)
                if (response.isSuccessful) {
                    val storeDTO = response.body()
                    if (storeDTO != null) {
                        emit(storeDTO)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun loadSeatList(storeId: Long): Flow<CheckSeatDTO> {
        return flow {
            try {
                val response = seatApi.seatList(storeId)
                if (response.isSuccessful) {
                    val seatDTO = response.body()
                    if (seatDTO != null) {
                        emit(seatDTO)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun loadMenuList(seatId: Long): Flow<List<PurchaseDTO>> {
        return flow {
            try {
                val response = seatApi.menuList(seatId)
                if (response.isSuccessful) {
                    val purchaseDTO = response.body()
                    if (purchaseDTO != null) {
                        emit(purchaseDTO)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun moveStart(seatId: Long): Boolean {
        return try {
            seatApi.moveStart(seatId)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun cancel(seatId: Long): Boolean {
        return try {
            seatApi.cancel(seatId)
            true
        } catch (e: Exception) {
            false
        }
    }
}