package com.grapefruit.aid_android_temi.presentation.viewmodel

import androidx.lifecycle.*
import com.grapefruit.aid_android_temi.data.dto.CheckSeatDTO
import com.grapefruit.aid_android_temi.data.dto.PurchaseDTO
import com.grapefruit.aid_android_temi.data.dto.StoreDTO
import com.grapefruit.aid_android_temi.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {
    private val _storeInfo = MutableStateFlow<StoreDTO?>(null)
    val storeInfo: StateFlow<StoreDTO?> = _storeInfo

    private val _seatListResponse = MutableStateFlow<CheckSeatDTO?>(null)
    val seatListResponse: StateFlow<CheckSeatDTO?> = _seatListResponse

    private val _menuListResponse = MutableStateFlow<List<PurchaseDTO>?>(null)
    val menuListResponse: StateFlow<List<PurchaseDTO>?> = _menuListResponse

    fun storeLoad(storeId: Long) {
        viewModelScope.launch {
            _storeInfo.value = mainUseCase.loadStore(storeId).firstOrNull()
        }
    }

    fun seatList(storeId: Long) {
        viewModelScope.launch {
            _seatListResponse.value = mainUseCase.loadSeatList(storeId).firstOrNull()
        }
    }

    fun menuList(seatId: Long) {
        viewModelScope.launch {
            _menuListResponse.value = mainUseCase.loadMenuList(seatId).firstOrNull()
        }
    }

    fun moveStart(seatId: Long) {
        viewModelScope.launch {
            mainUseCase.moveStart(seatId)
        }
    }

    fun cancel(seatId: Long) {
        viewModelScope.launch {
            mainUseCase.cancel(seatId)
        }
    }
}
