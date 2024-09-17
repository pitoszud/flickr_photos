package com.velocip.ybs.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsernameDto(
    @SerialName("_content")
    val userNameContent: String
)