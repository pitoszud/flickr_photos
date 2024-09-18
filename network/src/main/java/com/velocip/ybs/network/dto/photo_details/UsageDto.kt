package com.velocip.ybs.network.dto.photo_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsageDto(
    @SerialName("canblog")
    val canblog: Int = 0,
    @SerialName("candownload")
    val candownload: Int = 0,
    @SerialName("canprint")
    val canprint: Int = 0,
    @SerialName("canshare")
    val canshare: Int = 0
)