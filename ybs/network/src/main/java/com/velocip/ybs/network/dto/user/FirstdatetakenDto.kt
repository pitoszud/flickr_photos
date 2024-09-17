package com.velocip.ybs.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FirstdatetakenDto(
    @SerialName("_content")
    val firstDateTakenContent: String
)