package com.grapefruit.aid_android_temi.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.grapefruit.aid_android_temi.data.dto.CheckSeatDTO
import com.grapefruit.aid_android_temi.data.dto.PurchaseDTO
import com.grapefruit.aid_android_temi.data.dto.StoreDTO
import com.grapefruit.aid_android_temi.di.NetworkModule
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private var _storeInfo = MutableLiveData<StoreDTO>()
    val storeInfo: LiveData<StoreDTO> get() = _storeInfo

    private val _seatListResponse = MutableLiveData<CheckSeatDTO>()
    val seatListResponse: LiveData<CheckSeatDTO> get() = _seatListResponse

    private val _menuListResponse = MutableLiveData<List<PurchaseDTO>>()
    val menuListResponse: LiveData<List<PurchaseDTO>> get() = _menuListResponse

    fun storeLoad(storeId: Long) {
        viewModelScope.launch {
            val response = NetworkModule.searchStore(storeId)
            Log.d("qrResponse", response.code().toString())

            if (response.code() == 200) {
                _storeInfo.value = response.body()
                Log.d("qr 인증 성공", "$response")
            } else {
                Log.d("qr 인증 실패", response.code().toString())
            }
        }
    }
    fun seatList(storeId: Long) {
        viewModelScope.launch {
            val response = NetworkModule.seatList(storeId)
            Log.d("seatResponse", response.code().toString())

            if (response.code() == 200){
                _seatListResponse.value = response.body()
                Log.d("자리 가져오기 성공","$response")
            } else {
                Log.d("자리 가져오기 실패", response.code().toString())
            }
        }
    }

    fun menuList(seatId: Long) {
        viewModelScope.launch {
            val response = NetworkModule.menuList(seatId)
            Log.d("menuResponse", response.code().toString())

            if (response.code() == 200){
                _menuListResponse.value = response.body()
                Log.d("메뉴 가져오기 성공","$response")
            } else {
                Log.d("메뉴 가져오기 실패", response.code().toString())
            }
        }
    }

    fun moveStart(seatId: Long) {
        viewModelScope.launch {
            val response = NetworkModule.moveStart(seatId)
            Log.d("deleteResponse", response.code().toString())

            if (response.code() == 204){
                Log.d("메뉴 삭제 성공","$response")
            } else {
                Log.d("메뉴 삭제 실패", response.code().toString())
            }
        }
    }

    fun cancel(seatId: Long){
        viewModelScope.launch {
            val response = NetworkModule.cancel(seatId)

            when(response.code()){
                204 -> {
                    Log.d("자리취소 성공", "${response.code()}")
                }
                409 -> {
                    Log.d("자리 사용안하는중", "${response.code()}")
                }
            }
        }
    }
}