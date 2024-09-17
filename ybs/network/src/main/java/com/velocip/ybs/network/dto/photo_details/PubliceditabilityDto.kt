package com.velocip.ybs.network.dto.photo_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PubliceditabilityDto(
    @SerialName("canaddmeta")
    val canaddmeta: Int = 0,
    @SerialName("cancomment")
    val cancomment: Int = 0
)