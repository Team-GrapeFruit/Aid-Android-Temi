package com.grapefruit.aid_android_temi.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.databinding.ActivityMoveBinding
import com.grapefruit.aid_android_temi.presentation.viewmodel.MoveViewModel
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener

class MoveActivity : AppCompatActivity(), OnGoToLocationStatusChangedListener {

    lateinit var binding: ActivityMoveBinding
    lateinit var viewModel: MoveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_move)
        binding.activity = this

        viewModel.robot.goTo(intent.getStringExtra("seatNum")!!)
        viewModel.robot.addOnGoToLocationStatusChangedListener(this)
        viewModel.robot.hideTopBar()

        val seatNum = intent.getStringExtra("seatNum")
        binding.temiText.text =
            when (seatNum) {
                "홈베이스" -> {
                    getString(R.string.go_homebase)
                }
                else -> {
                    getString(R.string.go_table, seatNum)
                }
            }
    }

    override fun onGoToLocationStatusChanged(
        location: String,
        status: String,
        descriptionId: Int,
        description: String
    ) {
        when (status) {
            "complete" -> {
                startActivity(
                    if (intent.getStringExtra("seatNum") != "홈베이스")
                        Intent(
                            this,
                            ArrivalActivity::class.java
                        ) else Intent(this, SeatReserveActivity::class.java)
                )
            }
        }
    }
}