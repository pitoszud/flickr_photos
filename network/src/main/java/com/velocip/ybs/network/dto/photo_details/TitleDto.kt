package com.velocip.ybs.network.dto.photo_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TitleDto(
    @SerialName("_content")
    val content: String = ""
)