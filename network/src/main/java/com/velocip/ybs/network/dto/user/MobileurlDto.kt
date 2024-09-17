package com.velocip.ybs.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MobileurlDto(
    @SerialName("_content")
    val mobileUrlContent: String
)