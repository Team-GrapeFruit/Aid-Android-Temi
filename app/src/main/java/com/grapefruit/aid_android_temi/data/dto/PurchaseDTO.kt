package com.grapefruit.aid_android_temi.data.dto

import com.google.gson.annotations.SerializedName

data class PurchaseDTO(
    @SerializedName("purchaseId") val purchaseId: Long,
    @SerializedName("quantity") val quantity: Long,
    @SerializedName("purchaseMenu") val purchaseMenu: MenuDTO
)
