package com.grapefruit.aid_android_temi.data.dto

import com.google.gson.annotations.SerializedName

data class MenuDTO(
    @SerializedName("menuId") val menuId: Long,
    @SerializedName("menuName") val menuName: String,
    @SerializedName("cost") val cost: Long,
    @SerializedName("menuImgUrl") val menuImgUrl: String?
)
