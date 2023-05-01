package com.grapefruit.aid_android_temi.view

import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.grapefruit.aid_android_temi.databinding.DialogSeatReserveBinding
import com.grapefruit.aid_android_temi.viewmodel.SeatReserveViewModel

class SeatReserveDialog(private val context : SeatReserveActivity): DialogFragment() {

    lateinit var binding: DialogSeatReserveBinding
    lateinit var viewModel: SeatReserveViewModel
    private val dlg = Dialog(context)

    fun show(seatNum: Long, seatId: Long){

        viewModel =
            ViewModelProvider(context)[SeatReserveViewModel::class.java]

        viewModel.menuList(seatId, context)

        binding = DialogSeatReserveBinding.inflate(context.layoutInflater)

        Log.d("menuList", "${viewModel.menuListResponse.value}")

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   // 타이틀바 제거
        dlg.setContentView(binding.root)     // 다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(true)    // 다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히도록

        with(binding){
            number.text = "" + seatNum + "번"
            cancelBtn.setOnClickListener {
                dlg.dismiss()
            }

            nextBtn.setOnClickListener {

            }
        }

        viewModel.menuListResponse.observe(context) {
            Log.d("menuList", "$it")
            val menuRecyclerView = binding.menuRecycler
            menuRecyclerView.adapter = SeatRecyclerAdapter(
                it,
                LayoutInflater.from(context),
                Glide.with(context)
            )
        }

        dlg.show()
    }
}