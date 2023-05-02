package com.grapefruit.aid_android_temi.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.databinding.ActivityMoveBinding
import com.grapefruit.aid_android_temi.presentation.viewmodel.MoveViewModel

class MoveActivity : AppCompatActivity() {

    lateinit var binding: ActivityMoveBinding
    lateinit var viewModel: MoveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_move)
        binding.activity = this


        with(binding){
            val seatNum = intent.getLongExtra("seatNum", 0)

            temiText.text = seatNum.toString() + "번 자리로 이동중 잠시 비켜주세요!"
        }
    }
}