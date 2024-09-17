package com.velocip.ybs.network.dto.photos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    val farm: Int = 0,
    val id: String,
    @SerialName("isfamily")
    val isFamily: Int? = 0,
    @SerialName("isfriend")
    val isFriend: Int? = 0,
    @SerialName("ispublic")
    val isPublic: Int = 0,
    val owner: String? = "",
    val secret: String? = "",
    val server: String? = "",
    val title: String? = "",
    val tags: String? = ""
)