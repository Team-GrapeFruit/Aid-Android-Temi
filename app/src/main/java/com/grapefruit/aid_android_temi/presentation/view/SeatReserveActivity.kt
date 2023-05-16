package com.grapefruit.aid_android_temi.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.databinding.ActivitySeatReserveBinding
import com.grapefruit.aid_android_temi.presentation.viewmodel.MainViewModel
import com.robotemi.sdk.Robot

class SeatReserveActivity : AppCompatActivity() {

    lateinit var binding: ActivitySeatReserveBinding
    private val viewModel: MainViewModel by viewModels()
    private var storeId: Long = 0
    private val robot = Robot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_seat_reserve)
        binding.activity = this

        robot.getInstance().setKioskModeOn(true)
        robot.getInstance().hideTopBar()

        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val storeId = sharedPreferences.getLong("storeId", 0)

        viewModel.seatList(storeId)

        viewModel.seatListResponse.observe(this) {
            with(binding) {
                for (i in 0..it.singleSeatResponse.lastIndex) {
                    table.addView(createTable(i))
                }
            }
        }

        binding.menuText.setOnClickListener {
            val intent = Intent(this, MenuCheckActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.seatList(storeId)
    }

    private fun createTable(index: Int): View {
        val table = TextView(this)
        val seatList = viewModel.seatListResponse.value?.singleSeatResponse?.get(index)!!
        table.width = widthSize(seatList.customerNum)
        table.height = heightSize(seatList.customerNum)
        table.x = seatList.locationX
        table.y = seatList.locationY
        table.text = getString(R.string.table_number, seatList.seatNum.toString())
        table.setTextColor(
            ContextCompat.getColor(
                this,
                textColor(seatList.enabled)
            )
        )
        table.gravity = Gravity.CENTER
        table.textSize = 24F
        table.typeface = ResourcesCompat.getFont(this, R.font.roboto_light)
        table.setBackgroundResource(background(seatList.enabled))
        table.id = ViewCompat.generateViewId()
        Log.d("table", "${table.id}")
        table.setOnClickListener {
            val dialog = SeatReserveDialog(this, seatList.seatNum, seatList.seatId)
            Log.d("dialog", "${seatList.seatNum}" + "${seatList.seatId}")

            dialog.show(this.supportFragmentManager, "CustomDialog")
        }
        return table
    }

    private fun widthSize(customerNum: Long): Int {
        return if (customerNum == 1L) 98
        else 200
    }

    private fun heightSize(customerNum: Long): Int {
        return when (customerNum) {
            1L -> 98
            2L -> 98
            4L -> 200
            6L -> 400
            8L -> 600
            else -> 0
        }
    }

    private fun textColor(enabled: Boolean): Int {
        return if (enabled == true) R.color.dark_gray
        else R.color.white
    }

    private fun background(enabled: Boolean): Int {
        return if (enabled == true) R.drawable.seat_empty_background
        else R.drawable.seat_use_background
    }
}