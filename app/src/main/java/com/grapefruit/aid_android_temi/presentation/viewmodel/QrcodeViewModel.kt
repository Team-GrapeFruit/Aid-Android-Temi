package com.grapefruit.aid_android_temi.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grapefruit.aid_android_temi.data.model.dto.StoreDTO
import com.grapefruit.aid_android_temi.data.model.retrofit.RetrofitBuilder
import kotlinx.coroutines.launch

class QrcodeViewModel : ViewModel() {

    private var _storeInfo = MutableLiveData<StoreDTO>()
    val storeInfo: LiveData<StoreDTO> get() = _storeInfo

    fun storeLoad(storeId: Long) {
        viewModelScope.launch {
            val response = RetrofitBuilder.searchStore(storeId)
            Log.d("response", response.code().toString())

            if (response.code() == 200) {
                _storeInfo.value = response.body()
                Log.d("test", response.body().toString())
            } else {
                Log.d("test", "fail")
            }
        }
    }
}