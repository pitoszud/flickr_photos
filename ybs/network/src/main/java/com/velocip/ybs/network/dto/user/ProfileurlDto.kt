package com.velocip.ybs.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileurlDto(
    @SerialName("_content")
    val profileUrlContent: String
)