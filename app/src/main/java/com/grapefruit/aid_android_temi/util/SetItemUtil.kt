package com.grapefruit.aid_android_temi.util

import java.text.NumberFormat
import java.util.*

object SetItemUtil {
    fun setCost(cost: Long): String {
        return NumberFormat.getInstance(Locale.KOREA).format(cost) + "원"
    }

    fun setQuantity(quantity: Long): String {
        return NumberFormat.getInstance(Locale.KOREA).format(quantity) + "개"
    }

    fun setSeatNum(seatNum: Long): String {
        return NumberFormat.getInstance(Locale.KOREA).format(seatNum) + "번"
    }
}