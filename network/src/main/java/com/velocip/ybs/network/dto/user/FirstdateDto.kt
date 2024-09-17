package com.velocip.ybs.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FirstdateDto(
    @SerialName("_content")
    val firstDateContent: String
)