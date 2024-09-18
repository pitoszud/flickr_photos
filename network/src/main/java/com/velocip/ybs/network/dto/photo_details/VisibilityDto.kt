package com.velocip.ybs.network.dto.photo_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VisibilityDto(
    @SerialName("isfamily")
    val isfamily: Int = 0,
    @SerialName("isfriend")
    val isfriend: Int = 0,
    @SerialName("ispublic")
    val ispublic: Int = 0
)