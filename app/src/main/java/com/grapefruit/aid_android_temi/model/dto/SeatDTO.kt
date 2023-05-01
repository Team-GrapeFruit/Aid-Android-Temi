package com.grapefruit.aid_android_temi.model.dto

data class SeatDTO(
    val seatId: Long,
    val seatNum: Long,
    val customerNum: Long,
    val enabled: Boolean,
    val locationX: Float,
    val locationY: Float
)
