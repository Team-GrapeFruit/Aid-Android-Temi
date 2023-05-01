package com.grapefruit.aid_android_temi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.databinding.ActivitySeatReserveBinding
import com.grapefruit.aid_android_temi.viewmodel.SeatReserveViewModel

class SeatReserveActivity : AppCompatActivity() {

    lateinit var binding: ActivitySeatReserveBinding
    lateinit var viewModel: SeatReserveViewModel
    val storeId = 1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProvider(this)[SeatReserveViewModel::class.java]

        viewModel.seatList(storeId, this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_seat_reserve)
        binding.activity = this

        viewModel.seatListResponse.observe(this) {
            with(binding) {
                for (i in 0..it.lastIndex) {
                    table.addView(createTable(i))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.seatList(storeId, this)
    }

    private fun createTable(index: Int): View {
        val table = TextView(this)
        val seatList = viewModel.seatListResponse.value?.get(index)!!
        table.width = widthSize(seatList.customerNum)
        table.height = heightSize(seatList.customerNum)
        table.x = seatList.locationX
        table.y = seatList.locationY
        table.text = "" + seatList.seatNum + "번\n"
        table.setTextColor(
            ContextCompat.getColor(
                this,
                textColor(seatList.enabled)
            )
        )
        table.gravity = Gravity.CENTER
        table.textSize = 36F
        table.typeface = ResourcesCompat.getFont(this, R.font.roboto_light)
        table.setBackgroundResource(background(seatList.enabled))
        table.id = ViewCompat.generateViewId()
        Log.d("table", "${table.id}")
        table.setOnClickListener {
            SeatReserveDialog(this).show(seatList.seatNum, seatList.seatId)
        }
        return table
    }

    private fun widthSize(customerNum: Long): Int {
        return if (customerNum == 1L) 190
        else 400
    }

    private fun heightSize(customerNum: Long): Int {
        return when (customerNum) {
            1L -> 190
            2L -> 190
            4L -> 400
            6L -> 600
            8L -> 800
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