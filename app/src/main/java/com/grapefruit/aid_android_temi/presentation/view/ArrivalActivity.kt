package com.grapefruit.aid_android_temi.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.databinding.ActivityArrivalBinding
import com.robotemi.sdk.Robot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArrivalActivity : AppCompatActivity() {

    lateinit var binding: ActivityArrivalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_arrival)

        binding.homeBtn.setOnClickListener {
            val intent = Intent(this, MoveActivity::class.java)
            intent.putExtra("seatNum", "홈베이스")
            startActivity(intent)
        }
    }
}