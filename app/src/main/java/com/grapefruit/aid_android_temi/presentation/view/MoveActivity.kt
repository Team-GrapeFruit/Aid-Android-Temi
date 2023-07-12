package com.grapefruit.aid_android_temi.presentation.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.databinding.ActivityMoveBinding
import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoveActivity : AppCompatActivity(), OnGoToLocationStatusChangedListener {

    lateinit var binding: ActivityMoveBinding
    private val robot = Robot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val seatNum = intent.getStringExtra("seatNum")
        Log.d("seatNum", seatNum!!)

        robot.getInstance().goTo(seatNum!!)
        robot.getInstance().addOnGoToLocationStatusChangedListener(this)
        robot.getInstance().hideTopBar()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_move)
        binding.activity = this

        animation()

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

    private fun animation() {
        val ani1 = AnimationUtils.loadAnimation(this, R.anim.ani1)
        val ani2 = AnimationUtils.loadAnimation(this, R.anim.ani2)
        val ani3 = AnimationUtils.loadAnimation(this, R.anim.ani3)

        val goFront = ObjectAnimator.ofFloat(binding.temiImg, "translationX", 480f).apply {
            duration = 3000
            repeatCount = ValueAnimator.INFINITE
        }

        with(binding) {
            exDot1.animation = ani1
            exDot2.animation = ani2
            exDot3.animation = ani3
        }
        goFront.start()
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