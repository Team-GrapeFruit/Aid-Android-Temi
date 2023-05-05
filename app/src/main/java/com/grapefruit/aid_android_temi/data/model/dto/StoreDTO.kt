package com.grapefruit.aid_android_temi.data.model.dto

import com.google.gson.annotations.SerializedName

data class StoreDTO(
    @SerializedName("storeId") val storeId: Long,
    @SerializedName("storeName") val storeName: String,
    @SerializedName("information") val information: String,
    @SerializedName("storeImgURL") val storeImg: String?
)
