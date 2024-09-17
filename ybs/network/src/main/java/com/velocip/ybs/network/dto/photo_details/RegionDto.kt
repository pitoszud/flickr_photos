package com.velocip.ybs.network.dto.photo_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionDto(
    @SerialName("_content")
    val content: String = ""
)