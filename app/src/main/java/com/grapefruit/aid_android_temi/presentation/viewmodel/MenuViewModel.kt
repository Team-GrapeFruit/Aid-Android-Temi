package com.grapefruit.aid_android_temi.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grapefruit.aid_android_temi.data.model.dto.CheckSeatDTO
import com.grapefruit.aid_android_temi.data.model.dto.PurchaseDTO
import com.grapefruit.aid_android_temi.data.model.retrofit.RetrofitBuilder
import com.grapefruit.aid_android_temi.presentation.view.MenuCheckActivity
import com.grapefruit.aid_android_temi.presentation.view.SeatReserveActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuViewModel: ViewModel() {

    private val _seatListResponse = MutableLiveData<CheckSeatDTO>()
    val seatListResponse: LiveData<CheckSeatDTO> get() = _seatListResponse

    private val _menuListResponse = MutableLiveData<List<PurchaseDTO>>()
    val menuListResponse: LiveData<List<PurchaseDTO>> get() = _menuListResponse

    fun seatListResponse(seatList: CheckSeatDTO){
        _seatListResponse.value = seatList
    }

    fun menuListResponse(menuList: List<PurchaseDTO>){
        _menuListResponse.value = menuList
    }

    private val seatService = RetrofitBuilder.seatService

    fun seatList(storeId: Long, activity: MenuCheckActivity) {
        seatService.seatList(storeId)
            .enqueue(object : Callback<CheckSeatDTO> {
                override fun onResponse(
                    call: Call<CheckSeatDTO>,
                    response: Response<CheckSeatDTO>
                ) {
                    if (response.isSuccessful){
                        val seatList = response.body()
                        Log.d("자리 가져오기 성공","$seatList")

                        val viewModel = ViewModelProvider(activity)[MenuViewModel::class.java]
                        seatList?.let {
                            viewModel.seatListResponse(seatList)
                        }
                    }
                }

                override fun onFailure(call: Call<CheckSeatDTO>, t: Throwable) {
                    Log.d("통신 실패",t.message.toString())
                }
            })
    }

    fun menuList(seatId: Long, activity: MenuCheckActivity) {
        seatService.menuList(seatId)
            .enqueue(object : Callback<List<PurchaseDTO>> {
                override fun onResponse(
                    call: Call<List<PurchaseDTO>>,
                    response: Response<List<PurchaseDTO>>
                ) {
                    if (response.isSuccessful){
                        val menuList = response.body()
                        Log.d("주문 가져오기 성공", "$menuList")

                        val viewModel = ViewModelProvider(activity)[MenuViewModel::class.java]
                        menuList?.let {
                            viewModel.menuListResponse(menuList)
                        }
                    }
                }

                override fun onFailure(call: Call<List<PurchaseDTO>>, t: Throwable) {
                    Log.d("통신 실패",t.message.toString())
                }
            })
    }
}