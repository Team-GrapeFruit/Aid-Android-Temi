package com.grapefruit.aid_android_temi.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.databinding.ActivityArrivalBinding
import com.grapefruit.aid_android_temi.presentation.viewmodel.MoveViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArrivalActivity : AppCompatActivity() {

    lateinit var binding: ActivityArrivalBinding
    lateinit var viewModel: MoveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrival)

        binding.homeBtn.setOnClickListener {
            val intent = Intent(this, MoveActivity::class.java)
            intent.putExtra("seatNum", "홈베이스")
            viewModel.robot.goTo("홈베이스")
            startActivity(intent)
        }
    }
}