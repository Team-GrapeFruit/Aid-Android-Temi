package com.grapefruit.aid_android_temi.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.databinding.ActivityMoveBinding
import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener

class MoveActivity : AppCompatActivity(), OnGoToLocationStatusChangedListener {

    lateinit var binding: ActivityMoveBinding
    private val robot = Robot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_move)
        binding.activity = this

        val seatNum = intent.getStringExtra("seatNum")
        Log.d("seatNum", seatNum!!)

        robot.getInstance().goTo(seatNum!!)
        robot.getInstance().addOnGoToLocationStatusChangedListener(this)
        robot.getInstance().hideTopBar()

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
                        Intent(this, ArrivalActivity::class.java)
                    else
                        Intent(this, SeatReserveActivity::class.java)
                )
            }
        }
    }
}