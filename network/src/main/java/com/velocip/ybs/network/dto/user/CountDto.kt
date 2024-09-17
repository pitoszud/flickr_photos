package com.velocip.ybs.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountDto(
    @SerialName("_content")
    val countContent: Int
)