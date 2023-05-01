package com.grapefruit.aid_android_temi.model.dto

data class PurchaseDTO(
    val purchaseId: Long,
    val quantity: Long,
    val purchaseMenu: MenuDTO
)
