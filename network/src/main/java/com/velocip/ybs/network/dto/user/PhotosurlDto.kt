package com.velocip.ybs.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotosurlDto(
    @SerialName("_content")
    val photoUrlContent: String
)