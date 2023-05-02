package com.grapefruit.aid_android_temi.data.model.dto

data class PurchaseDTO(
    val purchaseId: Long,
    val quantity: Long,
    val purchaseMenu: MenuDTO
)
